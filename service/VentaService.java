package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.repository.VentaRepository;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    /**
     * 🥇 Crear venta
     */
    public Venta crearVenta(Venta venta) {


        if (venta.getTieneDiseno() == null) {
            venta.setTieneDiseno(false);
        }

        if (venta.getTieneTercerizado() == null) {
            venta.setTieneTercerizado(false);
        }
        venta.setCreatedAt(LocalDateTime.now());

        return ventaRepository.save(venta);
    }

    /**
     * 🥈 Listar ventas
     */
    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    /**
     * 🥉 Buscar por cliente
     */
    public List<Venta> buscarPorCliente(Integer clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    /**
     * 🏅 Buscar por usuario
     */
    public List<Venta> buscarPorUsuario(Integer usuarioId) {
        return ventaRepository.findByUsuarioId(usuarioId);
    }

    /**
     * 🏅 Buscar por estado
     */
    public List<Venta> buscarPorEstado(Venta.EstadoVenta estado) {
        return ventaRepository.findByEstado(estado);
    }

    /**
     * 💣 Cambiar estado (flujo del tablero)
     */
    public Venta cambiarEstado(Integer ventaId, Venta.EstadoVenta nuevoEstado) {

        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada"));

        venta.setEstado(nuevoEstado);

        return ventaRepository.save(venta);
    }

    /**
     * 💰 Actualizar total (cuando agregás productos)
     */
    public Venta actualizarTotal(Integer ventaId, BigDecimal nuevoTotal) {

        Venta venta = ventaRepository.findById(ventaId)
               .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada"));

        venta.setTotal(nuevoTotal);

        return ventaRepository.save(venta);
    }

    /**
     * ❌ Cancelar venta
     */
    public Venta cancelar(Integer ventaId) {

        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada"));

        venta.setEstado(Venta.EstadoVenta.ENTREGA); // o CANCELADO si agregás

        return ventaRepository.save(venta);
    }
}
