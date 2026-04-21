package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.PresupuestoDetalleRequestDTO;
import com.drover.demo.backend.dto.response.PresupuestoDetalleResponseDTO;
import com.drover.demo.backend.entity.PresupuestoDetalle;


public class PresupuestoDetalleMapper {
    public static PresupuestoDetalle toEntity(PresupuestoDetalleRequestDTO dto){
       PresupuestoDetalle pd = new PresupuestoDetalle();
       pd.setPresupuestoId(dto.getPresupuestoId());
       pd.setProductoId(dto.getProductoId());
       pd.setCantidad(dto.getCantidad());
       pd.setAlto(dto.getAlto());
       pd.setAncho(dto.getAncho());
       return pd;

    }
    public static PresupuestoDetalleResponseDTO toDTO(PresupuestoDetalle pd){
        PresupuestoDetalleResponseDTO dto = new PresupuestoDetalleResponseDTO();
        dto.setId(pd.getId());
        dto.setAlto(pd.getAlto());
        dto.setAncho(pd.getAncho());
        dto.setCantidad(pd.getCantidad());
        dto.setPrecio(pd.getPrecio());
        dto.setPresupuestoId(pd.getPresupuestoId());
        dto.setProductoId(pd.getProductoId());
        dto.setCosto(pd.getCosto());
        return dto;

   } 
   
    
}
