package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.TrabajoTercerizadoRequestDTO;
import com.drover.demo.backend.dto.response.TrabajoTercerizadoResponseDTO;
import com.drover.demo.backend.entity.TrabajoTercerizado;
import com.drover.demo.backend.mapper.TrabajoTercerizadoMapper;
import com.drover.demo.backend.service.TrabajoTercerizadoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/trabajos")
public class TrabajoTercerizadoController {
    private final TrabajoTercerizadoService trabajoService;
    public TrabajoTercerizadoController(TrabajoTercerizadoService trabajoService) {
        this.trabajoService = trabajoService;
    }
    @PostMapping
    public TrabajoTercerizadoResponseDTO guardar(@Valid @RequestBody TrabajoTercerizadoRequestDTO dto) {
        TrabajoTercerizado trabajoTercerizado = TrabajoTercerizadoMapper.toEntity(dto);
        TrabajoTercerizado guardar = trabajoService.registrarEnvio(trabajoTercerizado);
        return TrabajoTercerizadoMapper.toDTO(guardar);
    }
    @GetMapping("/pendientes")
    public List<TrabajoTercerizado> listarPendientes() {
        return trabajoService.listarPendientes();
    }
    @GetMapping("/empresa")
    public List<TrabajoTercerizado> listarPorEmpresa(@RequestParam Integer empresaId) {
        return trabajoService.listarPorEmpresa(empresaId);
    }
    @GetMapping("/empresa/historial")
    public List<TrabajoTercerizado> listarHistorialPorEmpresa(@RequestParam Integer empresaId) {
        return trabajoService.listarPorEmpresa(empresaId);
    }

    @PutMapping("/{id}")
    public TrabajoTercerizado actualizar(@PathVariable Integer id, @RequestBody TrabajoTercerizado trabajo) {
        trabajo.setId(id);
        return trabajoService.registrarEnvio(trabajo);
    }

}
