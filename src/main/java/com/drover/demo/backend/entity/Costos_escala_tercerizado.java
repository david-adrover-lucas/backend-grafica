package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "costos_escala_tercerizado")
public class Costos_escala_tercerizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "trabajo_tercerizado_id")
    private Long trabajo_tercerizado_id;
    @Column(name = "cantidad_desde",nullable = false,length = 15)
    private BigDecimal cantidad_desde;
    @Column(name = "cantidad_hasta", length = 15)
    private BigDecimal cantidad_hasta;
    @Column(name = "precio_unitario",nullable = false,length = 15)
    private BigDecimal precio_unitario;
    @Column(name = "activo",nullable = false)
    private Boolean activo;
   
    public Costos_escala_tercerizado() {
    }
   
    public Costos_escala_tercerizado(Long id, Long trabajo_tercerizado_id, BigDecimal cantidad_desde,
            BigDecimal cantidad_hasta, BigDecimal precio_unitario, Boolean activo) {
        this.id = id;
        this.trabajo_tercerizado_id = trabajo_tercerizado_id;
        this.cantidad_desde = cantidad_desde;
        this.cantidad_hasta = cantidad_hasta;
        this.precio_unitario = precio_unitario;
        this.activo = activo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTrabajo_tercerizado_id() {
        return trabajo_tercerizado_id;
    }
    public void setTrabajo_tercerizado_id(Long trabajo_tercerizado_id) {
        this.trabajo_tercerizado_id = trabajo_tercerizado_id;
    }
    public BigDecimal getCantidad_desde() {
        return cantidad_desde;
    }
    public void setCantidad_desde(BigDecimal cantidad_desde) {
        this.cantidad_desde = cantidad_desde;
    }
    public BigDecimal getCantidad_hasta() {
        return cantidad_hasta;
    }
    public void setCantidad_hasta(BigDecimal cantidad_hasta) {
        this.cantidad_hasta = cantidad_hasta;
    }
    public BigDecimal getPrecio_unitario() {
        return precio_unitario;
    }
    public void setPrecio_unitario(BigDecimal precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    
}
