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

import com.drover.demo.backend.entity.EmpresaTercerizada;
import com.drover.demo.backend.service.EmpresaTercerizadaService;

// ✅ cambiado de @Repository a @RestController
@RestController
@RequestMapping("/empresas")
public class EmpresaTercerizadaController {

    private final EmpresaTercerizadaService service;

    public EmpresaTercerizadaController(EmpresaTercerizadaService service) {
        this.service = service;
    }

    @PostMapping
    public EmpresaTercerizada crear(@RequestBody EmpresaTercerizada e){
        return service.guardar(e);
    }

    @GetMapping
    public List<EmpresaTercerizada> listar(){
        return service.listar();
    }

    @GetMapping("/activas")
    public List<EmpresaTercerizada> listarActiva(){
        return service.listarActivas();
    }

    @GetMapping("/buscar")
    public List<EmpresaTercerizada> buscarPorNombre(@RequestParam String nombre){
        return service.buscarPorNombre(nombre);
    }

    @PutMapping("/{id}/desactivar")
    public void desactivar(@PathVariable Integer id){
        service.desactivar(id);
    }
}
