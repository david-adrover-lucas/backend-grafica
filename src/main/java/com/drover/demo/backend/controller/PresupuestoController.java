package com.drover.demo.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
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

import com.drover.demo.backend.entity.Presupuesto;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.service.PresupuestoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/presupuestos")
public class PresupuestoController {

    private final PresupuestoService presupuestoService;

    public PresupuestoController(PresupuestoService presupuestoService) {
        this.presupuestoService = presupuestoService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody Presupuesto presupuesto) {
        presupuestoService.guardar(presupuesto);
        return ResponseEntity.status(201).build();
    }

    @PutMapping
    public ResponseEntity<Void> editar(@Valid @RequestBody Presupuesto presupuesto) {
        presupuestoService.editar(presupuesto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Presupuesto> listar() {
        return presupuestoService.listar();
    }

    @GetMapping("/estado/{estado}")
    public List<Presupuesto> listarPorEsatado(@PathVariable String estado) {
        return presupuestoService.listarPorEsatado(estado);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Presupuesto> listarPorCliente(@PathVariable Long clienteId) {
        return presupuestoService.listarPorCliente(clienteId);
    }

    @GetMapping("/revendedor/{revendedorId}")
    public List<Presupuesto> listarPorRevendedor(@PathVariable Long revendedorId) {
        return presupuestoService.listarPorRevendedor(revendedorId);
    }

    @GetMapping("/fecha")
    public List<Presupuesto> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return presupuestoService.listarPorFecha(desde, hasta);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        presupuestoService.cambiarEstado(id, estado);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/convertir-a-venta")
    public ResponseEntity<Venta> convertirConfirmadoAVenta(@PathVariable Long id) {
        return ResponseEntity.status(201).body(presupuestoService.convertirConfirmadoAVenta(id));
    }
}
