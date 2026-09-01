package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_presupuestos")
public class DetallePresupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "presupuesto_id")
    private Long presupuesto_id;
    @Column(name = "producto_id")    
    private Long producto_id;
    @Column(name = "cantidad",nullable = false, length = 15)
    private BigDecimal cantidad;
    @Column(name = "ancho", length = 15)
    private BigDecimal ancho;
    @Column(name = "alto", length = 15) 
    private BigDecimal alto;
    @Column(name = "costo_unitario_historico",nullable = false, length = 15) 
    private BigDecimal costo_unitario_historico;
    @Column(name = "monto_ganancia_unitario",nullable = false, length = 15) 
    private BigDecimal monto_ganancia_unitario;
    @Column(name = "precio_unitario",nullable = false, length = 15) 
    private BigDecimal precio_unitario;
    @Column(name = "subtotal",nullable = false, length = 15) 
    private BigDecimal subtotal;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPresupuesto_id() {
        return presupuesto_id;
    }
    public void setPresupuesto_id(Long presupuesto_id) {
        this.presupuesto_id = presupuesto_id;
    }
    public Long getProducto_id() {
        return producto_id;
    }
    public void setProducto_id(Long producto_id) {
        this.producto_id = producto_id;
    }
    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getAncho() {
        return ancho;
    }
    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }
    public BigDecimal getAlto() {
        return alto;
    }
    public void setAlto(BigDecimal alto) {
        this.alto = alto;
    }
    public BigDecimal getCosto_unitario_historico() {
        return costo_unitario_historico;
    }
    public void setCosto_unitario_historico(BigDecimal costo_unitario_historico) {
        this.costo_unitario_historico = costo_unitario_historico;
    }
    public BigDecimal getMonto_ganancia_unitario() {
        return monto_ganancia_unitario;
    }
    public void setMonto_ganancia_unitario(BigDecimal monto_ganancia_unitario) {
        this.monto_ganancia_unitario = monto_ganancia_unitario;
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
    public DetallePresupuesto(Long id, Long presupuesto_id, Long producto_id, BigDecimal cantidad, BigDecimal ancho,
            BigDecimal alto, BigDecimal costo_unitario_historico, BigDecimal monto_ganancia_unitario,
            BigDecimal precio_unitario, BigDecimal subtotal) {
        this.id = id;
        this.presupuesto_id = presupuesto_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.ancho = ancho;
        this.alto = alto;
        this.costo_unitario_historico = costo_unitario_historico;
        this.monto_ganancia_unitario = monto_ganancia_unitario;
        this.precio_unitario = precio_unitario;
        this.subtotal = subtotal;
    }
    public DetallePresupuesto() {
    } 
}
