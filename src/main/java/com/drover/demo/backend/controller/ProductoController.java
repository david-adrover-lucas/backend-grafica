package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody Producto producto) {
        productoService.guardar(producto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        productoService.editar(id, producto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Producto> listar() {
        return productoService.listar();
    }

    @GetMapping("/nombre/{nombre}")
    public List<Producto> listarNombre(@PathVariable String nombre) {
        return productoService.listarNombre(nombre);
    }

    @GetMapping("/unidad-venta/{unidadVenta}")
    public List<Producto> listarUnidadVenta(@PathVariable String unidadVenta) {
        return productoService.listarUnidad_venta(unidadVenta);
    }
}
