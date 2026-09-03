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

import com.drover.demo.backend.entity.PrecioRevendedor;
import com.drover.demo.backend.service.PrecioRevendedorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/precios-revendedor")
public class PrecioRevendedorController {

    private final PrecioRevendedorService precioRevendedorService;

    public PrecioRevendedorController(PrecioRevendedorService precioRevendedorService) {
        this.precioRevendedorService = precioRevendedorService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody PrecioRevendedor precioRevendedor) {
        precioRevendedorService.guardar(precioRevendedor);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody PrecioRevendedor precioRevendedor) {
        precioRevendedorService.editar(id, precioRevendedor);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<PrecioRevendedor> listar() {
        return precioRevendedorService.listar();
    }

    @GetMapping("/revendedor/{revendedorId}")
    public List<PrecioRevendedor> listarPorRevendedor(@PathVariable Long revendedorId) {
        return precioRevendedorService.listarPorRevendedor(revendedorId);
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarporID(@PathVariable Long id) {
        precioRevendedorService.desactivarporID(id);
        return ResponseEntity.noContent().build();
    }
}
