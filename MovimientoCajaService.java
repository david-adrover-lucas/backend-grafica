package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;


import com.drover.demo.backend.entity.MovimientoCaja;
import com.drover.demo.backend.exception.ValidacionException;
import com.drover.demo.backend.repository.MovimientoCajaRepository;

import jakarta.transaction.Transactional;

@Service
public class MovimientoCajaService {

    private final MovimientoCajaRepository repository;
    private final CajaService cajaService;

    public MovimientoCajaService(MovimientoCajaRepository repository,
                                 CajaService cajaService) {
        this.repository = repository;
        this.cajaService = cajaService;
    }

    // 💣 REGISTRAR MOVIMIENTO REAL
    @Transactional
    public MovimientoCaja registrarMovimiento(MovimientoCaja mov) {

        // 🔹 VALIDACIONES
        if (mov.getCuentaId() == null) {
            throw new ValidacionException("Cuenta obligatoria");
        }

        if (mov.getMonto() == null || mov.getMonto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("Monto inválido");
        }

        if (mov.getTipo() == null) {
            throw new ValidacionException("Tipo obligatorio");
        }

        // 🔥 IMPACTO EN CAJA
        if (mov.getTipo() == MovimientoCaja.TipoMovimiento.INGRESO) {
            cajaService.sumarSaldo(mov.getCuentaId(), mov.getMonto());
        } else if (mov.getTipo() == MovimientoCaja.TipoMovimiento.EGRESO) {
            cajaService.restarSaldo(mov.getCuentaId(), mov.getMonto());
        }

        // 🕒 AUDITORÍA
        mov.setCreatedAt(LocalDateTime.now());

        return repository.save(mov);
    }

    // 📊 LISTAR POR CUENTA
    public List<MovimientoCaja> listarPorCuenta(Integer cuentaId) {
        return repository.findByCuentaId(cuentaId);
    }

    // 📊 LISTAR POR TIPO
    public List<MovimientoCaja> listarPorTipo(MovimientoCaja.TipoMovimiento tipo) {
        return repository.findByTipo(tipo);
    }

    // 📊 LISTAR POR VENTA
    public List<MovimientoCaja> listarPorVenta(Integer ventaId) {
        return repository.findByVentaId(ventaId);
    }
}
