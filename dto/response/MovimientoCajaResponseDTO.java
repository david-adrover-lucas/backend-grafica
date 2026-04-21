package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.drover.demo.backend.entity.MovimientoCaja.TipoMovimiento;


public class MovimientoCajaResponseDTO {
    private Integer id;
    private Integer cuentaId;    
    private TipoMovimiento tipo;    
    private BigDecimal monto;    
    private String descripcion;
    private Integer ventaId;    
    private LocalDateTime createdAt;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getCuentaId() {
        return cuentaId;
    }
    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }
    public TipoMovimiento getTipo() {
        return tipo;
    }
    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public Integer getVentaId() {
        return ventaId;
    }
    public void setVentaId(Integer ventaId) {
        this.ventaId = ventaId;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}