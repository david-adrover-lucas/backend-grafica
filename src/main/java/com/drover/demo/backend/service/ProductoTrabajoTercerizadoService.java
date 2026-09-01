package com.drover.demo.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.ProductoTrabajoTercerizado;
import com.drover.demo.backend.repository.ProductoTrabajoTercerizadoRepository;
import com.drover.demo.backend.repository.TrabajosTercerizadoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class ProductoTrabajoTercerizadoService {

    private final ProductoTrabajoTercerizadoRepository productoTrabajoTercerizadoRepository;
    private final TrabajosTercerizadoRepository trabajosTercerizadoRepository;

    public ProductoTrabajoTercerizadoService( ProductoTrabajoTercerizadoRepository productoTrabajoTercerizadoRepository,
            TrabajosTercerizadoRepository trabajosTercerizadoRepository) {
        this.productoTrabajoTercerizadoRepository = productoTrabajoTercerizadoRepository;
        this.trabajosTercerizadoRepository = trabajosTercerizadoRepository;
    }
    
    @Transactional
    public void guardar(ProductoTrabajoTercerizado productoTrabajoTercerizado) {
        ProductoTrabajoTercerizado tareaLimpia = validarProductoTrabajoTercerizado(productoTrabajoTercerizado);
        productoTrabajoTercerizadoRepository.save(tareaLimpia);
    }
    
    @Transactional
    public void editar(Long id, ProductoTrabajoTercerizado productoTrabajoTercerizado) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        ProductoTrabajoTercerizado tareaExistente = productoTrabajoTercerizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Renglón de tarea externa no encontrado con el ID: " + id));

        ProductoTrabajoTercerizado datosLimpios = validarProductoTrabajoTercerizado(productoTrabajoTercerizado);

        // Traspasamos los valores al registro existente gestionado por Hibernate
        tareaExistente.setProducto(datosLimpios.getProducto());
        tareaExistente.setTrabajoTercerizado(datosLimpios.getTrabajoTercerizado());
        tareaExistente.setCantidadRequerida(datosLimpios.getCantidadRequerida());

        productoTrabajoTercerizadoRepository.save(tareaExistente);
    }
  
    public List<ProductoTrabajoTercerizado> listar() { 
        return productoTrabajoTercerizadoRepository.findAll();
    }

 
    public List<ProductoTrabajoTercerizado> listarPortrabajo(Long trabajoTercerizadoId) { 
        if (trabajoTercerizadoId == null) {
            throw new IllegalArgumentException("El ID del trabajo tercerizado es obligatorio para filtrar.");
        }
        return productoTrabajoTercerizadoRepository.findByTrabajoTercerizadoId(trabajoTercerizadoId);
    }

    @Transactional
    public void desactivarPorId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo para eliminar.");
        }
        productoTrabajoTercerizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Renglón de receta externa no encontrado para eliminar: " + id));
            
        productoTrabajoTercerizadoRepository.deleteById(id);
    }

    // --- MÉTODOS PRIVADOS ---
    
    private ProductoTrabajoTercerizado validarProductoTrabajoTercerizado(ProductoTrabajoTercerizado productoTrabajoTercerizado) {
        if (productoTrabajoTercerizado == null) {
            throw new IllegalArgumentException("La consulta de tarea externa no puede estar vacía.");
        }

        
        if (productoTrabajoTercerizado.getTrabajoTercerizado() == null || productoTrabajoTercerizado.getTrabajoTercerizado().getId() == null) {
            throw new IllegalArgumentException("El componente debe estar vinculado a un servicio tercerizado válido.");
        }

        if (!trabajosTercerizadoRepository.existsById(productoTrabajoTercerizado.getTrabajoTercerizado().getId())) {
            throw new IllegalArgumentException("El servicio tercerizado con ID " + productoTrabajoTercerizado.getTrabajoTercerizado().getId() + " no existe en el sistema.");
        }

        // Control numérico industrial: la mano de obra o procesos externos no pueden ser negativos o ceros
        if (productoTrabajoTercerizado.getCantidadRequerida() == null || productoTrabajoTercerizado.getCantidadRequerida().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad requerida del servicio externo es obligatoria y debe ser mayor a 0.");
        }

        return productoTrabajoTercerizado;
    }
}

