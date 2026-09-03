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

import com.drover.demo.backend.entity.ComisionesEscalaProducto;
import com.drover.demo.backend.service.ComisionesEscalaProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/comisiones-escala-producto")
public class ComisionesEscalaProductoController {

    private final ComisionesEscalaProductoService comisionesEscalaProductoService;

    public ComisionesEscalaProductoController(ComisionesEscalaProductoService comisionesEscalaProductoService) {
        this.comisionesEscalaProductoService = comisionesEscalaProductoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody ComisionesEscalaProducto escala) {
        comisionesEscalaProductoService.guardar(escala);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody ComisionesEscalaProducto escala) {
        comisionesEscalaProductoService.editar(id, escala);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarEscala(@PathVariable Long id) {
        comisionesEscalaProductoService.desactivarEscala(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto/{productoId}")
    public List<ComisionesEscalaProducto> listarPorProducto(@PathVariable Long productoId) {
        return comisionesEscalaProductoService.listarPorProducto(productoId);
    }
}
