package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "precios_revendedor")
public class Precio_revendedor {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "revendedor_id")
    private Long revendedor_id;
    @Column(name = "producto_id")
    private Long producto_id;
    @Column(name = "precio",nullable = false, length = 15)
    private BigDecimal precio;
    @Column(name = "activo",nullable = false)
    private Boolean activo;
    public Precio_revendedor(Long id, Long revendedor_id, Long producto_id, BigDecimal precio, Boolean activo) {
        this.id = id;
        this.revendedor_id = revendedor_id;
        this.producto_id = producto_id;
        this.precio = precio;
        this.activo = activo;
    }
    public Precio_revendedor() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRevendedor_id() {
        return revendedor_id;
    }
    public void setRevendedor_id(Long revendedor_id) {
        this.revendedor_id = revendedor_id;
    }
    public Long getProducto_id() {
        return producto_id;
    }
    public void setProducto_id(Long producto_id) {
        this.producto_id = producto_id;
    }
    public BigDecimal getPrecio() {
        return precio;
    }
    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
