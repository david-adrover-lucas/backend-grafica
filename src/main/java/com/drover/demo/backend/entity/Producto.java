package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "unidad_venta", nullable = false, length = 20)
    private String unidad_venta;
    @Column(name = "costo_actual", nullable = false, length = 15)
    private BigDecimal costo_actual;
    @Column(name = "monto_ganancia", nullable = false, length = 15)
    private BigDecimal monto_ganancia;
    @Column(name="precio_venta",nullable = false,length = 15)
    private BigDecimal precio_venta;
    @Column(name="activo",nullable = false) 
    private Boolean activo;
    public Producto() {
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getUnidad_venta() {
        return unidad_venta;
    }
    public void setUnidad_venta(String unidad_venta) {
        this.unidad_venta = unidad_venta;
    }
    public BigDecimal getCosto_actual() {
        return costo_actual;
    }
    public void setCosto_actual(BigDecimal costo_actual) {
        this.costo_actual = costo_actual;
    }
    public BigDecimal getMonto_ganancia() {
        return monto_ganancia;
    }
    public void setMonto_ganancia(BigDecimal monto_ganancia) {
        this.monto_ganancia = monto_ganancia;
    }
    public BigDecimal getPrecio_venta() {
        return precio_venta;
    }
    public void setPrecio_venta(BigDecimal precio_venta) {
        this.precio_venta = precio_venta;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public Producto(Long id, String nombre, String descripcion, String unidad_venta, BigDecimal costo_actual,
            BigDecimal monto_ganancia, BigDecimal precio_venta, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidad_venta = unidad_venta;
        this.costo_actual = costo_actual;
        this.monto_ganancia = monto_ganancia;
        this.precio_venta = precio_venta;
        this.activo = activo;
    }

}
    
