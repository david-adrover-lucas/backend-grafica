package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drover.demo.backend.entity.Deuda;
import com.drover.demo.backend.entity.DetalleVenta;
import com.drover.demo.backend.entity.Empleado;
import com.drover.demo.backend.entity.Persona;
import com.drover.demo.backend.entity.TrabajoTercerizadoVenta;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.repository.DeudaRepository;
import com.drover.demo.backend.repository.EmpleadoRepository;
import com.drover.demo.backend.repository.PersonaRepository;
import com.drover.demo.backend.repository.TrabajoTercerizadoVentaRepository;
import com.drover.demo.backend.repository.VentaRepository;

@Service
public class DeudaService {

    private final DeudaRepository deudaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PersonaRepository personaRepository;
    private final VentaRepository ventaRepository;
    private final TrabajoTercerizadoVentaRepository trabajoTercerizadoVentaRepository;
    private final List<String> tiposPermitidos = List.of("cliente", "proveedor_insumo", "proveedor_tercerizado", "sueldo", "comision");
    private final List<String> estadosPermitidos = List.of("pendiente", "parcial", "pagada", "cancelada");

    public DeudaService(DeudaRepository deudaRepository, EmpleadoRepository empleadoRepository,
                        PersonaRepository personaRepository, VentaRepository ventaRepository,
                        TrabajoTercerizadoVentaRepository trabajoTercerizadoVentaRepository) {
        this.deudaRepository = deudaRepository;
        this.empleadoRepository = empleadoRepository;
        this.personaRepository = personaRepository;
        this.ventaRepository = ventaRepository;
        this.trabajoTercerizadoVentaRepository = trabajoTercerizadoVentaRepository;
    }

    @Transactional
    public Deuda guardar(Deuda deuda) {
        Deuda deudaLimpia = validarDeuda(deuda);
        return deudaRepository.save(deudaLimpia);
    }

    @Transactional
    public Deuda cancelar(Long id) {
        Deuda deuda = deudaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Deuda no encontrada con el ID: " + id));

        if ("pagada".equals(deuda.getEstado())) {
            throw new IllegalStateException("Una deuda pagada no puede cancelarse.");
        }

        deuda.setEstado("cancelada");
        return deudaRepository.save(deuda);
    }

    @Transactional
    public Deuda generarSueldoMensual(Long empleadoId, Integer anio, Integer mes) {
        if (empleadoId == null || anio == null || mes == null) {
            throw new IllegalArgumentException("Empleado, anio y mes son obligatorios para generar sueldo.");
        }
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }

        Empleado empleado = empleadoRepository.findById(empleadoId)
            .orElseThrow(() -> new RuntimeException("Empleado no encontrado con el ID: " + empleadoId));

        if (!Boolean.TRUE.equals(empleado.getActivo())) {
            throw new IllegalStateException("No se puede generar sueldo para un empleado inactivo.");
        }
        if (empleado.getSueldoMensual() == null || empleado.getSueldoMensual().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El empleado no tiene un sueldo mensual valido configurado.");
        }

        String periodo = String.format("sueldo-%04d-%02d", anio, mes);
        if (deudaRepository.existsByPersonaIdAndTipoAndObservaciones(empleadoId, "sueldo", periodo)) {
            throw new IllegalStateException("Ya existe una deuda de sueldo para ese empleado y periodo.");
        }

        Deuda deuda = new Deuda();
        deuda.setPersona(empleado);
        deuda.setTipo("sueldo");
        deuda.setMontoTotal(empleado.getSueldoMensual());
        deuda.setFechaGeneracion(LocalDate.of(anio, mes, 1).atStartOfDay());
        deuda.setFechaVencimiento(LocalDate.of(anio, mes, 1).withDayOfMonth(LocalDate.of(anio, mes, 1).lengthOfMonth()).atTime(LocalTime.MAX));
        deuda.setObservaciones(periodo);

        return guardar(deuda);
    }

    @Transactional
    public Deuda generarComisionSemanal(Long personaId, LocalDate desde, LocalDate hasta) {
        if (personaId == null || desde == null || hasta == null) {
            throw new IllegalArgumentException("Persona, fecha desde y fecha hasta son obligatorias para generar comision.");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a hasta.");
        }

        Persona persona = personaRepository.findById(personaId)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + personaId));

        if (!Boolean.TRUE.equals(persona.getActivo())) {
            throw new IllegalStateException("No se puede generar comision para una persona inactiva.");
        }

        String periodo = "comision-" + desde + "-a-" + hasta;
        if (deudaRepository.existsByPersonaIdAndTipoAndObservaciones(personaId, "comision", periodo)) {
            throw new IllegalStateException("Ya existe una deuda de comision para esa persona y semana.");
        }

        BigDecimal totalComision = BigDecimal.ZERO;
        List<Venta> ventasEntregadas = ventaRepository.findByResponsableIdAndEstadoVentaAndFechaVentaBetween(
            personaId, "entregado", desde.atStartOfDay(), hasta.atTime(LocalTime.MAX));

        for (Venta venta : ventasEntregadas) {
            if (deudaRepository.existsByVentaIdAndPersonaIdAndTipo(venta.getId(), personaId, "comision")) {
                continue;
            }
            for (DetalleVenta detalle : venta.getDetalles()) {
                if (detalle.getMontoComisionHistorico() != null) {
                    totalComision = totalComision.add(detalle.getMontoComisionHistorico());
                }
            }
        }

        if (totalComision.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("No hay comisiones pendientes para esa persona en el periodo indicado.");
        }

        Deuda deuda = new Deuda();
        deuda.setPersona(persona);
        deuda.setTipo("comision");
        deuda.setMontoTotal(totalComision);
        deuda.setFechaGeneracion(LocalDateTime.now());
        deuda.setFechaVencimiento(hasta.atTime(LocalTime.MAX));
        deuda.setObservaciones(periodo);

        return guardar(deuda);
    }

    @Transactional
    public Deuda generarDeudaTercerizado(Long trabajoTercerizadoVentaId) {
        if (trabajoTercerizadoVentaId == null) {
            throw new IllegalArgumentException("El ID del trabajo tercerizado es obligatorio.");
        }
        if (deudaRepository.existsByTrabajoTercerizadoVentaIdAndTipo(trabajoTercerizadoVentaId, "proveedor_tercerizado")) {
            throw new IllegalStateException("Ese trabajo tercerizado ya tiene una deuda generada.");
        }

        TrabajoTercerizadoVenta trabajo = trabajoTercerizadoVentaRepository.findById(trabajoTercerizadoVentaId)
            .orElseThrow(() -> new RuntimeException("Trabajo tercerizado no encontrado con el ID: " + trabajoTercerizadoVentaId));

        if (!"retirado".equals(trabajo.getEstado())) {
            throw new IllegalStateException("La deuda al proveedor tercerizado se genera cuando el trabajo esta retirado.");
        }

        Deuda deuda = new Deuda();
        deuda.setProveedor(trabajo.getProveedor());
        deuda.setVenta(trabajo.getVenta());
        deuda.setTrabajoTercerizadoVenta(trabajo);
        deuda.setTipo("proveedor_tercerizado");
        deuda.setMontoTotal(trabajo.getCostoTotalHistorico());
        deuda.setFechaGeneracion(LocalDateTime.now());
        deuda.setObservaciones("trabajo-tercerizado-venta-" + trabajo.getId());

        return guardar(deuda);
    }

    public List<Deuda> listar() {
        return deudaRepository.findAll();
    }

    public List<Deuda> listarPorEstado(String estado) {
        String estadoLimpio = validarTexto(estado, "estado");
        if (!estadosPermitidos.contains(estadoLimpio)) {
            throw new IllegalArgumentException("Estado de deuda invalido. Use: " + estadosPermitidos);
        }
        return deudaRepository.findByEstado(estadoLimpio);
    }

    public List<Deuda> listarPorTipo(String tipo) {
        String tipoLimpio = validarTipo(tipo);
        return deudaRepository.findByTipo(tipoLimpio);
    }

    public List<Deuda> listarPorPersona(Long personaId) {
        if (personaId == null) {
            throw new IllegalArgumentException("El ID de persona es obligatorio.");
        }
        return deudaRepository.findByPersonaId(personaId);
    }

    public List<Deuda> listarPorProveedor(Long proveedorId) {
        if (proveedorId == null) {
            throw new IllegalArgumentException("El ID de proveedor es obligatorio.");
        }
        return deudaRepository.findByProveedorId(proveedorId);
    }

    public List<Deuda> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        validarRangoFechas(desde, hasta);
        return deudaRepository.findByFechaGeneracionBetween(desde, hasta);
    }

    Deuda aplicarPago(Long deudaId, BigDecimal monto) {
        Deuda deuda = deudaRepository.findById(deudaId)
            .orElseThrow(() -> new RuntimeException("Deuda no encontrada con el ID: " + deudaId));

        if ("cancelada".equals(deuda.getEstado()) || "pagada".equals(deuda.getEstado())) {
            throw new IllegalStateException("La deuda no permite nuevos pagos porque esta " + deuda.getEstado() + ".");
        }
        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }
        if (monto.compareTo(deuda.getSaldoPendiente()) > 0) {
            throw new IllegalArgumentException("El pago supera el saldo pendiente de la deuda.");
        }

        BigDecimal nuevoPagado = deuda.getMontoPagado().add(monto);
        BigDecimal nuevoSaldo = deuda.getMontoTotal().subtract(nuevoPagado);

        deuda.setMontoPagado(nuevoPagado);
        deuda.setSaldoPendiente(nuevoSaldo);
        deuda.setEstado(nuevoSaldo.compareTo(BigDecimal.ZERO) == 0 ? "pagada" : "parcial");

        return deudaRepository.save(deuda);
    }

    private Deuda validarDeuda(Deuda deuda) {
        if (deuda == null) {
            throw new IllegalArgumentException("La deuda no puede estar vacia.");
        }

        deuda.setTipo(validarTipo(deuda.getTipo()));

        if (deuda.getMontoTotal() == null || deuda.getMontoTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto total de la deuda debe ser mayor a cero.");
        }

        if (deuda.getMontoPagado() == null) {
            deuda.setMontoPagado(BigDecimal.ZERO);
        }
        if (deuda.getMontoPagado().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El monto pagado no puede ser negativo.");
        }
        if (deuda.getMontoPagado().compareTo(deuda.getMontoTotal()) > 0) {
            throw new IllegalArgumentException("El monto pagado no puede superar el monto total.");
        }

        validarTitularDeuda(deuda);

        deuda.setSaldoPendiente(deuda.getMontoTotal().subtract(deuda.getMontoPagado()));
        if (deuda.getSaldoPendiente().compareTo(BigDecimal.ZERO) == 0) {
            deuda.setEstado("pagada");
        } else if (deuda.getMontoPagado().compareTo(BigDecimal.ZERO) > 0) {
            deuda.setEstado("parcial");
        } else {
            deuda.setEstado("pendiente");
        }

        if (deuda.getFechaGeneracion() == null) {
            deuda.setFechaGeneracion(LocalDateTime.now());
        }

        return deuda;
    }

    private void validarTitularDeuda(Deuda deuda) {
        boolean tienePersona = deuda.getPersona() != null && deuda.getPersona().getId() != null;
        boolean tieneProveedor = deuda.getProveedor() != null && deuda.getProveedor().getId() != null;

        if ("cliente".equals(deuda.getTipo()) || "sueldo".equals(deuda.getTipo()) || "comision".equals(deuda.getTipo())) {
            if (!tienePersona || tieneProveedor) {
                throw new IllegalArgumentException("Las deudas de cliente, sueldo o comision deben estar asociadas a una persona y no a un proveedor.");
            }
            return;
        }

        if (!tieneProveedor || tienePersona) {
            throw new IllegalArgumentException("Las deudas a proveedores deben estar asociadas a un proveedor y no a una persona.");
        }
    }

    private String validarTipo(String tipo) {
        String tipoLimpio = validarTexto(tipo, "tipo");
        if (!tiposPermitidos.contains(tipoLimpio)) {
            throw new IllegalArgumentException("Tipo de deuda invalido. Use: " + tiposPermitidos);
        }
        return tipoLimpio;
    }

    private void validarRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas desde y hasta son obligatorias.");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a hasta.");
        }
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.strip().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio.");
        }
        return texto.strip().toLowerCase();
    }
}
