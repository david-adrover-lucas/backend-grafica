package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.PresupuestoDetalleRequestDTO;
import com.drover.demo.backend.dto.response.PresupuestoDetalleResponseDTO;
import com.drover.demo.backend.entity.PresupuestoDetalle;
import com.drover.demo.backend.mapper.PresupuestoDetalleMapper;
import com.drover.demo.backend.service.PresupuestoDetalleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/presupuesto-detalle")
public class PresupuestoDetalleController {

    private final PresupuestoDetalleService service;

    public PresupuestoDetalleController(PresupuestoDetalleService service) {
        this.service = service;
    }

    @PostMapping
    public PresupuestoDetalleResponseDTO agregar(@Valid @RequestBody PresupuestoDetalleRequestDTO dto) {
        PresupuestoDetalle presupuestoDetalle = PresupuestoDetalleMapper.toEntity(dto);
        PresupuestoDetalle guardar = service.agregarProducto(presupuestoDetalle);
        return PresupuestoDetalleMapper.toDTO(guardar);
    }

    @GetMapping("/presupuesto/{id}")
    public List<PresupuestoDetalle> listar(@PathVariable Integer id) {
        return service.listarPorPresupuesto(id);
    }
}