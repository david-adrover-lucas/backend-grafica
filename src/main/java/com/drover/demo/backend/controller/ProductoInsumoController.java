package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.ProductoInsumo;
import com.drover.demo.backend.service.ProductoInsumoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos-insumos")
public class ProductoInsumoController {

    private final ProductoInsumoService productoInsumoService;

    public ProductoInsumoController(ProductoInsumoService productoInsumoService) {
        this.productoInsumoService = productoInsumoService;
    }

    @PostMapping
    public ResponseEntity<ProductoInsumo> guardar(@Valid @RequestBody ProductoInsumo productoInsumo) {
        return ResponseEntity.status(201).body(productoInsumoService.guardar(productoInsumo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody ProductoInsumo productoInsumo) {
        productoInsumoService.editar(id, productoInsumo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<ProductoInsumo> listar() {
        return productoInsumoService.listar();
    }

    @GetMapping("/producto/{productoId}")
    public List<ProductoInsumo> listarPorProducto(@PathVariable Long productoId) {
        return productoInsumoService.listarPorProducto(productoId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPorId(@PathVariable Long id) {
        productoInsumoService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
