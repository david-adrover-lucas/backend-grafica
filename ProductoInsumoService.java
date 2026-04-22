package com.drover.demo.backend.service;

import com.drover.demo.backend.controller.ProductoController;
import com.drover.demo.backend.dto.request.ProductoInsumoRequestDTO;
import com.drover.demo.backend.entity.ProductoInsumo;
import com.drover.demo.backend.repository.ProductoInsumoRepository;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoInsumoService {

    private final ProductoController productoController;
    private final ProductoInsumoRepository  repository;

    public ProductoInsumoService(ProductoInsumoRepository  repository, ProductoController productoController) {
        this.repository = repository;
        this.productoController = productoController;
    }

    /**
     * 🥇 Agregar insumo a producto
     */
    public ProductoInsumo guardar(ProductoInsumo pi) {

        if (pi.getCantidad() == null || pi.getCantidad() <= 0) {
                       
        }

        return repository.save(pi);
    }

    /**
     * 🥈 Ver insumos de un producto
     */
    public List<ProductoInsumo> listarPorProducto(Integer productoId) {
        return repository.findByProductoId(productoId);
    }

    /**
     * 🥉 Ver productos que usan un insumo
     */
    public List<ProductoInsumo> listarPorInsumo(Integer insumoId) {
        return repository.findByInsumoId(insumoId);
    }

    /**
     * ❌ Eliminar relación
     */
    public void eliminar(Integer id) {
        repository.deleteById(id);
    }

    /**
     * 🔄 Actualizar cantidad
     */
    public ProductoInsumo actualizarCantidad(Integer id, Integer nuevaCantidad) {

        if (nuevaCantidad == null ||nuevaCantidad <= 0) {
            throw new ValidacionException("Cantidad inválida");
        }

        ProductoInsumo pi = repository.findById(id).orElseThrow(() -> new RecursoNoEncontradoException("Relación no encontrada"));

        pi.setCantidad(nuevaCantidad);

        return repository.save(pi);
    }
}
