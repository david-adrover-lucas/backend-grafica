package com.drover.demo.backend.dto.request;

import java.time.LocalDateTime;

import com.drover.demo.backend.entity.Envio.EstadoEnvio;
import com.drover.demo.backend.entity.Envio.TipoEnvio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class EnvioRequestDTO {
    @NotNull(message = "estado del envio obligatorio")
    private EstadoEnvio estado;
    @NotNull(message = "tipo de envio obligatorio")
    private TipoEnvio tipo;
    @NotNull(message = "fecha de entrega obligatorio")
    private LocalDateTime fechaEntrega;
    @NotNull (message = "venta obligatorio")  
    @Positive(message = " el numero debe ser mayor a 0") 
    private Integer ventaId;

    public Integer getVentaId() {
        return ventaId;
    }
    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
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

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }    
    
}
