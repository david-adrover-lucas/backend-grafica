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

import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.service.VentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public ResponseEntity<Venta> guardar(@Valid @RequestBody Venta venta) {
        return ResponseEntity.status(201).body(ventaService.guardar(venta));
    }

    @PutMapping
    public ResponseEntity<Venta> editar(@Valid @RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.editar(venta));
    }

    @GetMapping
    public List<Venta> listar() {
        return ventaService.listar();
    }

    @GetMapping("/estado-venta/{estado}")
    public List<Venta> listarEstadoVenta(@PathVariable String estado) {
        return ventaService.listarEstadoVenta(estado);
    }

    @GetMapping("/estado-pago/{estadoPago}")
    public List<Venta> listarEstadoPago(@PathVariable String estadoPago) {
        return ventaService.listarEstadoPago(estadoPago);
    }

    @GetMapping("/fecha")
    public List<Venta> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ventaService.listarPorFecha(desde, hasta);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Venta> listarPorCliente(@PathVariable Long clienteId) {
        return ventaService.listarPorCliente(clienteId);
    }

    @GetMapping("/revendedor/{revendedorId}")
    public List<Venta> listarPorRevendedor(@PathVariable Long revendedorId) {
        return ventaService.listarPorRevendedor(revendedorId);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Venta> cambiarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(ventaService.cambiarEstado(id, estado));
    }
}
