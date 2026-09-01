package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.ComisionesEscalaProducto;
import com.drover.demo.backend.repository.ComisionesEscalaProductoRepository;

import jakarta.transaction.Transactional;


@Service
public class ComisionesEscalaProductoService {

    private final ComisionesEscalaProductoRepository comisionesRepository;

    public ComisionesEscalaProductoService(ComisionesEscalaProductoRepository comisionesRepository) {
        this.comisionesRepository = comisionesRepository;
    }

    @Transactional
    public void guardar(ComisionesEscalaProducto escala) {
        ComisionesEscalaProducto escalaLimpia = validarEscalaComision(escala);
        comisionesRepository.save(escalaLimpia);
    }

    @Transactional
    public void editar(Long id, ComisionesEscalaProducto escalaNuevosDatos) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        ComisionesEscalaProducto escalaExisting = comisionesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Escala de comisión no encontrada con el ID: " + id));

        ComisionesEscalaProducto datosLimpios = validarEscalaComision(escalaNuevosDatos);

        // CORRECCIÓN: Asignación con las nuevas propiedades universales
        escalaExisting.setProducto(datosLimpios.getProducto());
        escalaExisting.setCantidadDesde(datosLimpios.getCantidadDesde());
        escalaExisting.setCantidadHasta(datosLimpios.getCantidadHasta());
        escalaExisting.setMontoComision(datosLimpios.getMontoComision());
        escalaExisting.setActivo(datosLimpios.getActivo());

        comisionesRepository.save(escalaExisting);
    }

    @Transactional
    public void desactivarEscala(Long id) {
        ComisionesEscalaProducto escala = comisionesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Escala de comisión no encontrada con el ID: " + id));
        escala.setActivo(false);
        comisionesRepository.save(escala);
    }

    public List<ComisionesEscalaProducto> listarPorProducto(Long productoId) {
        if (productoId == null) {
            throw new IllegalArgumentException("El ID del producto es requerido.");
        }
        return comisionesRepository.findByProductoId(productoId);
    }

    // --- MÉTODOS PRIVADOS DE VALIDACIÓN ---

    private ComisionesEscalaProducto validarEscalaComision(ComisionesEscalaProducto escala) {
        if (escala == null) {
            throw new IllegalArgumentException("La escala de comisión no puede estar vacía.");
        }

        if (escala.getProducto() == null || escala.getProducto().getId() == null) {
            throw new IllegalArgumentException("La escala de comisión debe estar vinculada obligatoriamente a un producto válido.");
        }

        // CORRECCIÓN: Validación de presencia utilizando nombres neutros
        if (escala.getCantidadDesde() == null || escala.getMontoComision() == null) {
            throw new IllegalArgumentException("Los campos cantidadDesde y montoComision son estrictamente obligatorios.");
        }

        // Controlamos que no ingresen números comerciales negativos
        if (escala.getCantidadDesde().compareTo(BigDecimal.ZERO) < 0 ||
            escala.getMontoComision().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Los rangos iniciales y los montos de comisión no pueden ser negativos.");
        }

        // CORRECCIÓN: Blindaje matemático adaptado a cantidadHasta
        if (escala.getCantidadHasta() != null) {
            if (escala.getCantidadHasta().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El rango límite cantidadHasta no puede ser negativo.");
            }
            if (escala.getCantidadHasta().compareTo(escala.getCantidadDesde()) <= 0) {
                throw new IllegalArgumentException("El límite cantidadHasta debe ser estrictamente mayor que el punto de inicio cantidadDesde.");
            }
        }

        if (escala.getActivo() == null) {
            escala.setActivo(true);
        }

        return escala;
    }
}

