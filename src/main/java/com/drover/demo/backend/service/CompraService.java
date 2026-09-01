package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Compra;
import com.drover.demo.backend.entity.DetalleCompra;
import com.drover.demo.backend.repository.CompraRepository;

import jakarta.transaction.Transactional;


@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    @Transactional
    public void guardar(Compra compra) {
        Compra compraLimpia = validarCompra(compra);
        
        compraRepository.save(compraLimpia);
    }
    
    @Transactional
    public void editar(Long id, Compra compra) {
        if (id == null) {
            throw new IllegalArgumentException("Debe marcar un ID válido de compra.");
        }
        
        Compra compraExistente = compraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ID de compra no encontrado: " + id));
            
        Compra compraLimpia = validarCompra(compra);
        
        compraExistente.setNumeroCompra(compraLimpia.getNumeroCompra());
        compraExistente.setFecha(compraLimpia.getFecha());
        compraExistente.setProveedor(compraLimpia.getProveedor());
        compraExistente.setTotal(compraLimpia.getTotal());
        compraExistente.setObservaciones(compraLimpia.getObservaciones());
        
        compraExistente.getDetalles().clear();
        for (DetalleCompra nuevoDetalle : compraLimpia.getDetalles()) {
            compraExistente.getDetalles().add(nuevoDetalle);
            nuevoDetalle.setCompra(compraExistente);      
        }

        
        compraRepository.save(compraExistente);
    }

    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    public List<Compra> listarPorFechCompras(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas provistas no son válidas.");
        }
        return compraRepository.findByFechaBetween(desde, hasta);
    }

    public Compra listarPorNroCompra(String nroCompra) {
        String nroCompraLimpio = validarString(nroCompra);
        return compraRepository.findByNumeroCompra(nroCompraLimpio)
            .orElseThrow(() -> new RuntimeException("Número de compra no encontrado: " + nroCompra));
    }

    // --- MÉTODOS PRIVADOS DE VALIDACIÓN ---

    private BigDecimal validarEntero(BigDecimal decimal) {
        if (decimal == null || decimal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Los números o montos no pueden ser nulos ni menores a 0.");
        }
        return decimal;
    }

    private String validarString(String string) {
        if (string == null || string.strip().isEmpty()) {
            throw new IllegalArgumentException("Este campo de texto es obligatorio.");
        }
        return string.strip().toLowerCase();
    }


    private Compra validarCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La consulta de compra está vacía.");
        }
        
        if (compra.getProveedor() == null || compra.getProveedor().getId() == null) {
            throw new IllegalArgumentException("El proveedor es obligatorio.");
        }
        
        compra.setNumeroCompra(validarString(compra.getNumeroCompra()));
        
        if (compra.getFecha() == null) {
            compra.setFecha(LocalDateTime.now());
        }

        if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Una compra no puede guardarse sin detalles.");
        }

        BigDecimal totalCalculado = BigDecimal.ZERO;

        for (DetalleCompra detalle : compra.getDetalles()) {
            if (detalle.getInsumo() == null || detalle.getInsumo().getId() == null) {
                throw new IllegalArgumentException("Cada renglón debe especificar un insumo válido.");
            }

            totalCalculado = totalCalculado.add(detalle.getSubtotal());

            detalle.setCompra(compra);
        }

        compra.setTotal(totalCalculado);
        return compra;
    }


}

