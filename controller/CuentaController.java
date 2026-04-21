package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.CuentaRequestDTO;
import com.drover.demo.backend.dto.response.CuentaResponseDTO;
import com.drover.demo.backend.entity.Cuenta;
import com.drover.demo.backend.mapper.CuentaMapper;
import com.drover.demo.backend.service.CuentaService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService service;
    public CuentaController(CuentaService service){
        this.service = service;
    }
    @PostMapping
    public CuentaResponseDTO crear(@Valid @RequestBody CuentaRequestDTO dto){
        Cuenta cuenta = CuentaMapper.toEntity(dto);
        Cuenta guardar = service.guardar(cuenta);
        
        return CuentaMapper.toDTO(guardar);
    }
    @GetMapping
    public List<Cuenta> listar(){
        return service.listar();
    }
    @GetMapping("/activas")
    public List<Cuenta> listarActivas(){
        return service.listarActivas();
    }
    @GetMapping("/nombre")
    public List<Cuenta> buscar(@RequestParam String nombre){
        return service.buscarPorNombre(nombre);
    }
    @PutMapping("/{id}")
    public Cuenta actualizar(@PathVariable Integer id, @RequestBody Cuenta c){
        c.setId(id);
        return service.guardar(c);
    }
    @DeleteMapping("/{id}/desactivar")
    public void desactivar(@PathVariable Integer id){
        service.desactivar(id);
    }
}
