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

import com.drover.demo.backend.entity.TrabajosTercerizado;
import com.drover.demo.backend.service.TrabajosTercerizadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trabajos-tercerizados")
public class TrabajosTercerizadoController {

    private final TrabajosTercerizadoService trabajosTercerizadoService;

    public TrabajosTercerizadoController(TrabajosTercerizadoService trabajosTercerizadoService) {
        this.trabajosTercerizadoService = trabajosTercerizadoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody TrabajosTercerizado trabajosTercerizado) {
        trabajosTercerizadoService.guardar(trabajosTercerizado);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody TrabajosTercerizado trabajosTercerizado) {
        trabajosTercerizadoService.editar(id, trabajosTercerizado);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivarTrabajo(@PathVariable Long id) {
        trabajosTercerizadoService.desactivarTrabajo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<TrabajosTercerizado> listar() {
        return trabajosTercerizadoService.listar();
    }

    @GetMapping("/proveedor/{proveedorId}")
    public List<TrabajosTercerizado> listarPorProveedor(@PathVariable Long proveedorId) {
        return trabajosTercerizadoService.listarPorProveedor(proveedorId);
    }

    @GetMapping("/nombre/{nombre}")
    public List<TrabajosTercerizado> buscarPorNombre(@PathVariable String nombre) {
        return trabajosTercerizadoService.buscarPorNombre(nombre);
    }
}
