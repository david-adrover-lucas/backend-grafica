package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Producto_trabajo_tercerizado;
import com.drover.demo.backend.repository.Producto_trabajo_tercerizadoRepository;

@Service
public class Producto_trabajo_tercerizadoService {

    private final Producto_trabajo_tercerizadoRepository productoTrabajoTercerizadoRepository;

    public Producto_trabajo_tercerizadoService(
            Producto_trabajo_tercerizadoRepository productoTrabajoTercerizadoRepository) {
        this.productoTrabajoTercerizadoRepository = productoTrabajoTercerizadoRepository;
    }

    public List<Producto_trabajo_tercerizado> listar() {
        return productoTrabajoTercerizadoRepository.findAll();
    }

    public Optional<Producto_trabajo_tercerizado> buscarPorId(Long id) {
        return productoTrabajoTercerizadoRepository.findById(id);
    }

    public Producto_trabajo_tercerizado guardar(Producto_trabajo_tercerizado productoTrabajoTercerizado) {
        return productoTrabajoTercerizadoRepository.save(productoTrabajoTercerizado);
    }

    public void eliminarPorId(Long id) {
        productoTrabajoTercerizadoRepository.deleteById(id);
    }
}
