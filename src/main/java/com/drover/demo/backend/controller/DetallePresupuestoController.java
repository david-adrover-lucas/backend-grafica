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

import com.drover.demo.backend.entity.DetallePresupuestos;
import com.drover.demo.backend.service.DetallePresupuestoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/detalles-presupuesto")
public class DetallePresupuestoController {

    private final DetallePresupuestoService detallePresupuestoService;

    public DetallePresupuestoController(DetallePresupuestoService detallePresupuestoService) {
        this.detallePresupuestoService = detallePresupuestoService;
    }

    @PostMapping
    public ResponseEntity<DetallePresupuestos> guardar(@Valid @RequestBody DetallePresupuestos detallePresupuesto) {
        return ResponseEntity.status(201).body(detallePresupuestoService.guardar(detallePresupuesto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetallePresupuestos> editar(@PathVariable Long id, @Valid @RequestBody DetallePresupuestos detallePresupuesto) {
        return ResponseEntity.ok(detallePresupuestoService.editar(id, detallePresupuesto));
    }

    @GetMapping
    public List<DetallePresupuestos> listar() {
        return detallePresupuestoService.listar();
    }
}
