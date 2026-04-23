package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.HistorialPresupuesto;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.repository.HistorialPresupuestoRepository;
import com.drover.demo.backend.repository.VentaRepository;
import org.springframework.stereotype.Service;

import com.drover.demo.backend.exception.NegocioException;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HistorialPresupuestoService {

    private final HistorialPresupuestoRepository presupuestoRepository;
    private final VentaRepository ventaRepository;

    public HistorialPresupuestoService(HistorialPresupuestoRepository presupuestoRepository,
                                       VentaRepository ventaRepository) {
        this.presupuestoRepository = presupuestoRepository;
        this.ventaRepository = ventaRepository;
    }

    /**
     * 🥇 Guardar presupuesto
     */
    public HistorialPresupuesto guardar(HistorialPresupuesto presupuesto) {

        presupuesto.setCreatedAt(LocalDateTime.now());

        return presupuestoRepository.save(presupuesto);
    }

    /**
     * 🥈 Listar todos
     */
    public List<HistorialPresupuesto> listar() {
        return presupuestoRepository.findAll();
    }

    /**
     * 🥉 Buscar por cliente
     */
    public List<HistorialPresupuesto> buscarPorCliente(Integer clienteId) {
        return presupuestoRepository.findByClienteId(clienteId);
    }

    /**
     * 🏅 Buscar por estado
     */
    public List<HistorialPresupuesto> buscarPorEstado(String estado) {
        return presupuestoRepository.findByEstado(estado);
    }

    /**
     * 🏅 Buscar por fecha
     */
    public List<HistorialPresupuesto> buscarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        return presupuestoRepository.findByCreatedAtBetween(desde, hasta);
    }

    /**
     * 💣 Convertir presupuesto a venta
     */
    public Venta convertirAVenta(Integer presupuestoId) {

        HistorialPresupuesto presupuesto = presupuestoRepository.findById(presupuestoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Presupuesto no encontrado"));

        if (!presupuesto.getEstado().equals("PENDIENTE")) {
            throw new NegocioException("El presupuesto ya fue procesado");
        }

        // Crear venta
        // Crear venta
        Venta venta = new Venta();

        // 👇 usar ID del cliente
        venta.setClienteId(presupuesto.getClienteId());

        // ⚠️ este lo tenés que definir vos (usuario logueado por ejemplo)
        venta.setUsuarioId(1); // después lo mejoramos

        venta.setTotal(presupuesto.getTotal());

        // 👇 usar ENUM
        venta.setEstado(Venta.EstadoVenta.DISENAR);

        venta.setTieneDiseno(false);
        venta.setTieneTercerizado(false);

        venta.setCreatedAt(LocalDateTime.now());
                presupuestoRepository.save(presupuesto);

        return ventaRepository.save(venta);
    }

    /**
     * ❌ Cancelar presupuesto
     */
    public HistorialPresupuesto cancelar(Integer presupuestoId) {

        HistorialPresupuesto presupuesto = presupuestoRepository.findById(presupuestoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Presupuesto no encontrado"));

        presupuesto.setEstado("RECHAZADO");

        return presupuestoRepository.save(presupuesto);
    }

    /**
     * 📊 Contar presupuestos por estado (estadística básica)
     */
    public Integer contarPorEstado(String estado) {
        return presupuestoRepository.countByEstado(estado);
    }
}
