package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.ReporteRequestDTO;
import com.drover.demo.backend.dto.response.ReporteResponseDTO;
import com.drover.demo.backend.entity.Reporte;
import com.drover.demo.backend.mapper.ReporteMapper;
import com.drover.demo.backend.service.ReporteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @PostMapping
    public ReporteResponseDTO crear(@Valid @RequestBody ReporteRequestDTO dto){
        Reporte reporte = ReporteMapper.toEntity(dto);
        Reporte guardar = service.guardar(reporte);  // Cambié 'guardar' por 'reporte'
        return ReporteMapper.toDTO(guardar);
    }

    @GetMapping("/tipo")
    public List<Reporte> porTipo(@RequestParam String tipo) {
        return service.listarPorTipo(tipo);
    }
}