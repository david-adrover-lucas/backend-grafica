package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.DetalleCompra;
import com.drover.demo.backend.repository.DetalleCompraRepository;

import jakarta.transaction.Transactional;

@Service
public class DetalleCompraService {

    private final DetalleCompraRepository detalleCompraRepository;

    public DetalleCompraService(DetalleCompraRepository detalleCompraRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
    }

    @Transactional
    public DetalleCompra guardar(DetalleCompra detalleCompra) {
        DetalleCompra detalleCompraValidado = validarDetalleCompra(detalleCompra);
        return detalleCompraRepository.save(detalleCompraValidado);
    }


    @Transactional
    public void editar(Long id, DetalleCompra detalleCompra) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar.");
        }

        DetalleCompra detalleExistente = detalleCompraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Renglón de detalle no encontrado con el ID: " + id));

        DetalleCompra datosLimpios = validarDetalleCompra(detalleCompra);


        detalleExistente.setInsumo(datosLimpios.getInsumo());
        detalleExistente.setCantidad(datosLimpios.getCantidad());
        detalleExistente.setPrecioUnitario(datosLimpios.getPrecioUnitario());
        
        detalleExistente.setSubtotal(datosLimpios.getSubtotal());

        // 4. Guardamos los cambios consolidados
        detalleCompraRepository.save(detalleExistente);
    }
    
    public List<DetalleCompra> listar() {
        return detalleCompraRepository.findAll();
    }

    // --- MÉTODOS PRIVADOS ---    

    private BigDecimal validarBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal == null || bigDecimal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Los números comerciales no pueden ser nulos ni menores a 0.");
        }
        return bigDecimal;
    }
 
    private DetalleCompra validarDetalleCompra(DetalleCompra detalleCompra) {
        if (detalleCompra == null) {
            throw new IllegalArgumentException("La consulta de detalle de compra no puede estar vacía.");
        }
        
        if (detalleCompra.getInsumo() == null || detalleCompra.getInsumo().getId() == null) {
            throw new IllegalArgumentException("Cada renglón del detalle debe estar vinculado a un insumo válido.");
        }
        
        // Validamos de forma segura las variables numéricas de entrada
        detalleCompra.setCantidad(validarBigDecimal(detalleCompra.getCantidad()));
        detalleCompra.setPrecioUnitario(validarBigDecimal(detalleCompra.getPrecioUnitario()));   
        
       
        BigDecimal subtotalCalculado = calcularSubtotal(detalleCompra.getCantidad(), detalleCompra.getPrecioUnitario());
        detalleCompra.setSubtotal(subtotalCalculado);
       
        return detalleCompra;
    }
  
    private BigDecimal calcularSubtotal(BigDecimal cantidad, BigDecimal precioUnitario) {
        return cantidad.multiply(precioUnitario);
    }
}


