package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.drover.demo.backend.entity.Pago.TipoPago;

import jakarta.validation.constraints.NotNull;

public class PagoRequestDTO {
    @NotNull(message =  "venta id es obligatorio") 
    private Integer ventaId;
    @NotNull(message = "cuenta id es obligatorio")
    private Integer cuentaId;    
    @NotNull(message = "monto  es obligatorio")  
    private BigDecimal monto;
    @NotNull(message = "tipo de pago  es obligatorio")   
    private TipoPago tipoPago;
    private LocalDateTime createdAt;
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public TipoPago getTipoPago() {
        return tipoPago;
    }
    public void setTipoPago(TipoPago tipoPago) {
        this.tipoPago = tipoPago;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
        public Integer getVentaId() {
        return ventaId;
    }
    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }
    public Integer getCuentaId() {
        return cuentaId;
    }
    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }
}
