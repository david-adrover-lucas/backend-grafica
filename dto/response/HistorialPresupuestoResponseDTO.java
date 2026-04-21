package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public class HistorialPresupuestoResponseDTO {
    private Integer presupuestoId;
    private Integer id;
    public Integer getPresupuestoId() {
        return presupuestoId;
    }
    public void setPresupuestoId(Integer presupuestoId) {
        this.presupuestoId = presupuestoId;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    private Integer clienteId;
    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
    private BigDecimal total;
    private String estado; 
    private LocalDateTime createdAt;
    private LocalDateTime fecha;

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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
