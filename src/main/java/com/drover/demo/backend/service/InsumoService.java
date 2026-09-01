package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.repository.InsumoRepository;

import jakarta.transaction.Transactional;


@Service
public class InsumoService {

    private final InsumoRepository insumoRepository;
    
    private final List<String> unidadPermitidas = List.of("m2", "lineal", "unidad");

    public InsumoService(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    @Transactional
    public void guardar(Insumo insumo) {
        Insumo insumoLimpio = validarInsumo(insumo);
        insumoRepository.save(insumoLimpio);
    }
    
    @Transactional
    public void editar(Long id, Insumo insumo) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID.");
        }
        
        Insumo insumoEncontrado = insumoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Insumo con ID no encontrado: " + id));
            
        Insumo insumoLimpio = validarInsumo(insumo);

        insumoEncontrado.setNombre(insumoLimpio.getNombre());
        insumoEncontrado.setProveedor(insumoLimpio.getProveedor()); 
        insumoEncontrado.setStockActual(insumoLimpio.getStockActual());
        insumoEncontrado.setStockMinimo(insumoLimpio.getStockMinimo());
        insumoEncontrado.setCostoUnitario(insumoLimpio.getCostoUnitario());    
        insumoEncontrado.setUnidad(insumoLimpio.getUnidad());
        
        insumoRepository.save(insumoEncontrado);
    }

    public List<Insumo> listar() {
        return insumoRepository.findAll();
    }

    public List<Insumo> listarPorUnidad(String unidad) {
        if (unidad == null || unidad.strip().isEmpty()) {
            throw new IllegalArgumentException("El parámetro unidad no puede estar nulo o vacío.");
        }
        
        String unidadLimpia = unidad.strip().toLowerCase();
        
        if (!unidadPermitidas.contains(unidadLimpia)) {
            throw new IllegalArgumentException("Unidad inexistente. Las unidades permitidas son: " + unidadPermitidas);
        }
        
        return insumoRepository.findByUnidad(unidadLimpia);
    }


    private Insumo validarInsumo(Insumo insumo) {
        if (insumo == null) {
            throw new IllegalArgumentException("El elemento insumo no puede ser nulo.");
        }
      
        if (insumo.getProveedor() == null
            || insumo.getNombre() == null || insumo.getNombre().strip().isEmpty()
            || insumo.getUnidad() == null || insumo.getUnidad().strip().isEmpty()) {
            throw new IllegalArgumentException("Los campos nombre, proveedor y unidad son obligatorios.");
        }
        
        if (insumo.getStockActual() == null || insumo.getStockActual().compareTo(BigDecimal.ZERO) < 0
            || insumo.getStockMinimo() == null || insumo.getStockMinimo().compareTo(BigDecimal.ZERO) < 0
            || insumo.getCostoUnitario() == null || insumo.getCostoUnitario().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Los campos StockActual, StockMinimo y costoUnitario son obligatorios y no pueden ser negativos.");
        }
        
        if (insumo.getActivo() == null) {
            insumo.setActivo(true);
        }
        
        insumo.setNombre(insumo.getNombre().strip().toLowerCase());
        
        insumo.setUnidad(insumo.getUnidad().strip().toLowerCase());

        if (!unidadPermitidas.contains(insumo.getUnidad())) {
            throw new IllegalArgumentException("Unidad inexistente. Las unidades aceptadas son: " + unidadPermitidas);
        }
        
        return insumo;
    }
    
}

