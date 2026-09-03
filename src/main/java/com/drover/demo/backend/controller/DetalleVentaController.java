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

import com.drover.demo.backend.entity.DetalleVenta;
import com.drover.demo.backend.service.DetalleVentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/detalles-venta")
public class DetalleVentaController {

    private final DetalleVentaService detalleVentaService;

    public DetalleVentaController(DetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }

    @PostMapping
    public ResponseEntity<DetalleVenta> guardar(@Valid @RequestBody DetalleVenta detalleVenta) {
        return ResponseEntity.status(201).body(detalleVentaService.guardar(detalleVenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> editar(@PathVariable Long id, @Valid @RequestBody DetalleVenta detalleVenta) {
        return ResponseEntity.ok(detalleVentaService.editar(id, detalleVenta));
    }

    @GetMapping
    public List<DetalleVenta> listar() {
        return detalleVentaService.listar();
    }
}
