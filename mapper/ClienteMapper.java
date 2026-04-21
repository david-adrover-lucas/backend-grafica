package com.drover.demo.backend.mapper;
import com.drover.demo.backend.dto.request.ClienteRequestDTO;
import com.drover.demo.backend.dto.response.ClienteResponseDTO;
import com.drover.demo.backend.entity.Cliente;

public class ClienteMapper {

    public static Cliente toEntity( ClienteRequestDTO dto){
        Cliente  c =new Cliente();
        c.setNombre(dto.getNombre());
        c.setDeuda(dto.getDeuda());
        c.setDireccion(dto.getDireccion());
        c.setEmail(dto.getEmail());
        c.setTelefono(dto.getTelefono());
        return c;
    }
    public static ClienteResponseDTO toDTO(Cliente c){
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setActivo(c.getActivo());
        dto.setCreatedAt(c.getCreatedAt());
        dto.setDeuda(c.getDeuda());
        dto.setDireccion(c.getDireccion());
        dto.setEmail(c.getEmail());
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setTelefono(c.getTelefono());
        return dto;
    }
}