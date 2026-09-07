package com.drover.demo.backend.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.MovimientoCaja;
import com.drover.demo.backend.service.MovimientoCajaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movimientos-caja")
public class MovimientoCajaController {

    private final MovimientoCajaService movimientoCajaService;

    public MovimientoCajaController(MovimientoCajaService movimientoCajaService) {
        this.movimientoCajaService = movimientoCajaService;
    }

    @PostMapping
    public ResponseEntity<MovimientoCaja> registrar(@Valid @RequestBody MovimientoCaja movimiento) {
        return ResponseEntity.status(201).body(movimientoCajaService.registrar(movimiento));
    }

    @PostMapping("/transferir")
    public ResponseEntity<List<MovimientoCaja>> transferir(
            @RequestParam Long cuentaOrigenId,
            @RequestParam Long cuentaDestinoId,
            @RequestParam BigDecimal monto,
            @RequestParam(required = false) String concepto) {
        return ResponseEntity.status(201).body(
            movimientoCajaService.transferir(cuentaOrigenId, cuentaDestinoId, monto, concepto)
        );
    }

    @GetMapping
    public List<MovimientoCaja> listar() {
        return movimientoCajaService.listar();
    }

    @GetMapping("/cuenta/{cuentaId}")
    public List<MovimientoCaja> listarPorCuenta(@PathVariable Long cuentaId) {
        return movimientoCajaService.listarPorCuenta(cuentaId);
    }

    @GetMapping("/fecha")
    public List<MovimientoCaja> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return movimientoCajaService.listarPorFecha(desde, hasta);
    }

    @GetMapping("/cuenta/{cuentaId}/fecha")
    public List<MovimientoCaja> listarPorCuentaYFecha(
            @PathVariable Long cuentaId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return movimientoCajaService.listarPorCuentaYFecha(cuentaId, desde, hasta);
    }

    @GetMapping("/total")
    public BigDecimal totalPorTipoYFecha(
            @RequestParam String tipo,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return movimientoCajaService.totalPorTipoYFecha(tipo, desde, hasta);
    }
}
