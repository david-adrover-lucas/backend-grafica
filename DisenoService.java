package com.drover.demo.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;
import com.drover.demo.backend.entity.Diseno;
import com.drover.demo.backend.repository.DisenoRepository;

@Service
public class DisenoService {

    private final DisenoRepository disenoRepository;

    public DisenoService(DisenoRepository disenoRepository) {
        this.disenoRepository = disenoRepository;
    }

    public Diseno crearDiseno(Diseno diseno) {
        if (diseno.getNombre() == null || diseno.getNombre().trim().isEmpty()) {
            throw new ValidacionException("El nombre del diseño es obligatorio");
        }

        diseno.setContadorUso(0);
        return disenoRepository.save(diseno);
    }

    public List<Diseno> listar() {
        return disenoRepository.findAll();
    }

    public List<Diseno> buscarDisenos(String categoria, String etiqueta, String nombre, Boolean reutilizable) {

        boolean tieneCategoria = categoria != null && !categoria.trim().isEmpty();
        boolean tieneEtiqueta = etiqueta != null && !etiqueta.trim().isEmpty();
        boolean tieneNombre = nombre != null && !nombre.trim().isEmpty();

        if (tieneCategoria && tieneEtiqueta && tieneNombre) {
            return disenoRepository
                    .findByCategoriaAndEtiquetasContainingIgnoreCaseAndNombreContainingIgnoreCase(
                            categoria.trim(), etiqueta.trim(), nombre.trim());
        }

        if (tieneCategoria && tieneEtiqueta) {
            return disenoRepository
                    .findByCategoriaAndEtiquetasContainingIgnoreCase(
                            categoria.trim(), etiqueta.trim());
        }

        if (tieneCategoria && tieneNombre) {
            return disenoRepository
                    .findByCategoriaAndNombreContainingIgnoreCase(
                            categoria.trim(), nombre.trim());
        }

        if (tieneEtiqueta && tieneNombre) {
            return disenoRepository
                    .findByEtiquetasContainingIgnoreCaseAndNombreContainingIgnoreCase(
                            etiqueta.trim(), nombre.trim());
        }

        if (tieneCategoria) {
            return disenoRepository.findByCategoria(categoria.trim());
        }

        if (tieneEtiqueta) {
            return disenoRepository.findByEtiquetasContainingIgnoreCase(etiqueta.trim());
        }

        if (tieneNombre) {
            return disenoRepository.findByNombreContainingIgnoreCase(nombre.trim());
        }

        return disenoRepository.findAll();
    }

    public Diseno incrementarUsoDiseno(Integer disenoId) {

        Diseno diseno = disenoRepository.findById(disenoId)
                .orElseThrow(() -> new RecursoNoEncontradoException(" Diseño no encontrado"));

        diseno.setContadorUso(diseno.getContadorUso() + 1);
        return disenoRepository.save(diseno);
    }
}




