package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.ProductoTrabajoTercerizado;
import com.drover.demo.backend.repository.ProductoTrabajoTercerizadoRepository;

@Service
public class Producto_trabajo_tercerizadoService {

    private final ProductoTrabajoTercerizadoRepository productoTrabajoTercerizadoRepository;

    public Producto_trabajo_tercerizadoService(
            ProductoTrabajoTercerizadoRepository productoTrabajoTercerizadoRepository) {
        this.productoTrabajoTercerizadoRepository = productoTrabajoTercerizadoRepository;
    }

    public List<ProductoTrabajoTercerizado> listar() { }
    public Optional<ProductoTrabajoTercerizado> buscarPorId(Long id){ }
    public ProductoTrabajoTercerizado guardar(ProductoTrabajoTercerizado productoTrabajoTercerizado) {}
    public void eliminarPorId(Long id) {}

    
}
