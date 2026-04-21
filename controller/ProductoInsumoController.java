package com.drover.demo.backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drover.demo.backend.dto.request.ProductoInsumoRequestDTO;
import com.drover.demo.backend.dto.response.ProductoInsumoResponseDTO;
import com.drover.demo.backend.entity.ProductoInsumo;
import com.drover.demo.backend.mapper.ProductoInsumoMapper;
import com.drover.demo.backend.service.ProductoInsumoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/producto_insumos")
public class ProductoInsumoController {

    private final ProductoInsumoService service;

    public ProductoInsumoController(ProductoInsumoService service){
        this.service = service;
    }

    @PostMapping
    public ProductoInsumoResponseDTO crear(@Valid @RequestBody ProductoInsumoRequestDTO dto){
        ProductoInsumo productoInsumo = ProductoInsumoMapper.toEntity(dto);
        ProductoInsumo guardar = service.guardar(productoInsumo);
        return ProductoInsumoMapper.toDTO(guardar);
    }

    @GetMapping("/producto/{productoId}")
    public List<ProductoInsumo> listarProducto(@PathVariable Integer productoId){
        return service.listarPorProducto(productoId);
    }

    @GetMapping("/insumo/{insumoId}")
    public List<ProductoInsumo> listarPorInsumo(@PathVariable Integer insumoId){
        return service.listarPorInsumo(insumoId);
    }

    @PutMapping("/{id}")
    public ProductoInsumo actualizar(@PathVariable Integer id, @RequestBody ProductoInsumo pi){
        pi.setId(id);
        return service.guardar(pi);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}
