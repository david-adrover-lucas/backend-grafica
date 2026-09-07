package com.drover.demo.backend.controller;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Deuda;
import com.drover.demo.backend.service.DeudaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/deudas")
public class DeudaController {

    private final DeudaService deudaService;

    public DeudaController(DeudaService deudaService) {
        this.deudaService = deudaService;
    }

    @PostMapping
    public ResponseEntity<Deuda> guardar(@Valid @RequestBody Deuda deuda) {
        return ResponseEntity.status(201).body(deudaService.guardar(deuda));
    }

    @PostMapping("/sueldos/{empleadoId}")
    public ResponseEntity<Deuda> generarSueldoMensual(
            @PathVariable Long empleadoId,
            @RequestParam Integer anio,
            @RequestParam Integer mes) {
        return ResponseEntity.status(201).body(deudaService.generarSueldoMensual(empleadoId, anio, mes));
    }

    @PostMapping("/comisiones/{personaId}")
    public ResponseEntity<Deuda> generarComisionSemanal(
            @PathVariable Long personaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.status(201).body(deudaService.generarComisionSemanal(personaId, desde, hasta));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Deuda> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(deudaService.cancelar(id));
    }

    @GetMapping
    public List<Deuda> listar() {
        return deudaService.listar();
    }

    @GetMapping("/estado/{estado}")
    public List<Deuda> listarPorEstado(@PathVariable String estado) {
        return deudaService.listarPorEstado(estado);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Deuda> listarPorTipo(@PathVariable String tipo) {
        return deudaService.listarPorTipo(tipo);
    }

    @GetMapping("/persona/{personaId}")
    public List<Deuda> listarPorPersona(@PathVariable Long personaId) {
        return deudaService.listarPorPersona(personaId);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public List<Deuda> listarPorProveedor(@PathVariable Long proveedorId) {
        return deudaService.listarPorProveedor(proveedorId);
    }

    @GetMapping("/fecha")
    public List<Deuda> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return deudaService.listarPorFecha(desde, hasta);
    }
}
