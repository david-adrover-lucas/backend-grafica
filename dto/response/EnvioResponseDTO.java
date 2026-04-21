package com.drover.demo.backend.dto.response;

import java.time.LocalDateTime;

import com.drover.demo.backend.entity.Envio.EstadoEnvio;
import com.drover.demo.backend.entity.Envio.TipoEnvio;


public class EnvioResponseDTO {

    private Integer id;

    private Integer ventaId;

    private EstadoEnvio estado;

    private TipoEnvio tipo;

    private LocalDateTime createdAt;

    private LocalDateTime fechaEntrega;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVenta() {
        return ventaId;
    }

    public void setVenta(Integer venta) {
        this.ventaId = venta;
    }

    public EstadoEnvio getEstado() {
        return estado;
    }

    public void setEstado(EstadoEnvio estado) {
        this.estado = estado;
    }

    public TipoEnvio getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnvio tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    
}
