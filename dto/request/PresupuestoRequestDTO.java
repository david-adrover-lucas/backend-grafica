package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PresupuestoRequestDTO {
    @NotNull(message = "cliemte id obligatorio")
    private Integer clienteId;
    private String codigo ;    
    @NotNull(message = "cliemte id obligatorio")
    @Positive(message = "el  numero debe ser mayor a 0")
    private BigDecimal total ;      
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
}
