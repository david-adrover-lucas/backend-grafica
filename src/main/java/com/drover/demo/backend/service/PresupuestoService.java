package com.drover.demo.backend.service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.Presupuesto;
import com.drover.demo.backend.repository.PresupuestoRepository;
import com.drover.demo.backend.entity.DetallePresupuestos;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final List<String> estadosPermitido = List.of("confirmado", "cancelado", "pendiente");

    public PresupuestoService(PresupuestoRepository presupuestoRepository) {
        this.presupuestoRepository = presupuestoRepository;
    }
    
    @Transactional
    public void guardar(Presupuesto presupuesto) {
        Presupuesto presupuestoLimpio = validarPresupuesto(presupuesto);
        
        if (presupuestoLimpio.getNroPresupuesto() == null || presupuestoLimpio.getNroPresupuesto().strip().isEmpty()) {
            presupuestoLimpio.setNroPresupuesto("PRE-" + System.currentTimeMillis());
        } else {
            if (presupuestoRepository.existsByNroPresupuesto(presupuestoLimpio.getNroPresupuesto())) {
                throw new IllegalArgumentException("El número de presupuesto '" + presupuestoLimpio.getNroPresupuesto() + "' ya se encuentra registrado.");
            }
        }

        presupuestoRepository.save(presupuestoLimpio);
    }

    @Transactional
    public void editar(Presupuesto presupuesto) {
        if (presupuesto == null || presupuesto.getId() == null) {
            throw new IllegalArgumentException("Debes proveer un presupuesto con un ID válido para editar.");
        }

        Presupuesto presupuestoExistente = presupuestoRepository.findById(presupuesto.getId())
        .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado con el ID: " + presupuesto.getId()));

        Presupuesto datosNuevosLimpios = validarPresupuesto(presupuesto);

        presupuestoExistente.setCliente(datosNuevosLimpios.getCliente());
        presupuestoExistente.setRevendedor(datosNuevosLimpios.getRevendedor());
        presupuestoExistente.setResponsable(datosNuevosLimpios.getResponsable());
        presupuestoExistente.setFecha(datosNuevosLimpios.getFecha());
        presupuestoExistente.setEstado(datosNuevosLimpios.getEstado());
        presupuestoExistente.setObservaciones(datosNuevosLimpios.getObservaciones());
        presupuestoExistente.setMontoTotal(datosNuevosLimpios.getMontoTotal());

        presupuestoExistente.getDetalles().clear();
        if (datosNuevosLimpios.getDetalles() != null) {
            for (DetallePresupuestos nuevoDetalle : datosNuevosLimpios.getDetalles()) {
                presupuestoExistente.getDetalles().add(nuevoDetalle);
                nuevoDetalle.setPresupuesto(presupuestoExistente); 
            }
        }

        presupuestoRepository.save(presupuestoExistente);
    }

    public List<Presupuesto> listar() {
        return presupuestoRepository.findAll();
    }

    public List<Presupuesto> listarPorEsatado(String estado) {
        if (estado == null || estado.strip().isEmpty()) {
            throw new IllegalArgumentException("El parámetro estado no puede estar vacío.");
        }
        String estadoLimpio = estado.strip().toLowerCase();
        if (!estadosPermitido.contains(estadoLimpio)) {
            throw new IllegalArgumentException("Estado inválido. Los estados permitidos son: " + estadosPermitido);
        }
        return presupuestoRepository.findByEstado(estadoLimpio);
    }

    public List<Presupuesto> listarPorCliente(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente es obligatorio para realizar el filtro.");
        }
        return presupuestoRepository.findByClienteId(clienteId);
    }
    
    public List<Presupuesto> listarPorRevendedor(Long revendedorId) {
        if (revendedorId == null) {
            throw new IllegalArgumentException("El ID del revendedor es obligatorio para realizar el filtro.");
        }
        return presupuestoRepository.findByRevendedorId(revendedorId);
    }

    public List<Presupuesto> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas provistas para el rango no son válidas.");
        }
        return presupuestoRepository.findByFechaBetween(desde, hasta);
    }

    @Transactional
    public void cambiarEstado(Long id, String estado) {
        if (id == null || estado == null || estado.strip().isEmpty()) {
            throw new IllegalArgumentException("El ID y el nuevo estado son campos obligatorios.");
        }
        String estadoLimpio = estado.strip().toLowerCase();
        if (!estadosPermitido.contains(estadoLimpio)) {
            throw new IllegalArgumentException("Estado inválido. No se puede cambiar a: " + estadoLimpio);
        }

        Presupuesto presupuesto = presupuestoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Presupuesto no encontrado con el ID: " + id));
            
        presupuesto.setEstado(estadoLimpio);
        presupuestoRepository.save(presupuesto);
    }
    
    // --- MÉTODOS PRIVADOS ---
    
    private Presupuesto validarPresupuesto(Presupuesto presupuesto) {
        if (presupuesto == null) {
            throw new IllegalArgumentException("La consulta de presupuesto está vacía.");
        }

        boolean tieneCliente = (presupuesto.getCliente() != null && presupuesto.getCliente().getId() != null);
        boolean tieneRevendedor = (presupuesto.getRevendedor() != null && presupuesto.getRevendedor().getId() != null);

        if (!tieneCliente && !tieneRevendedor) {
            throw new IllegalArgumentException("El presupuesto debe estar asignado obligatoriamente a un Cliente o a un Revendedor.");
        }
        if (tieneCliente && tieneRevendedor) {
            throw new IllegalArgumentException("Conflicto comercial: El presupuesto no puede pertenecer a un Cliente y a un Revendedor al mismo tiempo.");
        }

        if (presupuesto.getResponsable() == null || presupuesto.getResponsable().getId() == null) {
            throw new IllegalArgumentException("Se requiere un usuario responsable (Vendedor/Empleado) asignado a la cotización.");
        }

        if (presupuesto.getFecha() == null) {
            presupuesto.setFecha(LocalDateTime.now()); // Marcamos el momento actual por defecto
        }

        if (presupuesto.getEstado() == null || presupuesto.getEstado().strip().isEmpty()) {
            presupuesto.setEstado("pendiente"); // Estado inicial natural
        } else {
            presupuesto.setEstado(presupuesto.getEstado().strip().toLowerCase());
            if (!estadosPermitido.contains(presupuesto.getEstado())) {
                throw new IllegalArgumentException("Estado inicial '" + presupuesto.getEstado() + "' no permitido. Use: " + estadosPermitido);
            }
        }

        if (presupuesto.getDetalles() == null || presupuesto.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Un presupuesto no puede procesarse sin al menos un producto en su detalle.");
        }

        for (DetallePresupuestos detalle : presupuesto.getDetalles()) {
            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new IllegalArgumentException("Cada renglón del detalle debe apuntar a un producto válido del catálogo.");
            }
            if (detalle.getCantidad() == null || detalle.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("La cantidad solicitada de cada producto debe ser estrictamente mayor a cero.");
            }
            if (detalle.getSubtotal() == null || detalle.getSubtotal().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El subtotal de cada renglón es obligatorio y no puede ser negativo.");
            }
            
            // Amarramos el renglón al presupuesto padre
            detalle.setPresupuesto(presupuesto);
        }

        BigDecimal totalCalculado = calcularMontoTotal(presupuesto.getDetalles());
        presupuesto.setMontoTotal(totalCalculado);

        return presupuesto;
    }

    private BigDecimal calcularMontoTotal(List<DetallePresupuestos> detalles) {
        BigDecimal acumulador = BigDecimal.ZERO;
        for (DetallePresupuestos detalle : detalles) {
          acumulador = acumulador.add(detalle.getSubtotal());
        }
        return acumulador;
    }
}
