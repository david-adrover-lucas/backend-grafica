package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drover.demo.backend.entity.Caja;
import com.drover.demo.backend.exception.NegocioException;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;
import com.drover.demo.backend.repository.CajaRepository;

@Service
public class CajaService {

    private final CajaRepository repository;

    public CajaService(CajaRepository repository) {
        this.repository = repository;
    }

    // 📦 Crear caja si no existe
    private Caja obtenerOCrear(Integer cuentaId) {
        return repository.findByCuentaId(cuentaId)
                .orElseGet(() -> {
                    Caja nueva = new Caja();
                    nueva.setCuentaId(cuentaId);
                    nueva.setSaldo(BigDecimal.ZERO);
                    return repository.save(nueva);
                });
    }

    // ➕ SUMAR SALDO
    @Transactional
    public void sumarSaldo(Integer cuentaId, BigDecimal monto) {

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("Monto inválido");
        }

        Caja caja = obtenerOCrear(cuentaId);

        caja.setSaldo(caja.getSaldo().add(monto));

        repository.save(caja);
    }

    // ➖ RESTAR SALDO
    @Transactional
    public void restarSaldo(Integer cuentaId, BigDecimal monto) {

        if (monto == null || monto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("Monto inválido");
        }

        Caja caja = obtenerOCrear(cuentaId);

        if (caja.getSaldo().compareTo(monto) < 0) {
            throw new NegocioException("Saldo insuficiente");
        }

        caja.setSaldo(caja.getSaldo().subtract(monto));

        repository.save(caja);
    }

    // 📊 OBTENER CAJA
    public Caja obtenerCaja(Integer cuentaId) {
        return repository.findByCuentaId(cuentaId)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Caja no encontrada para cuentaId: " + cuentaId)
                );
    }

    // 📊 LISTAR TODAS
    public List<Caja> listarCajas() {
        return repository.findAll();
    }

    // ❌ ELIMINAR
    @Transactional
    public void eliminarCaja(Integer cuentaId) {
        repository.deleteByCuentaId(cuentaId);
    }
}



