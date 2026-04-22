package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.ClienteRequestDTO;
import com.drover.demo.backend.dto.response.ClienteResponseDTO;
import com.drover.demo.backend.entity.Cliente;
import com.drover.demo.backend.mapper.ClienteMapper;
import com.drover.demo.backend.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @PostMapping
    public ClienteResponseDTO crear(@Valid @RequestBody ClienteRequestDTO dto) {
        Cliente cliente = ClienteMapper.toEntity(dto);
        Cliente guardar = service.guardar(cliente);
        return ClienteMapper.toDTO(guardar);
    }

    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return service.listar()
        .stream()
        .map(ClienteMapper :: toDTO)
        .toList();

    }

    @GetMapping("/buscar")
    public List<Cliente> buscar(@RequestParam String nombre) {
        return service.buscar(nombre);
    }
}
