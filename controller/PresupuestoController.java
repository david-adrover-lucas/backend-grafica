package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.PresupuestoRequestDTO;
import com.drover.demo.backend.dto.response.PresupuestoResponseDTO;
import com.drover.demo.backend.entity.Presupuesto;
import com.drover.demo.backend.mapper.PresupuestoMapper;
import com.drover.demo.backend.service.PresupuestoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/presupuestos")
public class PresupuestoController {

    private final PresupuestoService service;

    public PresupuestoController(PresupuestoService service) {
        this.service = service;
    }

    @PostMapping
    public PresupuestoResponseDTO crear(@Valid @RequestBody PresupuestoRequestDTO p) {
        Presupuesto presupuesto = PresupuestoMapper.toEntity(p);
        Presupuesto guardar = service.crear(presupuesto);
        return PresupuestoMapper.toDTO(guardar);
    }

    @GetMapping
    public List<Presupuesto> listar() {
        return service.listar();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Presupuesto> porCliente(@PathVariable Integer clienteId) {
        return service.buscarPorCliente(clienteId);
    }

    @PutMapping("/{id}/calcular")
    public Presupuesto calcular(@PathVariable Integer id) {
        return service.calcularTotal(id);
    }
}
