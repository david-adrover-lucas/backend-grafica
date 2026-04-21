package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;

public class VentaDetalleInsumoExtraResponseDTO {
    private Integer id;
    private Integer ventaDetalleId;
    private Integer insumoId;
    private Integer cantidad;
    private BigDecimal costoUnitario;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
