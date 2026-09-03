package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Cliente;
import com.drover.demo.backend.service.ClienteService;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listar() {
        return clienteService.listar();
    }

    @PatchMapping("/{id}/departamento")
    public ResponseEntity<Void> actualizarDepartamento(@PathVariable Long id, @RequestParam String departamento) {
        clienteService.actualizarDepartamento(id, departamento);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/departamento/{departamento}")
    public List<Cliente> listarPorDepartamento(@PathVariable String departamento) {
        return clienteService.listarPorDepartamento(departamento);
    }

    @GetMapping("/numero/{numero}")
    public Cliente buscarPorNumero(@PathVariable String numero) {
        return clienteService.buscarPorNumero(numero);
    }
}
