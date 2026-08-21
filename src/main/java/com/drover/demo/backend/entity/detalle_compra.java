package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_compras")
public class detalle_compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name="id")
    private Long id;
    @Column( name="compra_id")   
    private Long compra_id;
    @Column( name="insumo_id")       
    private Long insumo_id;
    @Column( name="cantidad",nullable = false,length = 15)     
    private BigDecimal  cantidad;
    @Column( name="precio_unitario",nullable = false,length = 15)   
    private BigDecimal precio_unitario;
    @Column( name="subtotal",nullable = false,length = 15)    
    private BigDecimal subtotal;
    
    public detalle_compra() {
    }
    public detalle_compra(Long id, Long compra_id, Long insumo_id, BigDecimal cantidad, BigDecimal precio_unitario,
            BigDecimal subtotal) {
        this.id = id;
        this.compra_id = compra_id;
        this.insumo_id = insumo_id;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getCompra_id() {
        return compra_id;
    }
    public void setCompra_id(Long compra_id) {
        this.compra_id = compra_id;
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
    public BigDecimal getPrecio_unitario() {
        return precio_unitario;
    }
    public void setPrecio_unitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
}
