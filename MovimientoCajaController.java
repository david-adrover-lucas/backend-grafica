package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.MovimientoCajaRequestDTO;
import com.drover.demo.backend.dto.response.MovimientoCajaResponseDTO;
import com.drover.demo.backend.entity.MovimientoCaja;
import com.drover.demo.backend.mapper.MovimientoCajaMapper;
import com.drover.demo.backend.service.MovimientoCajaService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")

@RequestMapping("/movimientos")
public class MovimientoCajaController {

    private final MovimientoCajaService service;

    public MovimientoCajaController(MovimientoCajaService service) {
        this.service = service;
    }

    @PostMapping
    public MovimientoCajaResponseDTO crear(@Valid @RequestBody MovimientoCajaRequestDTO dto){

        MovimientoCaja mov = MovimientoCajaMapper.toEntity(dto);

        MovimientoCaja guardado = service.registrarMovimiento(mov);

        return MovimientoCajaMapper.toDTO(guardado);
    }

    @GetMapping("/cuenta/{cuentaId}")
    public List<MovimientoCaja> porCuenta(@PathVariable Integer cuentaId) {
        return service.listarPorCuenta(cuentaId);
    }
}
