package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;

public class VentaDetalleResponseDTO {
    private Integer id;
    private Integer ventaId;
    private Integer productoId;
    private Integer cantidad;
    private BigDecimal alto;
    private BigDecimal ancho;
    private BigDecimal costo;
    private BigDecimal precio;
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
    public Integer getProductoId() {
        return productoId;
    }
    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }
    public Integer getCantidad() {
        return cantidad;
    }
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
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
    public BigDecimal getCosto() {
        return costo;
    }
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

}
