package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity
@Table(name = "precios_revendedor")
public class PrecioRevendedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "revendedor_id", nullable = false)
    private Revendedor revendedor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "precio", nullable = false, precision = 14, scale = 2)
    private BigDecimal precio;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // Constructor vacío obligatorio para JPA
    public PrecioRevendedor() {}

    // Constructor completo actualizado con objetos
    public PrecioRevendedor(Long id, Revendedor revendedor, Producto producto, BigDecimal precio, Boolean activo) {
        this.id = id;
        this.revendedor = revendedor;
        this.producto = producto;
        this.precio = precio;
        this.activo = activo;
    }

    // --- GETTERS Y SETTERS CORREGIDOS A OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Revendedor getRevendedor() { return revendedor; }
    public void setRevendedor(Revendedor revendedor) { this.revendedor = revendedor; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
