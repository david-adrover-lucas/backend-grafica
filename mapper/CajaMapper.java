package com.drover.demo.backend.mapper;

import com.drover.demo.backend.dto.request.CajaRequestDTO;
import com.drover.demo.backend.dto.response.CajaResponseDTO;
import com.drover.demo.backend.entity.Caja;

public class CajaMapper {

    // 🔁 DTO → ENTITY
    public static Caja toEntity(CajaRequestDTO dto) {
        Caja caja = new Caja();
        caja.setCuentaId(dto.getCuentaId());
        caja.setSaldo(dto.getMonto()); // ⚠️ se usa solo si creás directamente
        return caja;
    }

    // 🔁 ENTITY → DTO
    public static CajaResponseDTO toDTO(Caja caja) {
        CajaResponseDTO dto = new CajaResponseDTO();
        dto.setCuentaId(caja.getCuentaId());
        dto.setSaldo(caja.getSaldo());
        return dto;
    }
}