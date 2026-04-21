package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.ComisionRequestDTO;
import com.drover.demo.backend.dto.response.ComisionResponseDTO;
import com.drover.demo.backend.entity.Comision;

public class ComisionMapper {
    public static Comision toEntity(ComisionRequestDTO dto) {
        Comision c = new Comision();
        c.setUsuarioId(dto.getUsuarioId());
        c.setProductoId(dto.getProductoId());
        c.setMontoFijo(dto.getMontoFijo());
        c.setPorcentaje(dto.getPorcentaje());
        return c;
    }

    public static ComisionResponseDTO toDTO(Comision c) {
        ComisionResponseDTO dto = new ComisionResponseDTO();
        dto.setId(c.getId());
        dto.setUsuarioId(c.getUsuarioId());
        dto.setProductoId(c.getProductoId());
        dto.setMontoFijo(c.getMontoFijo());
        dto.setPorcentaje(c.getPorcentaje());
        return dto;
    }
}