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

import com.drover.demo.backend.entity.MovimientoStock;
import com.drover.demo.backend.service.MovimientoStockService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/movimientos-stock")
public class MovimientoStockController {

    private final MovimientoStockService movimientoStockService;

    public MovimientoStockController(MovimientoStockService movimientoStockService) {
        this.movimientoStockService = movimientoStockService;
    }

    @PostMapping
    public ResponseEntity<Void> registrarMovimiento(@Valid @RequestBody MovimientoStock movimiento) {
        movimientoStockService.registrarMovimiento(movimiento);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody MovimientoStock movimiento) {
        movimientoStockService.editar(id, movimiento);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<MovimientoStock> listarTodo() {
        return movimientoStockService.listarTodo();
    }

    @GetMapping("/insumo/{insumoId}")
    public List<MovimientoStock> listarPorInsumo(@PathVariable Long insumoId) {
        return movimientoStockService.listarPorInsumo(insumoId);
    }

    @GetMapping("/fecha")
    public List<MovimientoStock> listarPorRangoFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return movimientoStockService.listarPorRangoFechas(desde, hasta);
    }
}
