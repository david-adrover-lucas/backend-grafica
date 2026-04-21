package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.drover.demo.backend.entity.Pago.TipoPago;



public class PagoResponseDTO {
    private Integer id;
    private Integer ventaId;
    private Integer cuentaId;
    private BigDecimal monto;
    private TipoPago tipoPago;
    private LocalDateTime createdAt;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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

    
}
