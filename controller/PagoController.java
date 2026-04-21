package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.PagoRequestDTO;
import com.drover.demo.backend.dto.response.PagoResponseDTO;
import com.drover.demo.backend.entity.Pago;
import com.drover.demo.backend.mapper.PagoMapper;
import com.drover.demo.backend.service.PagoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService service;

    public PagoController(PagoService service) {
        this.service = service;
    }

    @PostMapping
    public PagoResponseDTO crear(@Valid @RequestBody PagoRequestDTO dto) {
        Pago pago = PagoMapper.toEntity(dto);
        Pago guardar = service.registrarPago(pago);
        return PagoMapper.toDTO(guardar);
    }

    @GetMapping("/venta/{ventaId}")
    public List<Pago> listarPorVenta(@PathVariable Integer ventaId) {
        return service.listarPorVenta(ventaId);
    }
}