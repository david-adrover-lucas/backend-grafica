package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


import org.springframework.stereotype.Service;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.entity.Presupuesto;
import com.drover.demo.backend.entity.PresupuestoDetalle;
import com.drover.demo.backend.repository.PresupuestoDetalleRepository;
import com.drover.demo.backend.repository.PresupuestoRepository;

@Service
public class PresupuestoService {

    private final PresupuestoRepository presupuestoRepository;
    private final PresupuestoDetalleRepository detalleRepository;

    public PresupuestoService(PresupuestoRepository presupuestoRepository,
                              PresupuestoDetalleRepository detalleRepository) {
        this.presupuestoRepository = presupuestoRepository;
        this.detalleRepository = detalleRepository;
    }

    /**
     * 🥇 Crear presupuesto
     */
    public Presupuesto crear(Presupuesto presupuesto) {


        // generar código automático
        presupuesto.setCodigo("PRES-" + System.currentTimeMillis());

        presupuesto.setTotal(BigDecimal.ZERO);
        presupuesto.setCreatedAt(LocalDateTime.now());

        return presupuestoRepository.save(presupuesto);
    }

    /**
     * 🥈 Listar todos
     */
    public List<Presupuesto> listar() {
        return presupuestoRepository.findAll();
    }

    /**
     * 🥉 Buscar por cliente
     */
    public List<Presupuesto> buscarPorCliente(Integer clienteId) {
        return presupuestoRepository.findByClienteId(clienteId);
    }

    /**
     * 💣 Calcular total del presupuesto
     */
    public Presupuesto calcularTotal(Integer presupuestoId) {

        Presupuesto presupuesto = presupuestoRepository.findById(presupuestoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Presupuesto no encontrado"));

        List<PresupuestoDetalle> detalles =
                detalleRepository.findByPresupuestoId(presupuestoId);

        BigDecimal total = BigDecimal.ZERO;

        for (PresupuestoDetalle d : detalles) {
            total = total.add(d.getPrecio());
        }

        presupuesto.setTotal(total);

        return presupuestoRepository.save(presupuesto);
    }

    /**
     * ❌ Eliminar presupuesto
     */
    public void eliminar(Integer id) {
        presupuestoRepository.deleteById(id);
    }
}
