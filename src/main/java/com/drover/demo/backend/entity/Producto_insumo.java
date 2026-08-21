package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="producto_insumos")
public class Producto_insumo {
    public Producto_insumo() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "producto_id")
    private Long producto_id;
    @Column(name="insumo_id")
    private Long  insumo_id;
    @Column(name="cantidad")
    private BigDecimal cantidad;
    public Long getId() {
        return id;
    }
    public Producto_insumo(Long id, Long producto_id, Long insumo_id, BigDecimal cantidad) {
        this.id = id;
        this.producto_id = producto_id;
        this.insumo_id = insumo_id;
        this.cantidad = cantidad;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProducto_id() {
        return producto_id;
    }
    public void setProducto_id(Long producto_id) {
        this.producto_id = producto_id;
    }
    public Long getInsumo_id() {
        return insumo_id;
    }
    public void setInsumo_id(Long insumo_id) {
        this.insumo_id = insumo_id;
    }
    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

}
