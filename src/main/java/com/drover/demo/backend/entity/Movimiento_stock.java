package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="movimientos_stock")
public class Movimiento_stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "insumo_id")
    private Long insumo_id;
    @Column(name = "compra_id")
    private Long compra_id;
    @Column(name = "venta_id")
    private Long venta_id;
    @Column(name = "detalle_venta_id")
    private Long detalle_venta_id;
    @Column(name = "tipo", nullable = false,length = 30)
    private String tipo;
    @Column(name = "cantidad", nullable = false,length = 15)
    private BigDecimal cantidad;
    @Column(name = "", nullable = false,length = 15)    
    private BigDecimal costo_unitario;
    @Column(name = "costo_total", nullable = false,length = 15)    
    private BigDecimal costo_total;
    @Column(name = "stock_anterior", nullable = false,length = 15)    
    private  BigDecimal stock_anterior;
    @Column(name = "stock_posterior", nullable = false,length = 15)    
    private BigDecimal stock_posterior;
    @Column(name = "fecha", nullable = false)    
    private LocalDateTime fecha;
    @Column(name = "observaciones")    
    private String observaciones;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getInsumo_id() {
        return insumo_id;
    }
    public void setInsumo_id(Long insumo_id) {
        this.insumo_id = insumo_id;
    }
    public Long getCompra_id() {
        return compra_id;
    }
    public void setCompra_id(Long compra_id) {
        this.compra_id = compra_id;
    }
    public Long getVenta_id() {
        return venta_id;
    }
    public void setVenta_id(Long venta_id) {
        this.venta_id = venta_id;
    }
    public Long getDetalle_venta_id() {
        return detalle_venta_id;
    }
    public void setDetalle_venta_id(Long detalle_venta_id) {
        this.detalle_venta_id = detalle_venta_id;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getCosto_unitario() {
        return costo_unitario;
    }
    public void setCosto_unitario(BigDecimal costo_unitario) {
        this.costo_unitario = costo_unitario;
    }
    public BigDecimal getCosto_total() {
        return costo_total;
    }
    public void setCosto_total(BigDecimal costo_total) {
        this.costo_total = costo_total;
    }
    public BigDecimal getStock_anterior() {
        return stock_anterior;
    }
    public void setStock_anterior(BigDecimal stock_anterior) {
        this.stock_anterior = stock_anterior;
    }
    public BigDecimal getStock_posterior() {
        return stock_posterior;
    }
    public void setStock_posterior(BigDecimal stock_posterior) {
        this.stock_posterior = stock_posterior;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public Movimiento_stock(Long id, Long insumo_id, Long compra_id, Long venta_id, Long detalle_venta_id, String tipo,
            BigDecimal cantidad, BigDecimal costo_unitario, BigDecimal costo_total, BigDecimal stock_anterior,
            BigDecimal stock_posterior, LocalDateTime fecha, String observaciones) {
        this.id = id;
        this.insumo_id = insumo_id;
        this.compra_id = compra_id;
        this.venta_id = venta_id;
        this.detalle_venta_id = detalle_venta_id;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.costo_unitario = costo_unitario;
        this.costo_total = costo_total;
        this.stock_anterior = stock_anterior;
        this.stock_posterior = stock_posterior;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }
    public Movimiento_stock() {
    }
}
