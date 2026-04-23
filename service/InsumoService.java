package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.repository.InsumoRepository;
import org.springframework.stereotype.Service;

import com.drover.demo.backend.exception.NegocioException;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public InsumoService(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    /**
     * 🥇 Crear insumo
     */
    public Insumo guardar(Insumo insumo) {

        if (insumo.getActivo() == null) {
            insumo.setActivo(true);
        }

        return insumoRepository.save(insumo);
    }

    /**
     * 🥈 Listar todos
     */
    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    /**
     * 🥉 Listar activos
     */
    public List<Insumo> listarActivos() {
        return insumoRepository.findByActivo(true);
    }

    /**
     * 🏅 Buscar por nombre
     */
    public List<Insumo> buscarPorNombre(String nombre) {
        return insumoRepository.findByNombre(nombre);
    }

    /**
     * 🏅 Buscar por tipo
     */
    public List<Insumo> buscarPorTipo(Insumo.TipoInsumos tipo) {
        return insumoRepository.findByTipo(tipo);
    }

    /**
     * 🏅 Buscar por empresa
     */
    public List<Insumo> buscarPorEmpresa(Integer empresaId) {
        return insumoRepository.findByEmpresaId(empresaId);
    }

    /**
     * 🧠 Búsqueda flexible (nombre o tipo)
     */
    public List<Insumo> buscar(String nombre, Insumo.TipoInsumos tipo) {

        if (nombre != null && tipo != null) {
            return insumoRepository.findByNombreOrTipo(nombre, tipo);
        }

        if (nombre != null) {
            return insumoRepository.findByNombre(nombre);
        }

        if (tipo != null) {
            return insumoRepository.findByTipo(tipo);
        }

        return insumoRepository.findAll();
    }

    /**
     * ❌ Desactivar insumo
     */
    public void desactivar(Integer id) {

        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));

        insumo.setActivo(false);
        insumoRepository.save(insumo);
    }

    /**
     * 🔄 Actualizar costo
     */
    public Insumo actualizarCosto(Integer id, BigDecimal nuevoCosto) {

        if (nuevoCosto == null || nuevoCosto.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValidacionException("Costo inválido");
        }

        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));

        insumo.setCostoUnitario(nuevoCosto);

        return insumoRepository.save(insumo);
    }
}
