package com.drover.demo.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.ProductoRequestDTO;
import com.drover.demo.backend.dto.response.ProductoResponseDTO;
import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.mapper.ProductoMapper;
import com.drover.demo.backend.service.ProductoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService service;
    public ProductoController(ProductoService service){
        this.service = service;
    }   
    @PostMapping
    public ProductoResponseDTO crear(@Valid @RequestBody ProductoRequestDTO dto){
        Producto producto = ProductoMapper.toEntity(dto);
        Producto guardar = service.guardar(producto);
        return ProductoMapper.toDTO(guardar);
    }
    @GetMapping
    public List<ProductoResponseDTO> listar(){
        return service.listarActivos()
        .stream()
        .map( ProductoMapper :: toDTO)
        .toList();
    }
    @PutMapping("/{id}")
    public Producto actualizar(@RequestBody Producto p, @PathVariable Integer id){
        p.setId(id);
        return service.guardar(p);   
    }
    @DeleteMapping("/{id}/desactivar")
    public void desactivar(@PathVariable Integer id){
        service.desactivar(id);
    }

}