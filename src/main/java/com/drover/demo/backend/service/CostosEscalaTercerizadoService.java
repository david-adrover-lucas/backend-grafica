package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.CostosEscalaTercerizado;
import com.drover.demo.backend.repository.CostosEscalaTercerizadoRepository;
import com.drover.demo.backend.repository.TrabajosTercerizadoRepository;

import jakarta.transaction.Transactional;


@Service
public class CostosEscalaTercerizadoService {

    private final CostosEscalaTercerizadoRepository costosEscalaTercerizadoRepository;
    private final TrabajosTercerizadoRepository trabajosTercerizadoRepository;

    public CostosEscalaTercerizadoService(CostosEscalaTercerizadoRepository costosEscalaTercerizadoRepository, 
                                           TrabajosTercerizadoRepository trabajosTercerizadoRepository) {
        this.costosEscalaTercerizadoRepository = costosEscalaTercerizadoRepository;
        this.trabajosTercerizadoRepository = trabajosTercerizadoRepository;
    }

    @Transactional
    public void guardar(CostosEscalaTercerizado costosEscalaTercerizado) {
        CostosEscalaTercerizado escalaLimpia = validarCostosEscalaTercerizado(costosEscalaTercerizado);
        costosEscalaTercerizadoRepository.save(escalaLimpia);
    }
   
    @Transactional
    public void editar(Long id, CostosEscalaTercerizado costosEscalaTercerizado) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        CostosEscalaTercerizado escalaExistente = costosEscalaTercerizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Escala de costos no encontrada con el ID: " + id));

        CostosEscalaTercerizado datosNuevosLimpios = validarCostosEscalaTercerizado(costosEscalaTercerizado);

        // Traspasamos los valores validados al objeto persistente de Hibernate
        escalaExistente.setTrabajoTercerizado(datosNuevosLimpios.getTrabajoTercerizado());
        escalaExistente.setCantidadDesde(datosNuevosLimpios.getCantidadDesde());
        escalaExistente.setCantidadHasta(datosNuevosLimpios.getCantidadHasta());
        escalaExistente.setPrecioUnitario(datosNuevosLimpios.getPrecioUnitario());
        escalaExistente.setActivo(datosNuevosLimpios.getActivo());

        costosEscalaTercerizadoRepository.save(escalaExistente);
    }
  
    @Transactional
    public void desactivarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo.");
        }
        CostosEscalaTercerizado escala = costosEscalaTercerizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Escala de costos no encontrada con el ID: " + id));
        
        escala.setActivo(false);
        costosEscalaTercerizadoRepository.save(escala); // Guardamos la baja lógica de forma real en SQL
    }

    public List<CostosEscalaTercerizado> listar() {
        return costosEscalaTercerizadoRepository.findAll();
    }

 
    public List<CostosEscalaTercerizado> listarPorTrabajoTercerizado(Long trabajoTercerizadoId) {
        if (trabajoTercerizadoId == null) {
            throw new IllegalArgumentException("El ID del trabajo tercerizado es obligatorio.");
        }
        return costosEscalaTercerizadoRepository.findByTrabajoTercerizadoId(trabajoTercerizadoId);
    }

    // --- MÉTODOS PRIVADOS DE VALIDACIÓN ---

    private CostosEscalaTercerizado validarCostosEscalaTercerizado(CostosEscalaTercerizado costosEscalaTercerizado) {
        if (costosEscalaTercerizado == null) {
            throw new IllegalArgumentException("La consulta de escala de costos está vacía.");
        }

        // 1. Validamos la presencia de la relación con el Trabajo Tercerizado padre
        if (costosEscalaTercerizado.getTrabajoTercerizado() == null || costosEscalaTercerizado.getTrabajoTercerizado().getId() == null) {
            throw new IllegalArgumentException("La escala de costos debe estar vinculada obligatoriamente a un trabajo tercerizado válido.");
        }

        // 2. Blindaje de integridad: Verificamos si el trabajo tercerizado base realmente existe en nuestra BD
        if (!trabajosTercerizadoRepository.existsById(costosEscalaTercerizado.getTrabajoTercerizado().getId())) {
            throw new IllegalArgumentException("El trabajo tercerizado base con ID " + costosEscalaTercerizado.getTrabajoTercerizado().getId() + " no existe en el catálogo.");
        }

        // 3. Validamos obligatoriedad de los datos comerciales básicos
        if (costosEscalaTercerizado.getCantidadDesde() == null || costosEscalaTercerizado.getPrecioUnitario() == null) {
            throw new IllegalArgumentException("Los campos cantidadDesde y precioUnitario son obligatorios.");
        }

        // 4. Control numérico estricto: prohibido números negativos en cantidades o precios
        if (costosEscalaTercerizado.getCantidadDesde().compareTo(BigDecimal.ZERO) < 0 ||
            costosEscalaTercerizado.getPrecioUnitario().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("La cantidad inicial y el precio unitario del proveedor no pueden ser negativos.");
        }

        // 5. Blindaje matemático contra nulos en el límite superior (cantidadHasta)
        if (costosEscalaTercerizado.getCantidadHasta() != null) {
            if (costosEscalaTercerizado.getCantidadHasta().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El rango límite cantidadHasta no puede ser negativo.");
            }
            if (costosEscalaTercerizado.getCantidadHasta().compareTo(costosEscalaTercerizado.getCantidadDesde()) <= 0) {
                throw new IllegalArgumentException("El límite cantidadHasta debe ser estrictamente mayor que el punto de inicio cantidadDesde.");
            }
        }

        if (costosEscalaTercerizado.getActivo() == null) {
            costosEscalaTercerizado.setActivo(true);
        }

        return costosEscalaTercerizado;
    }
}

