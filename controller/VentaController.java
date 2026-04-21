package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.VentaRequestDTO;
import com.drover.demo.backend.dto.response.VentaResponseDTO;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.mapper.VentaMapper;
import com.drover.demo.backend.service.VentaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/ventas")
public class VentaController {
    private final VentaService service;
    public VentaController(VentaService service){   
        this.service = service;
    }
    @PostMapping
    public VentaResponseDTO crear(@Valid @RequestBody VentaRequestDTO dto){

        Venta venta = VentaMapper.toEntity(dto);

        Venta guardada = service.crearVenta(venta);

        return VentaMapper.toDTO(guardada);
    }
    @GetMapping
    public List<Venta> listar(){
        return service.listar();
    }
    @GetMapping("/cliente")
    public List<Venta> buscarPorCliente(@RequestParam Integer clienteId){
        return service.buscarPorCliente(clienteId);
    }
    @GetMapping("/usuario")
    public List<Venta> buscarPorUsuario(@RequestParam Integer usuarioId){
        return service.buscarPorUsuario(usuarioId);
    }
    @GetMapping("/estado")
    public List<Venta> buscarPorEstado(@RequestParam Venta.EstadoVenta estado){
        return service.buscarPorEstado(estado);
    }
    @PutMapping("/{id}/estado")
    public Venta actualizarEstado(@PathVariable Integer id,@RequestParam Venta.EstadoVenta estado){
        return service.cambiarEstado(id, estado);
    }
    @DeleteMapping("/{id}/eliminar")
    public void eliminar(@PathVariable Integer id){
        service.cancelar(id);
    
    }
}
