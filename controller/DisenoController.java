package com.drover.demo.backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Diseno;
import com.drover.demo.backend.service.DisenoService;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/disenos")
public class DisenoController {
    private final DisenoService service;
    public DisenoController(DisenoService service) {
        this.service = service;
    }   
    @PostMapping
    public Diseno crear(@RequestBody Diseno diseno){
        return service.crearDiseno(diseno);
    }    
    @GetMapping
    public List<Diseno> listar(){
        return service.listar();
    }
    @GetMapping("/buscar")
    public List<Diseno> buscar(
        @RequestParam(required = false) String categoria,
        @RequestParam(required = false) String etiqueta,
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) Boolean reutilizable
    ) {
        return service.buscarDisenos(categoria, etiqueta, nombre, reutilizable);
    }


}
    

