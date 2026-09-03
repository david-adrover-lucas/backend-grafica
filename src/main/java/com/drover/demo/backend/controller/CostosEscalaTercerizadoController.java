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

import com.drover.demo.backend.entity.CostosEscalaTercerizado;
import com.drover.demo.backend.service.CostosEscalaTercerizadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/costos-escala-tercerizado")
public class CostosEscalaTercerizadoController {

    private final CostosEscalaTercerizadoService costosEscalaTercerizadoService;

    public CostosEscalaTercerizadoController(CostosEscalaTercerizadoService costosEscalaTercerizadoService) {
        this.costosEscalaTercerizadoService = costosEscalaTercerizadoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody CostosEscalaTercerizado costosEscalaTercerizado) {
        costosEscalaTercerizadoService.guardar(costosEscalaTercerizado);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody CostosEscalaTercerizado costosEscalaTercerizado) {
        costosEscalaTercerizadoService.editar(id, costosEscalaTercerizado);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarPorId(@PathVariable Long id) {
        costosEscalaTercerizadoService.desactivarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<CostosEscalaTercerizado> listar() {
        return costosEscalaTercerizadoService.listar();
    }

    @GetMapping("/trabajo-tercerizado/{trabajoTercerizadoId}")
    public List<CostosEscalaTercerizado> listarPorTrabajoTercerizado(@PathVariable Long trabajoTercerizadoId) {
        return costosEscalaTercerizadoService.listarPorTrabajoTercerizado(trabajoTercerizadoId);
    }
}
