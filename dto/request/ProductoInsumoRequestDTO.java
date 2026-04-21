package com.drover.demo.backend.dto.request;



import jakarta.validation.constraints.NotNull;

public class ProductoInsumoRequestDTO {
    @NotNull(message ="Producto obligatorio")
    private Integer productoId;

    @NotNull(message ="Insumo obligatorio")
    private Integer insumoId;

    @NotNull(message ="La cantidad debe ser mayor a 0")
    private Integer cantidad;

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
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
}
