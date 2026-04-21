package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class VentaDetalleInsumoExtraRequestDTO {
    @NotNull(message = "Detalle obligatorio")
    private Integer ventaDetalleId;
    @NotNull(message = "Insumo obligatorio")
    private Integer insumoId;
    @NotNull(message = "Cantidad obligatorio")
    @Positive(message = "El valor debe ser mayor a 0")
    private Integer cantidad;
    @NotNull
    private BigDecimal costoUnitario;
    
    public Integer getVentaDetalleId() {
        return ventaDetalleId;
    }
    public void setVentaDetalleId(Integer ventaDetalleId) {
        this.ventaDetalleId = ventaDetalleId;
    }
    public Integer getInsumoId() {
        return insumoId;
    }
    public void setInsumoId(Integer insumoId) {
        this.insumoId = insumoId;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }
    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }


}
