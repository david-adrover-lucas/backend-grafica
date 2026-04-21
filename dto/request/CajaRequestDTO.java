package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CajaRequestDTO {
    @NotNull(message = "Cuenata obligatorio")
    private Integer cuentaId;
    private BigDecimal saldo;
    @NotNull(message = "monto obligatorio")
    @Positive(message = "El valor debe ser mayor a 0")    
    private BigDecimal monto;

    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public BigDecimal getSaldo() {
        return saldo;
    }
    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }
    public Integer getCuentaId() { return cuentaId; }
    public void setCuentaId(Integer cuentaId) { this.cuentaId = cuentaId; }

}