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

import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.service.InsumoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/insumos")
public class InsumoController {

    private final InsumoService insumoService;

    public InsumoController(InsumoService insumoService) {
        this.insumoService = insumoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody Insumo insumo) {
        insumoService.guardar(insumo);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody Insumo insumo) {
        insumoService.editar(id, insumo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Insumo> listar() {
        return insumoService.listar();
    }

    @GetMapping("/unidad/{unidad}")
    public List<Insumo> listarPorUnidad(@PathVariable String unidad) {
        return insumoService.listarPorUnidad(unidad);
    }
}
