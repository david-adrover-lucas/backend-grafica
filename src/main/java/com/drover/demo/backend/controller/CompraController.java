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

import com.drover.demo.backend.entity.Compra;
import com.drover.demo.backend.service.CompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compras")
public class CompraController {

    private final CompraService compraService;

    public CompraController(CompraService compraService) {
        this.compraService = compraService;
    }

    @PostMapping
    public ResponseEntity<Void> guardar(@Valid @RequestBody Compra compra) {
        compraService.guardar(compra);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> editar(@PathVariable Long id, @Valid @RequestBody Compra compra) {
        compraService.editar(id, compra);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Compra> listar() {
        return compraService.listar();
    }

    @GetMapping("/fecha")
    public List<Compra> listarPorFechCompras(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return compraService.listarPorFechCompras(desde, hasta);
    }

    @GetMapping("/numero/{nroCompra}")
    public Compra listarPorNroCompra(@PathVariable String nroCompra) {
        return compraService.listarPorNroCompra(nroCompra);
    }
}
