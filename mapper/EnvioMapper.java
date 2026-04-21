package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.EnvioRequestDTO;
import com.drover.demo.backend.dto.response.EnvioResponseDTO;
import com.drover.demo.backend.entity.Envio;

public class EnvioMapper {
    public static Envio toEntity(EnvioRequestDTO dto){
        Envio e = new Envio();
        e.setVentaId(dto.getVentaId());
        e.setEstado(dto.getEstado());
        e.setTipo(dto.getTipo());
        e.setFechaEntrega(dto.getFechaEntrega());
        return e;
    }
    public static EnvioResponseDTO toDTO(Envio e){
     
        EnvioResponseDTO dto = new EnvioResponseDTO();
        dto.setCreatedAt(e.getCreatedAt());
        dto.setEstado(e.getEstado());
        dto.setFechaEntrega(e.getFechaEntrega());
        dto.setId(e.getId());
        dto.setTipo(e.getTipo());
        dto.setVenta(e.getVentaId());
        return dto;
    }
    
}
