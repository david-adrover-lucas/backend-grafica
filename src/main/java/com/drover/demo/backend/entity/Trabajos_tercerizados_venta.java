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
@Table(name = "trabajos_tercerizados_venta")
public class Trabajos_tercerizados_venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "venta_id")
    private Long venta_id;
    @Column(name = "detalle_venta_id")
    private Long detalle_venta_id;
    @Column(name = "trabajo_tercerizado_id")
    private Long trabajo_tercerizado_id;
    @Column(name ="proveedor_id" )
    private Long  proveedor_id;
    @Column(name ="cantidad",nullable = false,length = 15 )    
    private BigDecimal cantidad;
    @Column(name ="precio_unitario_historico",nullable = false,length = 15 )    
    private BigDecimal precio_unitario_historico;
    @Column(name ="costo_total_historico", nullable = false, length = 15)    
    private BigDecimal costo_total_historico;
    @Column(name ="estado",nullable = false,length = 20 )    
    private String estado;
    @Column(name ="fecha_envio" )    
    private LocalDateTime fecha_envio;
    @Column(name ="fecha_finalizacion" )    
    private LocalDateTime fecha_finalizacion;
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
    public Long getDetalle_venta_id() {
        return detalle_venta_id;
    }
    public void setDetalle_venta_id(Long detalle_venta_id) {
        this.detalle_venta_id = detalle_venta_id;
    }
    public Long getTrabajo_tecerizado_id() {
        return trabajo_tercerizado_id;
    }
    public void setTrabajo_tecerizado_id(Long trabajo_tecerizado_id) {
        this.trabajo_tercerizado_id = trabajo_tecerizado_id;
    }
    public Long getProveedor_id() {
        return proveedor_id;
    }
    public void setProveedor_id(Long proveedor_id) {
        this.proveedor_id = proveedor_id;
    }
    public BigDecimal getCantidad() {
        return cantidad;
    }
    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }
    public BigDecimal getPrecio_unitario_historico() {
        return precio_unitario_historico;
    }
    public void setPrecio_unitario_historico(BigDecimal precio_unitario_historico) {
        this.precio_unitario_historico = precio_unitario_historico;
    }
    public BigDecimal getCosto_total_historico() {
        return costo_total_historico;
    }
    public void setCosto_total_historico(BigDecimal costo_total_historico) {
        this.costo_total_historico = costo_total_historico;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public LocalDateTime getFecha_envio() {
        return fecha_envio;
    }
    public void setFecha_envio(LocalDateTime fecha_envio) {
        this.fecha_envio = fecha_envio;
    }
    public LocalDateTime getFecha_finalizacion() {
        return fecha_finalizacion;
    }
    public void setFecha_finalizacion(LocalDateTime fecha_finalizacion) {
        this.fecha_finalizacion = fecha_finalizacion;
    }
    public Trabajos_tercerizados_venta(Long id, Long venta_id, Long detalle_venta_id, Long trabajo_tecerizado_id,
            Long proveedor_id, BigDecimal cantidad, BigDecimal precio_unitario_historico,
            BigDecimal costo_total_historico, String estado, LocalDateTime fecha_envio,
            LocalDateTime fecha_finalizacion) {
        this.id = id;
        this.venta_id = venta_id;
        this.detalle_venta_id = detalle_venta_id;
        this.trabajo_tercerizado_id = trabajo_tecerizado_id;
        this.proveedor_id = proveedor_id;
        this.cantidad = cantidad;
        this.precio_unitario_historico = precio_unitario_historico;
        this.costo_total_historico = costo_total_historico;
        this.estado = estado;
        this.fecha_envio = fecha_envio;
        this.fecha_finalizacion = fecha_finalizacion;
    }
    public Trabajos_tercerizados_venta() {
    }
}
