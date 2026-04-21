package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.PagoRequestDTO;
import com.drover.demo.backend.dto.response.PagoResponseDTO;
import com.drover.demo.backend.entity.Pago;

public class PagoMapper {
    public static  Pago toEntity( PagoRequestDTO dto){
        Pago p = new Pago();
        p.setCuentaId(dto.getCuentaId());
        p.setVentaId(dto.getVentaId());
        p.setMonto(dto.getMonto());
        p.setTipoPago(dto.getTipoPago());
        return p;      

    }   
    public static PagoResponseDTO toDTO(Pago p){
        PagoResponseDTO dto = new PagoResponseDTO();
        dto.setCreatedAt(p.getCreatedAt());
        dto.setCuentaId(p.getCuentaId());
        dto.setId(p.getId());
        dto.setMonto(p.getMonto());
        dto.setTipoPago(p.getTipoPago());
        dto.setVentaId(p.getVentaId());
        return dto;
    }

}
