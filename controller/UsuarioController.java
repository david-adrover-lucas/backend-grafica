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

import com.drover.demo.backend.dto.request.UsuarioRequestDTO;
import com.drover.demo.backend.dto.response.UsuarioResponseDTO;
import com.drover.demo.backend.entity.Usuario;
import com.drover.demo.backend.mapper.UsuarioMapper;
import com.drover.demo.backend.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping
    public UsuarioResponseDTO crear(@Valid @RequestBody UsuarioRequestDTO dto) {
        Usuario  usuario = UsuarioMapper.toEntity(dto);
        Usuario guardar = service.guardar(usuario);
        return UsuarioMapper.toDTO(guardar);
    }

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return service.listar()
        .stream()
        .map(UsuarioMapper :: toDTO)
        .toList();
    }

    @GetMapping("/rol")
    public List<Usuario> porRol(@RequestParam String rol) {
        return service.buscarPorRol(rol);
    }

    @PutMapping("/{id}/desactivar")
    public void desactivar(@PathVariable Integer id) {
        service.desactivar(id);
    }
}