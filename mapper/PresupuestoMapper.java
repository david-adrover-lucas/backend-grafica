package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.PresupuestoRequestDTO;
import com.drover.demo.backend.dto.response.PresupuestoResponseDTO;
import com.drover.demo.backend.entity.Presupuesto;

public class PresupuestoMapper {
    public static Presupuesto toEntity( PresupuestoRequestDTO dto){
        Presupuesto  p = new Presupuesto();
        p.setClienteId(dto.getClienteId());
        p.setTotal(dto.getTotal());
        return p;

    }
    public static PresupuestoResponseDTO toDTO(Presupuesto p){
        PresupuestoResponseDTO dto = new PresupuestoResponseDTO();
        dto.setClienteId(p.getClienteId());
        dto.setId(p.getId());
        dto.setTotal(p.getTotal());
        dto.setCreatedAt(p.getCreatedAt());
        dto.setCodigo(p.getCodigo());
        return dto;
        }
    
}
