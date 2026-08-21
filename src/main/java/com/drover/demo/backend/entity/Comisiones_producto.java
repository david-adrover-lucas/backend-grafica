package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "comisiones_productos")
public class Comisiones_producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")   
    private Long id;
    @Column(name="producto_id")       
    private Long producto_id;
    @Column(name="monto_comision",nullable = false, length = 15)
    private BigDecimal monto_comision;
    @Column(name="activo",nullable = false)       
    private Boolean activo;
    public Long getId() {
        return id;
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
    public BigDecimal getMonto_comison() {
        return monto_comision;
    }
    public void setMonto_comison(BigDecimal monto_comison) {
        this.monto_comision = monto_comison;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
