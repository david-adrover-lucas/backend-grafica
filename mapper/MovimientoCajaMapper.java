package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.MovimientoCajaRequestDTO;
import com.drover.demo.backend.dto.response.MovimientoCajaResponseDTO;
import com.drover.demo.backend.entity.MovimientoCaja;

public class MovimientoCajaMapper {
    public static MovimientoCaja toEntity(MovimientoCajaRequestDTO dto){
        MovimientoCaja mc = new MovimientoCaja();
        mc.setCuentaId(dto.getCuentaId());
        mc.setMonto(dto.getMonto());
        mc.setTipo(dto.getTipo());
        mc.setDescripcion(dto.getDescripcion());
        return mc;
    }
    public static MovimientoCajaResponseDTO toDTO(MovimientoCaja mc){
        MovimientoCajaResponseDTO dto = new MovimientoCajaResponseDTO();
        dto.setCreatedAt(mc.getCreatedAt());
        dto.setCuentaId(mc.getCuentaId()); // ✅ era mc.getId(), estaba mal
        dto.setDescripcion(mc.getDescripcion());
        dto.setId(mc.getId());
        dto.setMonto(mc.getMonto());
        dto.setTipo(mc.getTipo());
        dto.setVentaId(mc.getVentaId());
        return dto;
    }
}