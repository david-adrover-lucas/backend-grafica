package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.CuentaRequestDTO;
import com.drover.demo.backend.dto.response.CuentaResponseDTO;
import com.drover.demo.backend.entity.Cuenta;

public class CuentaMapper {
    public static Cuenta toEntity(CuentaRequestDTO dto){
        Cuenta c = new Cuenta();
        c.setNombre(dto.getNombre());
        return c;
    }
    public static CuentaResponseDTO toDTO(Cuenta c){
        CuentaResponseDTO dto = new CuentaResponseDTO();
        dto.setActiva(c.getActiva());
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        return dto;
    }

}
