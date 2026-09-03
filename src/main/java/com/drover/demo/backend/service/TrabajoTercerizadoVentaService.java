package com.drover.demo.backend.service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.TrabajoTercerizadoVenta;
import com.drover.demo.backend.repository.TrabajoTercerizadoVentaRepository;
import com.drover.demo.backend.repository.CostosEscalaTercerizadoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.RoundingMode;

@Service
public class TrabajoTercerizadoVentaService {

    private final TrabajoTercerizadoVentaRepository trabajoTercerizadoVentaRepository;
    private final CostosEscalaTercerizadoRepository costosEscalaRepository;

    private final List<String> estadosPermitidos = List.of("enviado", "retirado");

    public TrabajoTercerizadoVentaService(TrabajoTercerizadoVentaRepository trabajoTercerizadoVentaRepository,
                                          CostosEscalaTercerizadoRepository costosEscalaRepository) {
        this.trabajoTercerizadoVentaRepository = trabajoTercerizadoVentaRepository;
        this.costosEscalaRepository = costosEscalaRepository;
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

        TrabajoTercerizadoVenta datosNuevos = validarTrabajoTercerizadoVenta(trabajo);

        // Traspasamos las propiedades permitidas sin tocar variables de fechas que se descartaron
        existente.setVenta(datosNuevos.getVenta());
        existente.setDetalleVenta(datosNuevos.getDetalleVenta());
        existente.setTrabajoTercerizado(datosNuevos.getTrabajoTercerizado());
        existente.setProveedor(datosNuevos.getProveedor());
        existente.setCantidad(datosNuevos.getCantidad());
        existente.setPrecioUnitarioHistorico(datosNuevos.getPrecioUnitarioHistorico());
        existente.setCostoTotalHistorico(datosNuevos.getCostoTotalHistorico());
        existente.setEstado(datosNuevos.getEstado());

        trabajoTercerizadoVentaRepository.save(existente);
    }

    public List<TrabajoTercerizadoVenta> listar() {
        return trabajoTercerizadoVentaRepository.findAll();
    }


    public List<TrabajoTercerizadoVenta> listarPorEstado(String estado) {
        if (estado == null || estado.strip().isEmpty()) {
            throw new IllegalArgumentException("El parámetro estado es estrictamente obligatorio para realizar el filtro.");
        }
        
        String estadoLimpio = estado.strip().toLowerCase();
        if (!estadosPermitidos.contains(estadoLimpio)) {
            throw new IllegalArgumentException("Estado inválido para tercerizados. Los únicos estados permitidos son: " + estadosPermitidos);
        }
        
        return trabajoTercerizadoVentaRepository.findByEstado(estadoLimpio);
    }


    public List<TrabajoTercerizadoVenta> listarPorProveedor(Long proveedorId) {
        if (proveedorId == null) {
            throw new IllegalArgumentException("El ID del proveedor es estrictamente obligatorio para consultar su historial de órdenes.");
        }
        return trabajoTercerizadoVentaRepository.findByProveedorId(proveedorId);
    }

    // --- MÉTODOS PRIVADOS DE CÁLCULO CONTABLE AUTOMÁTICO ---

    private TrabajoTercerizadoVenta validarTrabajoTercerizadoVenta(TrabajoTercerizadoVenta trabajo) {
        if (trabajo == null) {
            throw new IllegalArgumentException("La consulta de trabajo tercerizado de venta está vacía.");
        }

        if (trabajo.getVenta() == null || trabajo.getVenta().getId() == null ||
            trabajo.getTrabajoTercerizado() == null || trabajo.getTrabajoTercerizado().getId() == null ||
            trabajo.getProveedor() == null || trabajo.getProveedor().getId() == null) {
            throw new IllegalArgumentException("Los campos Venta, Servicio Tercerizado y Proveedor son campos obligatorios.");
        }

        if (trabajo.getCantidad() == null || trabajo.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad requerida debe ser estrictamente mayor a cero.");
        }

        // 1. Sanitización estricta del estado según tu regla de negocio
        if (trabajo.getEstado() == null || trabajo.getEstado().strip().isEmpty()) {
            trabajo.setEstado("enviado"); // Estado inicial lógico por defecto
        } else {
            trabajo.setEstado(trabajo.getEstado().strip().toLowerCase());
            if (!estadosPermitidos.contains(trabajo.getEstado())) {
                throw new IllegalArgumentException("Estado de tercerización '" + trabajo.getEstado() + "' no permitido. Use: " + estadosPermitidos);
            }
        }

        BigDecimal precioUnitarioProveedor = costosEscalaRepository
            .encontrarCostoTercerizadoPorRango(trabajo.getTrabajoTercerizado().getId(), trabajo.getCantidad())
            .orElseThrow(() -> new RuntimeException("No se encontró una escala de precio configurada para esa cantidad del servicio tercerizado con el proveedor seleccionado."));

        // Congelamos el precio unitario histórico cobrado por el taller
        trabajo.setPrecioUnitarioHistorico(precioUnitarioProveedor);

        // Matemática automática: Costo Total a pagar = Cantidad * Precio Unitario Proveedor
        BigDecimal totalCalculado = trabajo.getCantidad().multiply(trabajo.getPrecioUnitarioHistorico()).setScale(2, RoundingMode.HALF_UP);
        trabajo.setCostoTotalHistorico(totalCalculado);

        // 🌟 NOTA REQUERIDA: Forzamos la omisión de las variables de fecha asegurándonos de que se guarden limpias o nulas
        trabajo.setFechaEnvio(null);
        trabajo.setFechaFinalizacion(null);

        return trabajo;
    }
}
