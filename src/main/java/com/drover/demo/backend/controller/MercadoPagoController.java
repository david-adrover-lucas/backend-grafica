package com.drover.demo.backend.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.MercadoPagoPreferenceResponse;
import com.drover.demo.backend.service.MercadoPagoService;

@RestController
@RequestMapping("/api/mercado-pago")
public class MercadoPagoController {

    private final MercadoPagoService mercadoPagoService;

    public MercadoPagoController(MercadoPagoService mercadoPagoService) {
        this.mercadoPagoService = mercadoPagoService;
    }

    @PostMapping("/ventas/{ventaId}/preferencia")
    public ResponseEntity<MercadoPagoPreferenceResponse> crearPreferenciaVenta(
            @PathVariable Long ventaId,
            @RequestParam Long cuentaId) {
        return ResponseEntity.status(201).body(mercadoPagoService.crearPreferenciaVenta(ventaId, cuentaId));
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> recibirWebhook(
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "data.id", required = false) String dataId,
            @RequestBody(required = false) Map<String, Object> body,
            @RequestHeader(name = "x-signature", required = false) String signature,
            @RequestHeader(name = "x-request-id", required = false) String requestId) {
        if ("payment".equals(type) && dataId != null) {
            mercadoPagoService.procesarPagoNotificado(dataId);
            return ResponseEntity.ok().build();
        }

        String bodyType = body != null && body.get("type") != null ? body.get("type").toString() : null;
        String bodyDataId = extraerDataId(body);
        if ("payment".equals(bodyType) && bodyDataId != null) {
            mercadoPagoService.procesarPagoNotificado(bodyDataId);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/webhook")
    public ResponseEntity<String> verificarWebhook() {
        return ResponseEntity.ok("ok");
    }

    @SuppressWarnings("unchecked")
    private String extraerDataId(Map<String, Object> body) {
        if (body == null || body.get("data") == null || !(body.get("data") instanceof Map)) {
            return null;
        }
        Object id = ((Map<String, Object>) body.get("data")).get("id");
        return id != null ? id.toString() : null;
    }
}
