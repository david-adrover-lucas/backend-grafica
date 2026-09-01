package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "producto_insumos")
public class ProductoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Relación ManyToOne hacia el Producto padre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false) // FK real en la BD hacia productos.id
    @JsonIgnore // Evita bucles infinitos en el JSON de salida de Postman
    private Producto producto;

    // Relación ManyToOne hacia el Insumo que consume la receta
    @ManyToOne(fetch = FetchType.EAGER) // Trae los datos base del insumo automáticamente al consultar
    @JoinColumn(name = "insumo_id", nullable = false) // FK real en la BD hacia insumos.id
    private Insumo insumo;

    // Mapeo exacto según tu PDF (DECIMAL 14,4) para soportar fracciones de metros, m2 o planchas
    @Column(name = "cantidad", nullable = false, precision = 14, scale = 4)
    private BigDecimal cantidad;

    // Constructor vacío obligatorio para JPA
    public ProductoInsumo() {}

    public ProductoInsumo(Long id, Producto producto, Insumo insumo, BigDecimal cantidad) {
        this.id = id;
        this.producto = producto;
        this.insumo = insumo;
        this.cantidad = cantidad;
    }

    // --- GETTERS Y SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public Insumo getInsumo() { return insumo; }
    public void setInsumo(Insumo insumo) { this.insumo = insumo; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }
}

