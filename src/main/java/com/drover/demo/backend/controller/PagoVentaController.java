package com.drover.demo.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.PagoVenta;
import com.drover.demo.backend.service.PagoVentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagos-venta")
public class PagoVentaController {

    private final PagoVentaService pagoVentaService;

    public PagoVentaController(PagoVentaService pagoVentaService) {
        this.pagoVentaService = pagoVentaService;
    }

    @PostMapping
    public ResponseEntity<PagoVenta> guardar(@Valid @RequestBody PagoVenta pagoVenta) {
        return ResponseEntity.status(201).body(pagoVentaService.guardar(pagoVenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagoVenta> editar(@PathVariable Long id, @Valid @RequestBody PagoVenta pagoVenta) {
        return ResponseEntity.ok(pagoVentaService.editar(id, pagoVenta));
    }

    @GetMapping
    public List<PagoVenta> listar() {
        return pagoVentaService.listar();
    }

    @GetMapping("/venta/{ventaId}")
    public List<PagoVenta> listarPorVenta(@PathVariable Long ventaId) {
        return pagoVentaService.listarPorVenta(ventaId);
    }

    @GetMapping("/fecha")
    public List<PagoVenta> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return pagoVentaService.listarPorFecha(desde, hasta);
    }

    @GetMapping("/medio-pago/{medioPago}")
    public List<PagoVenta> listarPorMedioPago(@PathVariable String medioPago) {
        return pagoVentaService.listarPorMedioPago(medioPago);
    }
}
