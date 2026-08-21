package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_ventas")
public class Detalle_venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "venta_id")
    private Long venta_id;
    @Column(name = "producto_id")
    private Long producto_id;
    @Column(name = "cantidad", nullable = false, length = 15)
    private BigDecimal cantidad;
    @Column(name = "ancho",  length = 15)
    private BigDecimal ancho;
    @Column(name = "alto",  length = 15)
    private BigDecimal alto;
    @Column(name = "precio_unitario_historico", nullable = false, length = 15)
    private BigDecimal precio_unitario_historico;
    @Column(name = "costo_historico", nullable = false, length = 15)
    private BigDecimal costo_historico;
    @Column(name = "monto_ganancia_historico", nullable = false, length = 15)
    private BigDecimal monto_ganancia_historico;
    @Column(name = "monto_comision_historico", nullable = false, length = 15)
    private BigDecimal monto_comision_historico;
    @Column(name = "subtotal", nullable = false, length = 15)
    private BigDecimal subtotal;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getVenta_id() {
        return venta_id;
    }
    public void setVenta_id(Long venta_id) {
        this.venta_id = venta_id;
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
    public BigDecimal getPrecio_unitario_historico() {
        return precio_unitario_historico;
    }
    public void setPrecio_unitario_historico(BigDecimal precio_unitario_historico) {
        this.precio_unitario_historico = precio_unitario_historico;
    }
    public BigDecimal getCosto_historico() {
        return costo_historico;
    }
    public void setCosto_historico(BigDecimal costo_historico) {
        this.costo_historico = costo_historico;
    }
    public BigDecimal getMonto_ganancia_historico() {
        return monto_ganancia_historico;
    }
    public void setMonto_ganancia_historico(BigDecimal monto_ganancia_historico) {
        this.monto_ganancia_historico = monto_ganancia_historico;
    }
    public BigDecimal getMonto_comision_ganancia_historico() {
        return monto_comision_historico;
    }
    public void setMonto_comision_ganancia_historico(BigDecimal monto_comision_ganancia_historico) {
        this.monto_comision_historico = monto_comision_ganancia_historico;
    }
    public BigDecimal getMonto_comicion_historico() {
        return monto_comision_historico;
    }
    public void setMonto_comicion_historico(BigDecimal monto_comicion_historico) {
        this.monto_comision_historico = monto_comicion_historico;
    }
    public BigDecimal getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }
    public Detalle_venta(Long id, Long venta_id, Long producto_id, BigDecimal cantidad, BigDecimal ancho,
            BigDecimal alto, BigDecimal precio_unitario_historico, BigDecimal costo_historico,
            BigDecimal monto_ganancia_historico, BigDecimal monto_comision_historico, BigDecimal subtotal) {
        this.id = id;
        this.venta_id = venta_id;
        this.producto_id = producto_id;
        this.cantidad = cantidad;
        this.ancho = ancho;
        this.alto = alto;
        this.precio_unitario_historico = precio_unitario_historico;
        this.costo_historico = costo_historico;
        this.monto_ganancia_historico = monto_ganancia_historico;
        this.monto_comision_historico = monto_comision_historico;
        this.subtotal = subtotal;
    }
    public Detalle_venta() {
    }
}
