package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.ProductoInsumoRequestDTO;
import com.drover.demo.backend.dto.response.ProductoInsumoResponseDTO;
import com.drover.demo.backend.entity.ProductoInsumo;

public class ProductoInsumoMapper {
    public static ProductoInsumo toEntity(ProductoInsumoRequestDTO dto){
        ProductoInsumo pi = new ProductoInsumo();
        pi.setProductoId(dto.getProductoId()); // ✅ faltaba esto
        pi.setInsumoId(dto.getInsumoId());     // ✅ faltaba esto
        pi.setCantidad(dto.getCantidad());
        return pi;        
    }

    public static ProductoInsumoResponseDTO toDTO(ProductoInsumo pi){
        ProductoInsumoResponseDTO dto = new ProductoInsumoResponseDTO();
        dto.setId(pi.getId());
        dto.setCantidad(pi.getCantidad());
        dto.setInsumoId(pi.getInsumoId());
        dto.setProductoId(pi.getProductoId());
        return dto;
    }
}