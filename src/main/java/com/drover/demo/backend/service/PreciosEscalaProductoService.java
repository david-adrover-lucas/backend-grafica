package com.drover.demo.backend.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.PreciosEscalaProducto;
import com.drover.demo.backend.repository.PreciosEscalaProductoRepository;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PreciosEscalaProductoService {
    
    private final PreciosEscalaProductoRepository preciosEscalaProductoRepository;
    
    // CORRECCIÓN 1: Agregamos el constructor obligatorio para la inyección de dependencias
    public PreciosEscalaProductoService(PreciosEscalaProductoRepository preciosEscalaProductoRepository) {
        this.preciosEscalaProductoRepository = preciosEscalaProductoRepository;
    }
    
    @Transactional
    public void guardar(PreciosEscalaProducto preciosEscalaProducto) {
        PreciosEscalaProducto preciosEscalaProductoLimpio = valiEscalaProducto(preciosEscalaProducto);
        preciosEscalaProductoRepository.save(preciosEscalaProductoLimpio);
    }
    
    @Transactional
    public void editar(Long id, PreciosEscalaProducto preciosEscalaProducto) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        PreciosEscalaProducto escalaProductoExistente = preciosEscalaProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ID de escala no encontrado: " + id));
            
        PreciosEscalaProducto datosNuevosLimpios = valiEscalaProducto(preciosEscalaProducto);
        
        escalaProductoExistente.setProducto(datosNuevosLimpios.getProducto());
        escalaProductoExistente.setCantidadDesde(datosNuevosLimpios.getCantidadDesde());
        escalaProductoExistente.setCantidadHasta(datosNuevosLimpios.getCantidadHasta());
        escalaProductoExistente.setPorcentajeGananciaEscala(datosNuevosLimpios.getPorcentajeGananciaEscala());
       
        preciosEscalaProductoRepository.save(escalaProductoExistente); 
    }
    
    @Transactional
    public void eliminar(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Debes proveer un ID para eliminar.");
        }
        preciosEscalaProductoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ID de escala no encontrado para eliminar: " + id));
            
        preciosEscalaProductoRepository.deleteById(id);
    }


    public List<PreciosEscalaProducto> listarPorProductoId(Long productoId) {
        if (productoId == null) {
            throw new IllegalArgumentException("El ID del producto es obligatorio para listar sus escalas.");
        }
        return preciosEscalaProductoRepository.findByProductoId(productoId);
    }


    private PreciosEscalaProducto valiEscalaProducto(PreciosEscalaProducto preciosEscalaProducto) {
        if (preciosEscalaProducto == null) {
            throw new IllegalArgumentException("La consulta de escala no puede estar vacía.");
        }
        
        if (preciosEscalaProducto.getProducto() == null || preciosEscalaProducto.getProducto().getId() == null) {
            throw new IllegalArgumentException("La escala debe estar vinculada obligatoriamente a un producto válido.");
        }

        if (preciosEscalaProducto.getCantidadDesde() == null || preciosEscalaProducto.getPorcentajeGananciaEscala() == null) {
            throw new IllegalArgumentException("Los campos cantidadDesde y porcentajeGanancia son obligatorios.");
        }

        if (preciosEscalaProducto.getCantidadDesde().compareTo(BigDecimal.ZERO) < 0 ||
            preciosEscalaProducto.getPorcentajeGananciaEscala().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Los rangos iniciales y porcentajes de ganancia no pueden ser negativos.");
        }

        if (preciosEscalaProducto.getCantidadHasta() != null) {
            if (preciosEscalaProducto.getCantidadHasta().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El rango límite cantidadHasta no puede ser negativo.");
            }
            if (preciosEscalaProducto.getCantidadHasta().compareTo(preciosEscalaProducto.getCantidadDesde()) <= 0) {
                throw new IllegalArgumentException("El límite cantidadHasta debe ser estrictamente mayor que cantidadDesde.");
            }
        }

        return preciosEscalaProducto;
    }
}




