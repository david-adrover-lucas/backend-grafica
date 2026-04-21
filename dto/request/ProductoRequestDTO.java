package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import com.drover.demo.backend.entity.Producto.TipoProducto;

import jakarta.validation.constraints.NotNull;

public class ProductoRequestDTO {
    @NotNull(message = "Nombre obligatorio")
    private String nombre;
    @NotNull (message = " Tipo  es obligatorio")    
    private TipoProducto tipo;
    @NotNull (message = "El porcentaje debe ser mayor a 0")  
    private BigDecimal porcentajeGanancia;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public TipoProducto getTipo() {
        return tipo;
    }
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }
    public BigDecimal getPorcentajeGanancia() {
        return porcentajeGanancia;
    }
    public void setPorcentajeGanancia(BigDecimal porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }
}
