package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import com.drover.demo.backend.config.MercadoPagoProperties;
import com.drover.demo.backend.config.MercadoPagoProperties.CuentaMercadoPago;
import com.drover.demo.backend.dto.MercadoPagoPreferenceResponse;
import com.drover.demo.backend.entity.Cuenta;
import com.drover.demo.backend.entity.PagoVenta;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.repository.CuentaRepository;
import com.drover.demo.backend.repository.PagoVentaRepository;
import com.drover.demo.backend.repository.VentaRepository;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class MercadoPagoService {

    private final MercadoPagoProperties properties;
    private final VentaRepository ventaRepository;
    private final CuentaRepository cuentaRepository;
    private final PagoVentaRepository pagoVentaRepository;
    private final PagoVentaService pagoVentaService;
    private final RestClient restClient;

    public MercadoPagoService(MercadoPagoProperties properties, VentaRepository ventaRepository,
                              CuentaRepository cuentaRepository, PagoVentaRepository pagoVentaRepository,
                              PagoVentaService pagoVentaService) {
        this.properties = properties;
        this.ventaRepository = ventaRepository;
        this.cuentaRepository = cuentaRepository;
        this.pagoVentaRepository = pagoVentaRepository;
        this.pagoVentaService = pagoVentaService;
        this.restClient = RestClient.builder()
            .baseUrl("https://api.mercadopago.com")
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    public MercadoPagoPreferenceResponse crearPreferenciaVenta(Long ventaId, Long cuentaId) {
        Venta venta = ventaRepository.findById(ventaId)
            .orElseThrow(() -> new RuntimeException("Venta no encontrada con el ID: " + ventaId));
        Cuenta cuenta = cuentaRepository.findById(cuentaId)
            .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con el ID: " + cuentaId));

        if (!"mercado_pago".equals(cuenta.getTipo())) {
            throw new IllegalArgumentException("La cuenta seleccionada no es de tipo mercado_pago.");
        }

        CuentaMercadoPago cuentaMp = buscarCuentaMercadoPago(cuentaId);
        String externalReference = "venta:" + venta.getId() + ":cuenta:" + cuenta.getId();

        Map<String, Object> body = Map.of(
            "items", List.of(Map.of(
                "title", "Venta " + venta.getNroVenta(),
                "quantity", 1,
                "currency_id", "ARS",
                "unit_price", venta.getMontoTotal()
            )),
            "external_reference", externalReference,
            "notification_url", properties.getWebhookUrl(),
            "back_urls", Map.of(
                "success", properties.getSuccessUrl(),
                "failure", properties.getFailureUrl(),
                "pending", properties.getPendingUrl()
            ),
            "auto_return", "approved"
        );

        JsonNode respuesta = restClient.post()
            .uri("/checkout/preferences")
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + cuentaMp.getAccessToken())
            .body(body)
            .retrieve()
            .body(JsonNode.class);

        return new MercadoPagoPreferenceResponse(
            texto(respuesta, "id"),
            texto(respuesta, "init_point"),
            texto(respuesta, "sandbox_init_point"),
            externalReference
        );
    }

    @Transactional
    public void procesarPagoNotificado(String paymentId) {
        if (paymentId == null || paymentId.strip().isEmpty()) {
            throw new IllegalArgumentException("El ID del pago de Mercado Pago es obligatorio.");
        }

        if (pagoVentaRepository.existsByIdPagoExterno(paymentId)) {
            return;
        }

        for (CuentaMercadoPago cuentaMp : properties.getCuentas()) {
            if (cuentaMp.getAccessToken() == null || cuentaMp.getAccessToken().isBlank()) {
                continue;
            }

            JsonNode pago = obtenerPago(paymentId, cuentaMp);
            if (pago == null || pago.has("error")) {
                continue;
            }

            String estado = texto(pago, "status");
            if (!"approved".equals(estado)) {
                return;
            }

            Long ventaId = ventaIdDesdeReferencia(texto(pago, "external_reference"));
            Long cuentaId = cuentaIdDesdeReferencia(texto(pago, "external_reference"));
            if (cuentaId == null) {
                cuentaId = cuentaMp.getCuentaId();
            }

            Venta venta = new Venta();
            venta.setId(ventaId);

            Cuenta cuenta = new Cuenta();
            cuenta.setId(cuentaId);

            PagoVenta pagoVenta = new PagoVenta();
            pagoVenta.setVenta(venta);
            pagoVenta.setCuenta(cuenta);
            pagoVenta.setMonto(decimal(pago, "transaction_amount"));
            pagoVenta.setFecha(fechaPago(pago));
            pagoVenta.setMedioPago("mercado_pago");
            pagoVenta.setProveedorPago("mercado_pago");
            pagoVenta.setIdPagoExterno(paymentId);
            pagoVenta.setReferenciaExterna(texto(pago, "external_reference"));
            pagoVenta.setEstadoExterno(estado);
            pagoVenta.setObservaciones("Pago aprobado por Mercado Pago");

            pagoVentaService.guardar(pagoVenta);
            return;
        }

        throw new RuntimeException("No se pudo consultar el pago en ninguna cuenta configurada de Mercado Pago.");
    }

    private JsonNode obtenerPago(String paymentId, CuentaMercadoPago cuentaMp) {
        try {
            return restClient.get()
                .uri("/v1/payments/{id}", paymentId)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + cuentaMp.getAccessToken())
                .retrieve()
                .body(JsonNode.class);
        } catch (RuntimeException e) {
            return null;
        }
    }

    private CuentaMercadoPago buscarCuentaMercadoPago(Long cuentaId) {
        CuentaMercadoPago cuenta = properties.buscarPorCuentaId(cuentaId)
            .orElseThrow(() -> new IllegalArgumentException("No hay credenciales de Mercado Pago configuradas para la cuenta ID " + cuentaId));

        if (cuenta.getAccessToken() == null || cuenta.getAccessToken().isBlank()) {
            throw new IllegalStateException("La cuenta de Mercado Pago no tiene access token configurado.");
        }
        return cuenta;
    }

    private Long ventaIdDesdeReferencia(String referencia) {
        String[] partes = referencia != null ? referencia.split(":") : new String[0];
        if (partes.length < 4 || !"venta".equals(partes[0])) {
            throw new IllegalArgumentException("La referencia externa de Mercado Pago no corresponde a una venta valida.");
        }
        return Long.valueOf(partes[1]);
    }

    private Long cuentaIdDesdeReferencia(String referencia) {
        String[] partes = referencia != null ? referencia.split(":") : new String[0];
        if (partes.length >= 4 && "cuenta".equals(partes[2])) {
            return Long.valueOf(partes[3]);
        }
        return null;
    }

    private String texto(JsonNode node, String campo) {
        return node != null && node.hasNonNull(campo) ? node.get(campo).asText() : null;
    }

    private BigDecimal decimal(JsonNode node, String campo) {
        if (node == null || !node.hasNonNull(campo)) {
            throw new IllegalArgumentException("Mercado Pago no informo el campo " + campo + ".");
        }
        return node.get(campo).decimalValue();
    }

    private java.time.LocalDateTime fechaPago(JsonNode node) {
        String fecha = texto(node, "date_approved");
        if (fecha == null) {
            fecha = texto(node, "date_created");
        }
        if (fecha == null) {
            return java.time.LocalDateTime.now();
        }
        try {
            return OffsetDateTime.parse(fecha).toLocalDateTime();
        } catch (DateTimeParseException e) {
            return java.time.LocalDateTime.now();
        }
    }
}
