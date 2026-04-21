package com.drover.demo.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.ComisionRequestDTO;
import com.drover.demo.backend.dto.response.ComisionResponseDTO;
import com.drover.demo.backend.mapper.ComisionMapper;
import com.drover.demo.backend.service.ComisionService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/comisiones")
public class ComisionController {

    private final ComisionService service;

    public ComisionController(ComisionService service) {
        this.service = service;
    }

    @PostMapping
    public ComisionResponseDTO crear(@Valid @RequestBody ComisionRequestDTO dto) {
        return ComisionMapper.toDTO(service.guardar(ComisionMapper.toEntity(dto)));
    }

    @GetMapping
    public List<ComisionResponseDTO> listar() {
        return service.listar()
                .stream()
                .map(ComisionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/usuario")
    public List<ComisionResponseDTO> obtenerPorUsuario(@RequestParam Integer usuarioId) {
        return service.obtenerPorUsuario(usuarioId)
                .stream()
                .map(ComisionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.eliminar(id);
    }
}
