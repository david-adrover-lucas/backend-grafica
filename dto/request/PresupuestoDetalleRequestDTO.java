package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PresupuestoDetalleRequestDTO {
    @NotNull(message = " presupueto obligatorio")    
    @Positive(message = "el numero debe ser mayor a 0")
    private Integer presupuestoId;
    @NotNull(message = " producto obligatorio")  
    @Positive(message = "el numero debe ser mayor a 0")
    private Integer productoId;   
    @NotNull(message = " cantidad obligatorio")        
    @Positive(message = "el numero debe ser mayor a 0")
    private Integer cantidad;
    @NotNull (message = " alto obligatorio")       
    @Positive(message = "el numero debe ser mayor a 0")    
    private BigDecimal alto;
    @Positive(message = "el numero debe ser mayor a 0") 
    @NotNull (message = " ancho obligatorio")       
    private BigDecimal ancho;     
    public BigDecimal getAlto() {
        return alto;
    }
    public void setAlto(BigDecimal alto) {
        this.alto = alto;
    }
    public BigDecimal getAncho() {
        return ancho;
    }
    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public Integer getPresupuestoId() {
        return presupuestoId;
    }
    public void setPresupuestoId(Integer presupuestoId) {
        this.presupuestoId = presupuestoId;
    }
    public Integer getProductoId() {
        return productoId;
    }
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

   
}
