package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import com.drover.demo.backend.entity.MovimientoCaja.TipoMovimiento;



public class MovimientoCajaRequestDTO {

    private Integer cuentaId;       
    public Integer getCuentaId() {
        return cuentaId;
    }
    public void setCuentaId(Integer cuentaId) {
        this.cuentaId = cuentaId;
    }
    private TipoMovimiento tipo;    
 
    private BigDecimal monto;    

    private String descripcion;

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
  
}
