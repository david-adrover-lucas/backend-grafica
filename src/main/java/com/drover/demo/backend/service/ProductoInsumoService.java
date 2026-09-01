package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.ProductoInsumo;
import com.drover.demo.backend.repository.ProductoInsumoRepository;

import jakarta.transaction.Transactional;



@Service
public class ProductoInsumoService {

    private final ProductoInsumoRepository productoInsumoRepository;

    public ProductoInsumoService(ProductoInsumoRepository productoInsumoRepository) {
        this.productoInsumoRepository = productoInsumoRepository;
    }

 
    @Transactional
    public ProductoInsumo guardar(ProductoInsumo productoInsumo) {
        ProductoInsumo productoInsumoLimpio = validarProductoInsumo(productoInsumo);
        
        return productoInsumoRepository.save(productoInsumoLimpio);
    }

    @Transactional
    public void editar(Long id, ProductoInsumo productoInsumo) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        ProductoInsumo existeProductoInsumo = productoInsumoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ID de componente no encontrado: " + id));
            
        ProductoInsumo limpiProductoInsumo = validarProductoInsumo(productoInsumo);
        
        existeProductoInsumo.setProducto(limpiProductoInsumo.getProducto());
        existeProductoInsumo.setInsumo(limpiProductoInsumo.getInsumo());
        existeProductoInsumo.setCantidad(limpiProductoInsumo.getCantidad());
        
        productoInsumoRepository.save(existeProductoInsumo);
    }

    public List<ProductoInsumo> listar() {
        return productoInsumoRepository.findAll();
    }
    

    public List<ProductoInsumo> listarPorProducto(Long productoId) {
        if (productoId == null) {
            throw new IllegalArgumentException("El ID del producto es obligatorio.");
        }
        return productoInsumoRepository.findByProductoId(productoId);
    }

    @Transactional
    public void eliminarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo para eliminar.");
        }
        productoInsumoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ID de componente no encontrado para eliminar: " + id));
            
        productoInsumoRepository.deleteById(id);
    }

    // --- MÉTODOS PRIVADOS ---
    
    private ProductoInsumo validarProductoInsumo(ProductoInsumo productoInsumo) {
        if (productoInsumo == null) {
            throw new IllegalArgumentException("La consulta de componente de receta no puede estar vacía.");
        }
        

        if (productoInsumo.getInsumo() == null || productoInsumo.getInsumo().getId() == null) {
            throw new IllegalArgumentException("El componente debe estar vinculado a un insumo válido.");
        }
        
        if (productoInsumo.getCantidad() == null || productoInsumo.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad consumida del insumo es obligatoria y debe ser mayor a 0.");
        }
        
        return productoInsumo;
    }
}
