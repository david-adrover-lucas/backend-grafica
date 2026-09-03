package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.ProductoTrabajoTercerizado;
import com.drover.demo.backend.service.ProductoTrabajoTercerizadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos-trabajos-tercerizados")
public class ProductoTrabajoTercerizadoController {

    private final ProductoTrabajoTercerizadoService productoTrabajoTercerizadoService;

    public ProductoTrabajoTercerizadoController(ProductoTrabajoTercerizadoService productoTrabajoTercerizadoService) {
        this.productoTrabajoTercerizadoService = productoTrabajoTercerizadoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody ProductoTrabajoTercerizado productoTrabajoTercerizado) {
        productoTrabajoTercerizadoService.guardar(productoTrabajoTercerizado);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody ProductoTrabajoTercerizado productoTrabajoTercerizado) {
        productoTrabajoTercerizadoService.editar(id, productoTrabajoTercerizado);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<ProductoTrabajoTercerizado> listar() {
        return productoTrabajoTercerizadoService.listar();
    }

    @GetMapping("/trabajo/{trabajoTercerizadoId}")
    public List<ProductoTrabajoTercerizado> listarPortrabajo(@PathVariable Long trabajoTercerizadoId) {
        return productoTrabajoTercerizadoService.listarPortrabajo(trabajoTercerizadoId);
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarPorId(@PathVariable Long id) {
        productoTrabajoTercerizadoService.desactivarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
