package com.drover.demo.backend.controller;

import java.time.LocalDateTime;
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

import com.drover.demo.backend.dto.request.HistorialPresupuestoRequestDTO;
import com.drover.demo.backend.dto.response.HistorialPresupuestoResponseDTO;
import com.drover.demo.backend.entity.HistorialPresupuesto;
import com.drover.demo.backend.mapper.HistorialPresupuestoMapper;
import com.drover.demo.backend.service.HistorialPresupuestoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/historial-presupuestos")
public class HistorialPresupuestoController {
    private final HistorialPresupuestoService service;
    public HistorialPresupuestoController(HistorialPresupuestoService service) {
        this.service = service;
    }
    @PostMapping
    public HistorialPresupuestoResponseDTO crear(@Valid @RequestBody HistorialPresupuestoRequestDTO hp) {
        HistorialPresupuesto historialPresupuesto = HistorialPresupuestoMapper.toEntity(hp);
        HistorialPresupuesto guardar = service.guardar(historialPresupuesto);
        return HistorialPresupuestoMapper.toDTO(guardar);
    }
    @GetMapping
    public List<HistorialPresupuesto> listar(){
        return service.listar();
    }
    @GetMapping("/cliente/{clienteId}")
    public List<HistorialPresupuesto> buscarPorCliente(@PathVariable Integer clienteId){
        return service.buscarPorCliente(clienteId);
    }
    @GetMapping("/estado/{estado}")
    public List<HistorialPresupuesto> buscarPorEstado(@PathVariable String estado){ 
        return service.buscarPorEstado(estado);
    }
    @GetMapping("/fecha")
    public List<HistorialPresupuesto> buscarPorFecha(
            @RequestParam LocalDateTime desde,
            @RequestParam LocalDateTime hasta) {

        return service.buscarPorFecha(desde, hasta);
    }
    @PutMapping("/{id}/estado")
    public HistorialPresupuesto actualizarEstado(@PathVariable Integer id, @RequestBody HistorialPresupuesto hp) {
        hp.setId(id);
        return service.guardar(hp);
    }
    @DeleteMapping  ("/{id}")
    public void eliminar(@PathVariable Integer id) {
        service.cancelar(id);
    }
    


    
}