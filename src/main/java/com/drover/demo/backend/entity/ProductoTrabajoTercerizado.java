package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto_trabajo_tercerizado")
public class ProductoTrabajoTercerizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "producto_id")
    private Long producto_id;
    @Column(name = "trabajo_tercerizado_id")
    private Long trabajo_tercerizado_id;
    @Column(name = "cantidad_requerida",nullable = false,length = 15)
    private BigDecimal cantidad_requerida;
    public ProductoTrabajoTercerizado() {
    }
    public ProductoTrabajoTercerizado(Long id, Long producto_id, Long trabajo_tercerizado_id,
            BigDecimal cantidad_requerida) {
        this.id = id;
        this.producto_id = producto_id;
        this.trabajo_tercerizado_id = trabajo_tercerizado_id;
        this.cantidad_requerida = cantidad_requerida;
    }
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
    public Long getTrabajo_tercerizado_id() {
        return trabajo_tercerizado_id;
    }
    public void setTrabajo_tercerizado_id(Long trabajo_tercerizado_id) {
        this.trabajo_tercerizado_id = trabajo_tercerizado_id;
    }
    public BigDecimal getCantidad_requerida() {
        return cantidad_requerida;
    }
    public void setCantidad_requerida(BigDecimal cantidad_requerida) {
        this.cantidad_requerida = cantidad_requerida;
    }
    
}
