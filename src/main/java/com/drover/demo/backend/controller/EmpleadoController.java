package com.drover.demo.backend.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Empleado;
import com.drover.demo.backend.service.EmpleadoService;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PatchMapping("/{id}/sueldo")
    public ResponseEntity<Void> asignarSueldo(@PathVariable Long id, @RequestParam BigDecimal sueldo) {
        empleadoService.asignarSueldo(id, sueldo);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listar();
    }
}
