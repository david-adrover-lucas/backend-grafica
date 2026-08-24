package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Producto_insumo;
import com.drover.demo.backend.repository.Producto_insumoRepository;

@Service
public class Producto_insumoService {

    private final Producto_insumoRepository productoInsumoRepository;

    public Producto_insumoService(Producto_insumoRepository productoInsumoRepository) {
        this.productoInsumoRepository = productoInsumoRepository;
    }

    public List<Producto_insumo> listar() {
        return productoInsumoRepository.findAll();
    }

    public Optional<Producto_insumo> buscarPorId(Long id) {
        return productoInsumoRepository.findById(id);
    }

    public Producto_insumo guardar(Producto_insumo productoInsumo) {
        return productoInsumoRepository.save(productoInsumo);
    }

    public void eliminarPorId(Long id) {
        productoInsumoRepository.deleteById(id);
    }
}
