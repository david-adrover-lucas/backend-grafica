package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.TrabajoTercerizado;
import com.drover.demo.backend.repository.TrabajoTercerizadoRepository;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TrabajoTercerizadoService {

    private final TrabajoTercerizadoRepository trabajoRepository;

    public TrabajoTercerizadoService(TrabajoTercerizadoRepository trabajoRepository) {
        this.trabajoRepository = trabajoRepository;
    }

    // 🥇 Registrar envío (crear trabajo)
    public TrabajoTercerizado registrarEnvio(TrabajoTercerizado trabajo) {

        return trabajoRepository.save(trabajo);
    }

    // 🥈 Listar pendientes
    public List<TrabajoTercerizado> listarPendientes() {
        return trabajoRepository.findByEstado("ENVIADO");
    }

    // 🥉 Listar por empresa
    public List<TrabajoTercerizado> listarPorEmpresa(Integer empresaId) {
        return trabajoRepository.findByEmpresaId(empresaId);
    }

    // 🏅 Calcular total deuda por empresa
    public BigDecimal calcularTotalPorEmpresa(Integer empresaId) {

        List<TrabajoTercerizado> trabajos =
                trabajoRepository.findByEmpresaIdAndEstado(empresaId, "ENVIADO");

        BigDecimal total = BigDecimal.ZERO;

        for (TrabajoTercerizado t : trabajos) {
            total = total.add(t.getCosto());
        }

        return total;
    }

    // 🏅 Marcar como retirado (pagado)
    public TrabajoTercerizado marcarComoRetirado(Integer id) {

        TrabajoTercerizado trabajo = trabajoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Trabajo no encontrado"));

        trabajo.setEstado("RETIRADO");
        trabajo.setFechaRetiro(LocalDateTime.now());

        return trabajoRepository.save(trabajo);
    }

    // 🏅 Historial de pagos
    public List<TrabajoTercerizado> historialPagos() {
        return trabajoRepository.findByEstadoOrderByFechaRetiroDesc("RETIRADO");
    }
    
}
