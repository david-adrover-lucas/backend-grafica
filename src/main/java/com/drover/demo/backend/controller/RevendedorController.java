package com.drover.demo.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Revendedor;
import com.drover.demo.backend.service.RevendedorService;

@RestController
@RequestMapping("/api/revendedores")
public class RevendedorController {

    private final RevendedorService revendedorService;

    public RevendedorController(RevendedorService revendedorService) {
        this.revendedorService = revendedorService;
    }

    @GetMapping
    public List<Revendedor> listar() {
        return revendedorService.listar();
    }

    @GetMapping("/fecha/{alta}")
    public List<Revendedor> listarPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate alta) {
        return revendedorService.listarPorFecha(alta);
    }
}
