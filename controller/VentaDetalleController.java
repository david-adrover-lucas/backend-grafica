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

import com.drover.demo.backend.dto.request.VentaDetalleRequestDTO;
import com.drover.demo.backend.dto.response.VentaDetalleResponseDTO;
import com.drover.demo.backend.entity.VentaDetalle;
import com.drover.demo.backend.mapper.VentaDetalleMapper;
import com.drover.demo.backend.service.VentaDetalleService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/venta-detalles")
public class VentaDetalleController {   
    private final VentaDetalleService service;
    public VentaDetalleController(VentaDetalleService service){
        this.service = service;
    }
    @PostMapping
    public VentaDetalleResponseDTO crear(@Valid @RequestBody VentaDetalleRequestDTO dto){
        VentaDetalle ventaDetalle = VentaDetalleMapper.toEntity(dto);

        VentaDetalle guardar= service.agregarProducto(ventaDetalle);

        return VentaDetalleMapper.toDTO(guardar);
    }
    @GetMapping("/venta/{ventaId}")
    public List<VentaDetalle> listarDetalles(@PathVariable Integer ventaId){
        return service.listarPorVenta(ventaId);
    }
    @PutMapping("/{id}")
    public VentaDetalle actualizar(@PathVariable Integer id, @RequestBody VentaDetalle detalle){
        detalle.setId(id);
        return service.agregarProducto(detalle);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
    
    
}
