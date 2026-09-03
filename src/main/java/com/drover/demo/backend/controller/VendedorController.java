package com.drover.demo.backend.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.entity.Vendedor;
import com.drover.demo.backend.service.VendedorService;

@RestController
@RequestMapping("/api/vendedores")
public class VendedorController {

    private final VendedorService vendedorService;

    public VendedorController(VendedorService vendedorService) {
        this.vendedorService = vendedorService;
    }

    @GetMapping
    public List<Vendedor> listar() {
        return vendedorService.listar();
    }

    @GetMapping("/fecha-alta/{alta}")
    public List<Vendedor> listarFechaAlta(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate alta) {
        return vendedorService.listarFechaAlta(alta);
    }
}
