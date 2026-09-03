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

import com.drover.demo.backend.entity.PreciosEscalaProducto;
import com.drover.demo.backend.service.PreciosEscalaProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/precios-escala-producto")
public class PreciosEscalaProductoController {

    private final PreciosEscalaProductoService preciosEscalaProductoService;

    public PreciosEscalaProductoController(PreciosEscalaProductoService preciosEscalaProductoService) {
        this.preciosEscalaProductoService = preciosEscalaProductoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody PreciosEscalaProducto preciosEscalaProducto) {
        preciosEscalaProductoService.guardar(preciosEscalaProducto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody PreciosEscalaProducto preciosEscalaProducto) {
        preciosEscalaProductoService.editar(id, preciosEscalaProducto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        preciosEscalaProductoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/producto/{productoId}")
    public List<PreciosEscalaProducto> listarPorProductoId(@PathVariable Long productoId) {
        return preciosEscalaProductoService.listarPorProductoId(productoId);
    }
}
