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
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Cuenta;
import com.drover.demo.backend.service.CuentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @PostMapping
    public ResponseEntity<Cuenta> guardar(@Valid @RequestBody Cuenta cuenta) {
        return ResponseEntity.status(201).body(cuentaService.guardar(cuenta));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> editar(@PathVariable Long id, @Valid @RequestBody Cuenta cuenta) {
        return ResponseEntity.ok(cuentaService.editar(id, cuenta));
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        cuentaService.desactivar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Cuenta> listar() {
        return cuentaService.listar();
    }

    @GetMapping("/activas")
    public List<Cuenta> listarActivas() {
        return cuentaService.listarActivas();
    }

    @GetMapping("/tipo/{tipo}")
    public List<Cuenta> listarPorTipo(@PathVariable String tipo) {
        return cuentaService.listarPorTipo(tipo);
    }
}
