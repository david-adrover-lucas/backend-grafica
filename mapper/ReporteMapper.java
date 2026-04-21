package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.ReporteRequestDTO;
import com.drover.demo.backend.dto.response.ReporteResponseDTO;
import com.drover.demo.backend.entity.Reporte;

public class ReporteMapper {
    public static Reporte toEntity(ReporteRequestDTO dto){
        Reporte r =new Reporte();
        r.setDescripcion(dto.getDescripcion());
        r.setTipo(dto.getTipo());
        r.setValor(dto.getValor());        
        return r;
    }
    public static ReporteResponseDTO toDTO(Reporte r){
        ReporteResponseDTO dto = new ReporteResponseDTO();
        dto.setCreatedAt(r.getCreatedAt());
        dto.setDescripcion(r.getDescripcion());
        dto.setTipo(r.getTipo());
        dto.setValor(r.getValor());
        return dto;
    }
}
