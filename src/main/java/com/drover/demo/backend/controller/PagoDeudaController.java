package com.drover.demo.backend.controller;

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

import com.drover.demo.backend.entity.PagoDeuda;
import com.drover.demo.backend.service.PagoDeudaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pagos-deuda")
public class PagoDeudaController {

    private final PagoDeudaService pagoDeudaService;

    public PagoDeudaController(PagoDeudaService pagoDeudaService) {
        this.pagoDeudaService = pagoDeudaService;
    }

    @PostMapping
    public ResponseEntity<PagoDeuda> pagar(@Valid @RequestBody PagoDeuda pagoDeuda) {
        return ResponseEntity.status(201).body(pagoDeudaService.pagar(pagoDeuda));
    }

    @GetMapping
    public List<PagoDeuda> listar() {
        return pagoDeudaService.listar();
    }

    @GetMapping("/deuda/{deudaId}")
    public List<PagoDeuda> listarPorDeuda(@PathVariable Long deudaId) {
        return pagoDeudaService.listarPorDeuda(deudaId);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public List<PagoDeuda> listarPorCuenta(@PathVariable Long cuentaId) {
        return pagoDeudaService.listarPorCuenta(cuentaId);
    }

    @GetMapping("/fecha")
    public List<PagoDeuda> listarPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return pagoDeudaService.listarPorFecha(desde, hasta);
    }
}
