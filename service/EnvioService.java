package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.Envio;
import com.drover.demo.backend.repository.EnvioRespository;
import com.drover.demo.backend.repository.VentaRepository;
import org.springframework.stereotype.Service;

import com.drover.demo.backend.exception.NegocioException;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class EnvioService {

    private final EnvioRespository envioRepository;
    private final VentaRepository ventaRepository;

    public EnvioService(EnvioRespository envioRepository,
                        VentaRepository ventaRepository) {
        this.envioRepository = envioRepository;
        this.ventaRepository = ventaRepository;
    }

    /**
     * 🥇 Crear envío (cuando pasa a entrega)
     */
    public Envio crearEnvio(Integer ventaId, Envio.TipoEnvio tipo) {
        ventaRepository.findById(ventaId) 
        .orElseThrow(() -> new RecursoNoEncontradoException("Venta no encontrada"));
        Envio envio = new Envio();
        envio.setVentaId(ventaId); // pasas el Integer directamente, sin buscar la Venta        envio.setTipo(tipo);
        envio.setEstado(Envio.EstadoEnvio.PENDIENTE);
        envio.setCreatedAt(LocalDateTime.now());
        return envioRepository.save(envio);
    }

    /**
     * 🥈 Marcar como enviado (solo si es envío)
     */
    public Envio marcarComoEnviado(Integer envioId) {

        Envio envio = envioRepository.findById(envioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Envio no encontrado"));

        if (envio.getTipo() != Envio.TipoEnvio.ENVIO) {
            throw new NegocioException("No es un envío a domicilio");
        }

        envio.setEstado(Envio.EstadoEnvio.ENVIADO);

        return envioRepository.save(envio);
    }

    /**
     * 🥉 Marcar como entregado
     */
    public Envio marcarComoEntregado(Integer envioId) {

        Envio envio = envioRepository.findById(envioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Envio no encontrado"));

        envio.setEstado(Envio.EstadoEnvio.ENTREGADO);
        envio.setFechaEntrega(LocalDateTime.now());

        return envioRepository.save(envio);
    }

    /**
     * 🏅 Cancelar envío
     */
    public Envio cancelarEnvio(Integer envioId) {

        Envio envio = envioRepository.findById(envioId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Envio no encontrado"));

        envio.setEstado(Envio.EstadoEnvio.CANCELADO);

        return envioRepository.save(envio);
    }

    /**
     * 📦 Ver envíos de una venta
     */
    public List<Envio> obtenerPorVenta(Integer ventaId) {
        return envioRepository.findByVentaId(ventaId);
    }

    /**
     * 📦 Listar por estado
     */
    public List<Envio> listarPorEstado(Envio.EstadoEnvio estado) {
        return envioRepository.findByEstado(estado);
    }
    public List<Envio> listar() {
        return envioRepository.findAll();
    }
    public Envio guardaEnvio(Envio envio) {
        return envioRepository.save(envio);
    }


}
