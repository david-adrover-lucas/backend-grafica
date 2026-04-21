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

import com.drover.demo.backend.dto.request.VentaDetalleInsumoExtraRequestDTO;
import com.drover.demo.backend.dto.response.VentaDetalleInsumoExtraResponseDTO;
import com.drover.demo.backend.entity.VentaDetalleInsumoExtra;
import com.drover.demo.backend.mapper.VentaDetalleInsumoExtraMapper;
import com.drover.demo.backend.service.VentaDetalleInsumoExtraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/venta-detalle-insumo-extra")
public class VentaDetalleInsumoExtraController {
    private final VentaDetalleInsumoExtraService service;
    public VentaDetalleInsumoExtraController(VentaDetalleInsumoExtraService service){
        this.service = service;
    }
    @PostMapping
    public VentaDetalleInsumoExtraResponseDTO agregar(@Valid @RequestBody VentaDetalleInsumoExtraRequestDTO dto){
        VentaDetalleInsumoExtra ventaDetalleInsumoExtra = VentaDetalleInsumoExtraMapper.toEntity(dto);
        VentaDetalleInsumoExtra agregar = service.agregarExtra(ventaDetalleInsumoExtra);
        return VentaDetalleInsumoExtraMapper.toDTO(agregar);
    }
    @GetMapping
    public List<VentaDetalleInsumoExtra> listarPorDetalle(@RequestParam Integer detalleId){
        return service.listarPorDetalle(detalleId);
    }
    @PutMapping("/{id}")
    public VentaDetalleInsumoExtra actualizar(@PathVariable Integer id, @RequestBody VentaDetalleInsumoExtra detalle){
        detalle.setId(id);
        return service.agregarExtra(detalle);
    }
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        service.eliminar(id);
    }
}