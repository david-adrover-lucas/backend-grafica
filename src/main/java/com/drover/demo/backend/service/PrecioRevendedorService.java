package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.PrecioRevendedor;
import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.repository.PrecioRevendedorRepository;
import com.drover.demo.backend.repository.ProductoRepository;

import jakarta.transaction.Transactional;



@Service
public class PrecioRevendedorService {

    private final PrecioRevendedorRepository precioRevendedorRepository;
    // CORRECCIÓN 1: Inyectamos ProductoRepository para poder verificar los costos reales de la BD
    private final ProductoRepository productoRepository;

    public PrecioRevendedorService(PrecioRevendedorRepository precioRevendedorRepository, ProductoRepository productoRepository) {
        this.precioRevendedorRepository = precioRevendedorRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional    
    public void guardar(PrecioRevendedor precioRevendedor) {
        PrecioRevendedor limpio = validaPrecioRevendedor(precioRevendedor);
        
        // CORRECCIÓN 3: Validación para impedir tarifas duplicadas para el mismo producto y cliente
        if (precioRevendedorRepository.existsByRevendedorIdAndProductoId(limpio.getRevendedor().getId(), limpio.getProducto().getId())) {
            throw new IllegalArgumentException("Ya existe un precio especial registrado para este revendedor y este producto.");
        }
        
        precioRevendedorRepository.save(limpio);
    }

    @Transactional    
    public void editar(Long id, PrecioRevendedor precioRevendedor) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo para editar.");
        }
        
        PrecioRevendedor existePrecioRevendedor = precioRevendedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No existe el precio especial con el ID: " + id));
            
        PrecioRevendedor precioRevendedorLimpio = validaPrecioRevendedor(precioRevendedor);
        
        existePrecioRevendedor.setPrecio(precioRevendedorLimpio.getPrecio());
        existePrecioRevendedor.setProducto(precioRevendedorLimpio.getProducto());
        existePrecioRevendedor.setRevendedor(precioRevendedorLimpio.getRevendedor());
        existePrecioRevendedor.setActivo(precioRevendedorLimpio.getActivo());

        precioRevendedorRepository.save(existePrecioRevendedor);
    }   

    public List<PrecioRevendedor> listar() {
        return precioRevendedorRepository.findAll();
    }

    public List<PrecioRevendedor> listarPorRevendedor(Long revendedorId) {
        if (revendedorId == null) {
            throw new IllegalArgumentException("El ID del revendedor no puede estar vacío.");
        }
        return precioRevendedorRepository.findByRevendedorIdOrderByProductoNombreAsc(revendedorId);
    }

    @Transactional
    public void desactivarporID(Long id) {
        PrecioRevendedor precioRevendedor = precioRevendedorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Precio revendedor con ID no encontrado: " + id));
        
        precioRevendedor.setActivo(false);
        precioRevendedorRepository.save(precioRevendedor);
    }

    // --- MÉTODOS PRIVADOS ---

    private PrecioRevendedor validaPrecioRevendedor(PrecioRevendedor precioRevendedor) {
        if (precioRevendedor == null) {
            throw new IllegalArgumentException("La consulta de precio no puede estar vacía.");
        }
        
        if (precioRevendedor.getProducto() == null || precioRevendedor.getProducto().getId() == null ||
            precioRevendedor.getRevendedor() == null || precioRevendedor.getRevendedor().getId() == null) {
            throw new IllegalArgumentException("Los campos Producto ID y Revendedor ID son obligatorios.");
        }
        
        if (precioRevendedor.getPrecio() == null || precioRevendedor.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El precio especial no puede ser nulo ni menor a 0.");
        } 

        Producto productoBd = productoRepository.findById(precioRevendedor.getProducto().getId())
            .orElseThrow(() -> new RuntimeException("El producto asociado a la tarifa no existe en el catálogo."));

        if (productoBd.getCostoActual().compareTo(precioRevendedor.getPrecio()) > 0) {
            throw new IllegalArgumentException("El precio de oferta (" + precioRevendedor.getPrecio() 
                + ") no puede ser menor al costo de fabricación de sus insumos (" + productoBd.getCostoActual() + "). ¡Pérdida comercial!");
        }

        if (precioRevendedor.getActivo() == null) {
            precioRevendedor.setActivo(true);
        }
        
        return precioRevendedor;
    }
}
