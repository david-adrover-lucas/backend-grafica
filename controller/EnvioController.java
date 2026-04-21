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

import com.drover.demo.backend.dto.request.EnvioRequestDTO;
import com.drover.demo.backend.dto.response.EnvioResponseDTO;
import com.drover.demo.backend.entity.Envio;
import com.drover.demo.backend.mapper.EnvioMapper;
import com.drover.demo.backend.service.EnvioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/envios")
public class EnvioController {

    private final EnvioService service;

    public EnvioController(EnvioService service){
        this.service = service;
    }

    @PostMapping
    public EnvioResponseDTO crearEnvio(@Valid @RequestBody EnvioRequestDTO dto){
        Envio envio =EnvioMapper.toEntity(dto);
        Envio guardar =service.guardaEnvio(envio);
        return EnvioMapper.toDTO(guardar);
    }

    @GetMapping
    public List<Envio> listar(){
        return service.listar();
    }

    @GetMapping("/estado")
    public List<Envio> listarPorEstado(@RequestParam Envio.EstadoEnvio estado){
        return service.listarPorEstado(estado);
    }

    @PutMapping("/{id}/enviado")
    public Envio marcarComoEnviado(@PathVariable Integer id){
        return service.marcarComoEnviado(id);
    }

    @PutMapping("/{id}/entregado")
    public Envio marcarComoEntregado(@PathVariable Integer id){
        return service.marcarComoEntregado(id);
    }

    @PutMapping("/{id}/cancelado")
    public Envio cancelar(@PathVariable Integer id){
        return service.cancelarEnvio(id);
    }

    @GetMapping("/venta/{ventaId}")
    public List<Envio> listarPorVenta(@PathVariable Integer ventaId){
        return service.obtenerPorVenta(ventaId);
    }
}