package com.drover.demo.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.PagoVenta;
import com.drover.demo.backend.repository.PagoVentaRepository;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.repository.VentaRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class PagoVentaService {

    private final PagoVentaRepository pagoVentaRepository;
    private final VentaRepository ventaRepository;
    private final VentaService ventasService; 

    public PagoVentaService(PagoVentaRepository pagoVentaRepository, 
                            VentaRepository ventaRepository, 
                            VentaService ventasService) {
        this.pagoVentaRepository = pagoVentaRepository;
        this.ventaRepository = ventaRepository;
        this.ventasService = ventasService;
    }
    
    @Transactional
    public PagoVenta guardar(PagoVenta pagoVenta) {
        PagoVenta pagoLimpio = validarPagoVenta(pagoVenta);
        
        Venta ventaBd = ventaRepository.findById(pagoLimpio.getVenta().getId())
            .orElseThrow(() -> new RuntimeException("La venta asociada al pago no existe."));

        List<PagoVenta> pagosPrevios = pagoVentaRepository.findByVentaId(ventaBd.getId());
        BigDecimal totalPagadoHistorico = BigDecimal.ZERO;
        
        for (PagoVenta p : pagosPrevios) {
            totalPagadoHistorico = totalPagadoHistorico.add(p.getMonto());
        }

        BigDecimal nuevoTotalPagado = totalPagadoHistorico.add(pagoLimpio.getMonto());

        if (nuevoTotalPagado.compareTo(ventaBd.getMontoTotal()) > 0) {
            BigDecimal saldoDeuda = ventaBd.getMontoTotal().subtract(totalPagadoHistorico);
            throw new IllegalArgumentException("Monto excedido. El cliente intenta pagar " + pagoLimpio.getMonto() 
                + " pero la deuda real de la venta es de " + saldoDeuda);
        }

        if (nuevoTotalPagado.compareTo(ventaBd.getMontoTotal()) == 0) {
            ventaBd.setEstadoPago("pagado");
        } else {
            ventaBd.setEstadoPago("señeado");
        }

        // 4. Persistimos el pago de forma física en SQL
        PagoVenta pagoGuardado = pagoVentaRepository.save(pagoLimpio);

        // Actualizamos el estado de la venta madre
        ventaRepository.save(ventaBd);
        
        return pagoGuardado;
    }

    @Transactional
    public PagoVenta editar(Long id, PagoVenta pVenta) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del pago es requerido para editar.");
        }

        PagoVenta pagoExistente = pagoVentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro de pago no encontrado con el ID: " + id));

        PagoVenta datosNuevos = validarPagoVenta(pVenta);

        pagoExistente.setMonto(datosNuevos.getMonto());
        pagoExistente.setMedioPago(datosNuevos.getMedioPago());
        pagoExistente.setObservaciones(datosNuevos.getObservaciones());
        pagoExistente.setFecha(datosNuevos.getFecha());

        return pagoVentaRepository.save(pagoExistente);
    }

    public List<PagoVenta> listar() {
        return pagoVentaRepository.findAll();
    }

    public List<PagoVenta> listarPorVenta(Long ventaId) {
        if (ventaId == null) {
            throw new IllegalArgumentException("El ID de la venta es requerido.");
        }
        return pagoVentaRepository.findByVentaId(ventaId);
    }

    public List<PagoVenta> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        // Validamos que ninguna de las dos variables llegue en null desde el controlador
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas 'desde' y 'hasta' son obligatorias para filtrar los pagos.");
        }
        
        // Freno de mano: Evitamos que pongan fechas invertidas (ej: desde lunes hasta el domingo anterior)
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha de inicio 'desde' no puede ser posterior a la fecha de fin 'hasta'.");
        }
        
        return pagoVentaRepository.findByFechaBetween(desde, hasta);
    }
   
    public List<PagoVenta> listarPorMedioPago(String medioPago) {
        if (medioPago == null || medioPago.strip().isEmpty()) {
            throw new IllegalArgumentException("El medio de pago es obligatorio para realizar el filtro.");
        }
        
        String medioPagoLimpio = medioPago.strip().toLowerCase();
        
        return pagoVentaRepository.findByMedioPago(medioPagoLimpio);
    }

    // --- MÉTODOS PRIVADOS ---
    
    private PagoVenta validarPagoVenta(PagoVenta pago) {
        if (pago == null) {
            throw new IllegalArgumentException("La consulta de pago está vacía.");
        }

        if (pago.getVenta() == null || pago.getVenta().getId() == null) {
            throw new IllegalArgumentException("El pago debe estar obligatoriamente vinculado a un comprobante de venta válido.");
        }

        if (pago.getMonto() == null || pago.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto a entregar es obligatorio y debe ser estrictamente mayor a 0.");
        }

        if (pago.getMedioPago() == null || pago.getMedioPago().strip().isEmpty()) {
            throw new IllegalArgumentException("El medio de pago es obligatorio.");
        }

        pago.setMedioPago(pago.getMedioPago().strip().toLowerCase());

        if (pago.getFecha() == null) {
            pago.setFecha(LocalDateTime.now()); 
        }

        return pago;
    }
}
