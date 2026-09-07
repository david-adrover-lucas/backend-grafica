package com.drover.demo.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drover.demo.backend.entity.Deuda;
import com.drover.demo.backend.entity.MovimientoCaja;
import com.drover.demo.backend.entity.PagoDeuda;
import com.drover.demo.backend.repository.PagoDeudaRepository;

@Service
public class PagoDeudaService {

    private final PagoDeudaRepository pagoDeudaRepository;
    private final DeudaService deudaService;
    private final MovimientoCajaService movimientoCajaService;

    public PagoDeudaService(PagoDeudaRepository pagoDeudaRepository, DeudaService deudaService,
                            MovimientoCajaService movimientoCajaService) {
        this.pagoDeudaRepository = pagoDeudaRepository;
        this.deudaService = deudaService;
        this.movimientoCajaService = movimientoCajaService;
    }

    @Transactional
    public PagoDeuda pagar(PagoDeuda pagoDeuda) {
        PagoDeuda limpio = validarPagoDeuda(pagoDeuda);
        Deuda deudaActualizada = deudaService.aplicarPago(limpio.getDeuda().getId(), limpio.getMonto());
        limpio.setDeuda(deudaActualizada);

        PagoDeuda pagoGuardado = pagoDeudaRepository.save(limpio);
        registrarMovimientoCaja(pagoGuardado, deudaActualizada);

        return pagoGuardado;
    }

    public List<PagoDeuda> listar() {
        return pagoDeudaRepository.findAll();
    }

    public List<PagoDeuda> listarPorDeuda(Long deudaId) {
        if (deudaId == null) {
            throw new IllegalArgumentException("El ID de deuda es obligatorio.");
        }
        return pagoDeudaRepository.findByDeudaId(deudaId);
    }

    public List<PagoDeuda> listarPorCuenta(Long cuentaId) {
        if (cuentaId == null) {
            throw new IllegalArgumentException("El ID de cuenta es obligatorio.");
        }
        return pagoDeudaRepository.findByCuentaId(cuentaId);
    }

    public List<PagoDeuda> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas desde y hasta son obligatorias.");
        }
        if (desde.isAfter(hasta)) {
            throw new IllegalArgumentException("La fecha desde no puede ser posterior a hasta.");
        }
        return pagoDeudaRepository.findByFechaBetween(desde, hasta);
    }

    private PagoDeuda validarPagoDeuda(PagoDeuda pagoDeuda) {
        if (pagoDeuda == null) {
            throw new IllegalArgumentException("El pago de deuda no puede estar vacio.");
        }
        if (pagoDeuda.getDeuda() == null || pagoDeuda.getDeuda().getId() == null) {
            throw new IllegalArgumentException("El pago debe estar asociado a una deuda valida.");
        }
        if (pagoDeuda.getCuenta() == null || pagoDeuda.getCuenta().getId() == null) {
            throw new IllegalArgumentException("El pago debe indicar la cuenta afectada.");
        }
        if (pagoDeuda.getMonto() == null || pagoDeuda.getMonto().signum() <= 0) {
            throw new IllegalArgumentException("El monto del pago debe ser mayor a cero.");
        }
        if (pagoDeuda.getFecha() == null) {
            pagoDeuda.setFecha(LocalDateTime.now());
        }
        return pagoDeuda;
    }

    private void registrarMovimientoCaja(PagoDeuda pago, Deuda deuda) {
        MovimientoCaja movimiento = new MovimientoCaja();
        movimiento.setCuenta(pago.getCuenta());
        movimiento.setTipo(esDeudaPorCobrar(deuda) ? "ingreso" : "egreso");
        movimiento.setConcepto("pago_deuda_" + deuda.getTipo());
        movimiento.setMonto(pago.getMonto());
        movimiento.setFecha(pago.getFecha());
        movimiento.setVenta(deuda.getVenta());
        movimiento.setCompra(deuda.getCompra());
        movimiento.setPagoDeuda(pago);
        movimiento.setObservaciones("Pago de deuda ID " + deuda.getId());

        movimientoCajaService.registrar(movimiento);
    }

    private boolean esDeudaPorCobrar(Deuda deuda) {
        return "cliente".equals(deuda.getTipo());
    }
}
