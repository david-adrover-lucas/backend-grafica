package com.drover.demo.backend.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.CajaRequestDTO;
import com.drover.demo.backend.dto.response.CajaResponseDTO;
import com.drover.demo.backend.mapper.CajaMapper;
import com.drover.demo.backend.service.CajaService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/caja")
public class CajaController {

    private final CajaService service;

    public CajaController(CajaService service) {
        this.service = service;
    }

    // ➕ SUMAR SALDO
    @PostMapping("/sumar")
    public CajaResponseDTO sumarSaldo(@Valid @RequestBody CajaRequestDTO dto) {

        service.sumarSaldo(dto.getCuentaId(), dto.getMonto());

        return CajaMapper.toDTO(
                service.obtenerCaja(dto.getCuentaId())
        );
    }

    // ➖ RESTAR SALDO
    @PostMapping("/restar")
    public CajaResponseDTO restarSaldo(@Valid @RequestBody CajaRequestDTO dto) {

        service.restarSaldo(dto.getCuentaId(), dto.getMonto());

        return CajaMapper.toDTO(
                service.obtenerCaja(dto.getCuentaId())
        );
    }

    // 📊 OBTENER UNA CAJA
    @GetMapping("/{cuentaId}")
    public CajaResponseDTO obtenerCaja(@PathVariable Integer cuentaId) {

        return CajaMapper.toDTO(
                service.obtenerCaja(cuentaId)
        );
    }

    // 📊 LISTAR TODAS
    @GetMapping
    public List<CajaResponseDTO> listarCajas() {
        return service.listarCajas()
                .stream()
                .map(CajaMapper::toDTO)
                .collect(Collectors.toList());
    }

    // ❌ ELIMINAR
    @DeleteMapping("/{cuentaId}")
    public void eliminarCaja(@PathVariable Integer cuentaId) {
        service.eliminarCaja(cuentaId);
    }
}