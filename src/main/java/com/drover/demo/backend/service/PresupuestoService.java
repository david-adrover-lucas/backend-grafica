package com.drover.demo.backend.service;

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

    public List<Presupuesto> listar() {
        return presupuestoRepository.findAll();
    }

    public Optional<Presupuesto> buscarPorId(Long id) {
        return presupuestoRepository.findById(id);
    }

    public Presupuesto guardar(Presupuesto presupuesto) {
        return presupuestoRepository.save(presupuesto);
    }

    public void eliminarPorId(Long id) {
        presupuestoRepository.deleteById(id);
    }
}
