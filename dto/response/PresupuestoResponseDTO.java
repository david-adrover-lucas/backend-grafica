package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;



public class PresupuestoResponseDTO {

    private Integer id;
    private String codigo ;    
    private Integer ClienteId ;
    private BigDecimal total ;      
    private LocalDateTime createdAt;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public Integer getClienteId() {
        return ClienteId;
    }
    public void setClienteId(Integer clienteId) {
        ClienteId = clienteId;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
  
}
