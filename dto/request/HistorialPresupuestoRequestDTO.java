package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;



public class HistorialPresupuestoRequestDTO {
    @NotNull(message = "presupuesto obligatorio")
    private Integer presupuestoId;
    @NotNull(message = "cliente obligatorio")
    private Integer clienteId;
    @NotNull(message = "total obligatorio")
    @Positive(message = "el numero debe ser mayor a 0")   
    private BigDecimal total;
    @NotNull(message = "estado obligatorio")
    private String estado;
    @NotNull(message = "fecha obligatorio")
    private LocalDateTime fecha;
    

    public Integer getPresupuestoId() {
        return presupuestoId;
    }

    public void setPresupuestoId(Integer presupuestoId) {
        this.presupuestoId = presupuestoId;
    }

  
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
        public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
    
}
