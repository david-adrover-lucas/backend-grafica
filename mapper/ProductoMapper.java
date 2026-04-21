package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.ProductoRequestDTO;
import com.drover.demo.backend.dto.response.ProductoResponseDTO;
import com.drover.demo.backend.entity.Producto;

public class ProductoMapper {
    public static Producto toEntity(ProductoRequestDTO dto){
        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setTipo(dto.getTipo());
        p.setPorcentajeGanancia(dto.getPorcentajeGanancia());
        return p;
    }
    public static ProductoResponseDTO toDTO(Producto p){
        ProductoResponseDTO dto = new ProductoResponseDTO();
        dto.setId(p.getId());
        dto.setNombre(p.getNombre());
        dto.setPorcentajeGanancia(p.getPorcentajeGanancia());
        dto.setActivo(p.getActivo());
        dto.setTipo(p.getTipo());
        return dto;
    }
}
