package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Presupuesto;
import com.drover.demo.backend.repository.PresupuestoRepository;

@Service
public class PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;

    public PresupuestoService(PresupuestoRepository presupuestoRepository) {
        this.presupuestoRepository = presupuestoRepository;
    }

    public List<Presupuesto> listar() {}
    public Optional<Presupuesto> buscarPorId(Long id) { }
    public Presupuesto guardar(Presupuesto presupuesto) { }
    public void eliminarPorId(Long id) {}

    private BigDecimal calcular monto_total(){}
}
