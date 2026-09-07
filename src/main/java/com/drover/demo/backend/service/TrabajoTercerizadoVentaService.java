package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drover.demo.backend.entity.TrabajoTercerizadoVenta;
import com.drover.demo.backend.repository.CostosEscalaTercerizadoRepository;
import com.drover.demo.backend.repository.TrabajoTercerizadoVentaRepository;

@Service
public class TrabajoTercerizadoVentaService {

    private final TrabajoTercerizadoVentaRepository trabajoTercerizadoVentaRepository;
    private final CostosEscalaTercerizadoRepository costosEscalaRepository;
    private final DeudaService deudaService;

    private final List<String> estadosPermitidos = List.of("pendiente", "enviado", "en_proceso", "listo", "retirado", "cancelado");

    public TrabajoTercerizadoVentaService(TrabajoTercerizadoVentaRepository trabajoTercerizadoVentaRepository,
                                          CostosEscalaTercerizadoRepository costosEscalaRepository,
                                          DeudaService deudaService) {
        this.trabajoTercerizadoVentaRepository = trabajoTercerizadoVentaRepository;
        this.costosEscalaRepository = costosEscalaRepository;
        this.deudaService = deudaService;
    }

    @Transactional
    public void guardar(TrabajoTercerizadoVenta trabajo) {
        TrabajoTercerizadoVenta limpio = validarTrabajoTercerizadoVenta(trabajo);
        trabajoTercerizadoVentaRepository.save(limpio);
    }

    @Transactional
    public void editar(Long id, TrabajoTercerizadoVenta trabajo) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        TrabajoTercerizadoVenta existente = trabajoTercerizadoVentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden de trabajo tercerizado no encontrada con el ID: " + id));

        if ("retirado".equals(existente.getEstado())) {
            throw new IllegalStateException("Un trabajo tercerizado retirado ya genera deuda y no puede editarse.");
        }

        TrabajoTercerizadoVenta datosNuevos = validarTrabajoTercerizadoVenta(trabajo);

        existente.setVenta(datosNuevos.getVenta());
        existente.setDetalleVenta(datosNuevos.getDetalleVenta());
        existente.setTrabajoTercerizado(datosNuevos.getTrabajoTercerizado());
        existente.setProveedor(datosNuevos.getProveedor());
        existente.setCantidad(datosNuevos.getCantidad());
        existente.setPrecioUnitarioHistorico(datosNuevos.getPrecioUnitarioHistorico());
        existente.setCostoTotalHistorico(datosNuevos.getCostoTotalHistorico());
        existente.setEstado(datosNuevos.getEstado());
        existente.setFechaEnvio(datosNuevos.getFechaEnvio());
        existente.setFechaFinalizacion(datosNuevos.getFechaFinalizacion());
        existente.setFechaRetiro(datosNuevos.getFechaRetiro());

        trabajoTercerizadoVentaRepository.save(existente);
    }

    @Transactional
    public TrabajoTercerizadoVenta cambiarEstado(Long id, String estado) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del trabajo tercerizado es obligatorio.");
        }

        String estadoLimpio = validarEstado(estado);
        TrabajoTercerizadoVenta trabajo = trabajoTercerizadoVentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Orden de trabajo tercerizado no encontrada con el ID: " + id));

        String estadoAnterior = trabajo.getEstado();
        trabajo.setEstado(estadoLimpio);
        actualizarFechasPorEstado(trabajo, estadoLimpio);

        TrabajoTercerizadoVenta guardado = trabajoTercerizadoVentaRepository.save(trabajo);
        if ("retirado".equals(estadoLimpio) && !"retirado".equals(estadoAnterior)) {
            deudaService.generarDeudaTercerizado(guardado.getId());
        }

        return guardado;
    }

    @Transactional
    public void generarDeudaProveedor(Long id) {
        deudaService.generarDeudaTercerizado(id);
    }

    public List<TrabajoTercerizadoVenta> listar() {
        return trabajoTercerizadoVentaRepository.findAll();
    }

    public List<TrabajoTercerizadoVenta> listarPorEstado(String estado) {
        return trabajoTercerizadoVentaRepository.findByEstado(validarEstado(estado));
    }

    public List<TrabajoTercerizadoVenta> listarPorProveedor(Long proveedorId) {
        if (proveedorId == null) {
            throw new IllegalArgumentException("El ID del proveedor es obligatorio para consultar su historial de ordenes.");
        }
        return trabajoTercerizadoVentaRepository.findByProveedorId(proveedorId);
    }

    public List<TrabajoTercerizadoVenta> listarPorProveedorYEstado(Long proveedorId, String estado) {
        if (proveedorId == null) {
            throw new IllegalArgumentException("El ID del proveedor es obligatorio.");
        }
        return trabajoTercerizadoVentaRepository.findByProveedorIdAndEstado(proveedorId, validarEstado(estado));
    }

    private TrabajoTercerizadoVenta validarTrabajoTercerizadoVenta(TrabajoTercerizadoVenta trabajo) {
        if (trabajo == null) {
            throw new IllegalArgumentException("La consulta de trabajo tercerizado de venta esta vacia.");
        }

        if (trabajo.getVenta() == null || trabajo.getVenta().getId() == null ||
            trabajo.getTrabajoTercerizado() == null || trabajo.getTrabajoTercerizado().getId() == null ||
            trabajo.getProveedor() == null || trabajo.getProveedor().getId() == null) {
            throw new IllegalArgumentException("Los campos venta, servicio tercerizado y proveedor son obligatorios.");
        }

        if (trabajo.getCantidad() == null || trabajo.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad requerida debe ser mayor a cero.");
        }

        if (trabajo.getEstado() == null || trabajo.getEstado().strip().isEmpty()) {
            trabajo.setEstado("pendiente");
        } else {
            trabajo.setEstado(validarEstado(trabajo.getEstado()));
        }

        BigDecimal precioUnitarioProveedor = costosEscalaRepository
            .encontrarCostoTercerizadoPorRango(trabajo.getTrabajoTercerizado().getId(), trabajo.getCantidad())
            .orElseThrow(() -> new RuntimeException("No se encontro una escala de precio configurada para esa cantidad del servicio tercerizado."));

        trabajo.setPrecioUnitarioHistorico(precioUnitarioProveedor);
        trabajo.setCostoTotalHistorico(trabajo.getCantidad().multiply(precioUnitarioProveedor).setScale(2, RoundingMode.HALF_UP));
        actualizarFechasPorEstado(trabajo, trabajo.getEstado());

        return trabajo;
    }

    private String validarEstado(String estado) {
        if (estado == null || estado.strip().isEmpty()) {
            throw new IllegalArgumentException("El estado del trabajo tercerizado es obligatorio.");
        }

        String estadoLimpio = estado.strip().toLowerCase();
        if (!estadosPermitidos.contains(estadoLimpio)) {
            throw new IllegalArgumentException("Estado de tercerizado invalido. Use: " + estadosPermitidos);
        }
        return estadoLimpio;
    }

    private void actualizarFechasPorEstado(TrabajoTercerizadoVenta trabajo, String estado) {
        LocalDateTime ahora = LocalDateTime.now();
        if ("enviado".equals(estado) && trabajo.getFechaEnvio() == null) {
            trabajo.setFechaEnvio(ahora);
        }
        if ("listo".equals(estado) && trabajo.getFechaFinalizacion() == null) {
            trabajo.setFechaFinalizacion(ahora);
        }
        if ("retirado".equals(estado) && trabajo.getFechaRetiro() == null) {
            trabajo.setFechaRetiro(ahora);
            if (trabajo.getFechaFinalizacion() == null) {
                trabajo.setFechaFinalizacion(ahora);
            }
        }
    }
}
