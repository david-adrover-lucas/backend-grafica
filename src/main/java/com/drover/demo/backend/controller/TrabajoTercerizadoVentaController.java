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
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping("/proveedor/{proveedorId}/estado/{estado}")
    public List<TrabajoTercerizadoVenta> listarPorProveedorYEstado(@PathVariable Long proveedorId, @PathVariable String estado) {
        return trabajoTercerizadoVentaService.listarPorProveedorYEstado(proveedorId, estado);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<TrabajoTercerizadoVenta> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(trabajoTercerizadoVentaService.cambiarEstado(id, estado));
    }

    @PostMapping("/{id}/deuda")
    public ResponseEntity<Void> generarDeudaProveedor(@PathVariable Long id) {
        trabajoTercerizadoVentaService.generarDeudaProveedor(id);
        return ResponseEntity.status(201).build();
    }
}
