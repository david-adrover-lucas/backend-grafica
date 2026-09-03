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

import com.drover.demo.backend.entity.TrabajoTercerizadoVenta;
import com.drover.demo.backend.service.TrabajoTercerizadoVentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trabajos-tercerizados-venta")
public class TrabajoTercerizadoVentaController {

    private final TrabajoTercerizadoVentaService trabajoTercerizadoVentaService;

    public TrabajoTercerizadoVentaController(TrabajoTercerizadoVentaService trabajoTercerizadoVentaService) {
        this.trabajoTercerizadoVentaService = trabajoTercerizadoVentaService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody TrabajoTercerizadoVenta trabajo) {
        trabajoTercerizadoVentaService.guardar(trabajo);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody TrabajoTercerizadoVenta trabajo) {
        trabajoTercerizadoVentaService.editar(id, trabajo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<TrabajoTercerizadoVenta> listar() {
        return trabajoTercerizadoVentaService.listar();
    }

    @GetMapping("/estado/{estado}")
    public List<TrabajoTercerizadoVenta> listarPorEstado(@PathVariable String estado) {
        return trabajoTercerizadoVentaService.listarPorEstado(estado);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public List<TrabajoTercerizadoVenta> listarPorProveedor(@PathVariable Long proveedorId) {
        return trabajoTercerizadoVentaService.listarPorProveedor(proveedorId);
    }
}
