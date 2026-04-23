package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.Comision;
import com.drover.demo.backend.repository.ComisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ComisionService {

    private final ComisionRepository comisionRepository;

    public ComisionService(ComisionRepository comisionRepository) {
        this.comisionRepository = comisionRepository;
    }

    public Comision guardar(Comision comision) {
        return comisionRepository.save(comision);
    }

    public List<Comision> listar() {
        return comisionRepository.findAll();
    }

    public List<Comision> obtenerPorUsuario(Integer usuarioId) {
        return comisionRepository.findByUsuarioId(usuarioId);
    }

    @Transactional  // ✅ evita el error de transacción
    public void eliminar(Integer id) {
        comisionRepository.deleteById(id);
    }

    public BigDecimal calcularPorcentaje(BigDecimal totalVenta, BigDecimal porcentaje) {
        if (totalVenta == null || porcentaje == null) return BigDecimal.ZERO;
        return totalVenta.multiply(porcentaje).divide(BigDecimal.valueOf(100));
    }

    public BigDecimal calcularMontoFijo(BigDecimal montoFijo) {
        return montoFijo != null ? montoFijo : BigDecimal.ZERO;
    }

    public BigDecimal totalComisionesUsuario(Integer usuarioId) {
        List<Comision> comisiones = comisionRepository.findByUsuarioId(usuarioId);
        return comisiones.stream()
                .map(c -> c.getMontoFijo() != null ? c.getMontoFijo() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}