package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;

import com.drover.demo.backend.entity.Producto.TipoProducto;


public class ProductoResponseDTO {
    private Integer id;
    private String nombre;
    private TipoProducto tipo;
    private BigDecimal porcentajeGanancia;
    private Boolean activo;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
}
