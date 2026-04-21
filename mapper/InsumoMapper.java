package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.InsumoRequestDTO;
import com.drover.demo.backend.dto.response.InsumoResponseDTO;
import com.drover.demo.backend.entity.Insumo;

public class InsumoMapper {
    public static Insumo toEntity(InsumoRequestDTO dto){
        Insumo i = new Insumo();
        i.setNombre(dto.getNombre());
        i.setTipo(dto.getTipo());
        i.setCostoUnitario(dto.getCostoUnitario());
        return i;

    }
    public static InsumoResponseDTO toDTO( Insumo i){
        InsumoResponseDTO dto = new InsumoResponseDTO();
        dto.setActivo(i.getActivo());
        dto.setCostoUnitario(i.getCostoUnitario());
        dto.setEmpresaId(i.getEmpresaId());
        dto.setId(i.getId());
        dto.setTipo(i.getTipo());
        dto.setNombre(i.getNombre());
        return dto;
    }
}
