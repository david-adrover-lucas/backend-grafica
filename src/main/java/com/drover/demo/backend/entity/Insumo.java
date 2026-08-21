package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity 
@Table(name = "insumos")
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name="id")   
    private Long id;
    @Column(name = "proveedor_id")
    private Long proveedor_id;
    @Column(name="nombre",nullable = false, length = 150)
    private String nombre;
    @Column(name="unidad", nullable = false,length = 20)
    private String unidad;
    @Column(name = "costo_unitario", nullable = false, length = 15)
    private BigDecimal costo_unitario;
    @Column(name = "Stock_actual", nullable = false, length = 15)
    private BigDecimal stock_actual;
    @Column(name="stock_minimo", nullable = false,length = 15)
    private BigDecimal stock_minimo;
    @Column(name = "activo",nullable = false)
    private Boolean activo;

    public Insumo() {
    }
    
    public Insumo(Long id, Long proveedor_id, String nombre, String unidad, BigDecimal costo_unitario,
            BigDecimal stock_actual, BigDecimal stock_minimo, Boolean activo) {
        this.id = id;
        this.proveedor_id = proveedor_id;
        this.nombre = nombre;
        this.unidad = unidad;
        this.costo_unitario = costo_unitario;
        this.stock_actual = stock_actual;
        this.stock_minimo = stock_minimo;
        this.activo = activo;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProveedor_id() {
        return proveedor_id;
    }
    public void setProveedor_id(Long proveedor_id) {
        this.proveedor_id = proveedor_id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUnidad() {
        return unidad;
    }
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    public BigDecimal getCosto_unitario() {
        return costo_unitario;
    }
    public void setCosto_unitario(BigDecimal costo_unitario) {
        this.costo_unitario = costo_unitario;
    }
    public BigDecimal getStock_actual() {
        return stock_actual;
    }
    public void setStock_actual(BigDecimal stock_actual) {
        this.stock_actual = stock_actual;
    }
    public BigDecimal getStock_minimo() {
        return stock_minimo;
    }
    public void setStock_minimo(BigDecimal stock_minimo) {
        this.stock_minimo = stock_minimo;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }


}
