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

import com.drover.demo.backend.entity.DetalleCompra;
import com.drover.demo.backend.service.DetalleCompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/detalles-compra")
public class DetalleCompraController {

    private final DetalleCompraService detalleCompraService;

    public DetalleCompraController(DetalleCompraService detalleCompraService) {
        this.detalleCompraService = detalleCompraService;
    }

    @PostMapping
    public ResponseEntity<DetalleCompra> guardar(@Valid @RequestBody DetalleCompra detalleCompra) {
        return ResponseEntity.status(201).body(detalleCompraService.guardar(detalleCompra));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody DetalleCompra detalleCompra) {
        detalleCompraService.editar(id, detalleCompra);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<DetalleCompra> listar() {
        return detalleCompraService.listar();
    }
}
