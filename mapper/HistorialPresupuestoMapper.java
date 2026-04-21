package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.HistorialPresupuestoRequestDTO;
import com.drover.demo.backend.dto.response.HistorialPresupuestoResponseDTO;
import com.drover.demo.backend.entity.HistorialPresupuesto;

public class HistorialPresupuestoMapper {
    public static HistorialPresupuesto toEntity(HistorialPresupuestoRequestDTO dto){
        HistorialPresupuesto hp = new HistorialPresupuesto();
        hp.setPresupuestoId(dto.getPresupuestoId());
        hp.setClienteId(dto.getClienteId());
        hp.setTotal(dto.getTotal());
        hp.setEstado(dto.getEstado());
        hp.setFecha(dto.getFecha());
        return hp;
    }
    public static HistorialPresupuestoResponseDTO toDTO(HistorialPresupuesto hp){
        HistorialPresupuestoResponseDTO dto = new HistorialPresupuestoResponseDTO();
        dto.setClienteId(hp.getClienteId());
        dto.setCreatedAt(hp.getCreatedAt());
        dto.setEstado(hp.getEstado());
        dto.setFecha(hp.getFecha());
        dto.setId(hp.getId());
        dto.setPresupuestoId(hp.getPresupuestoId());
        dto.setTotal(hp.getTotal());
        return dto;
    }
    
    
}
