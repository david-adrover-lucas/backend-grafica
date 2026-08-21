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
@Table(name = "compras")
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column( name = "proveedor_id")
    private Long  proveedor_id;
    @Column( name = "fecha",nullable = false)
    private LocalDateTime fecha;
    @Column(name="numero_compra", nullable = false, length = 30)
    private String numero_compra;
    @Column(name = "total",nullable = false,length = 15)
    private BigDecimal total;
    @Column(name = "observaciones")
    private String observaciones;
    
    public Compra() {
    }

    public Compra(Long id, Long proveedor_id, LocalDateTime fecha, String numero_compra, BigDecimal total,
            String observaciones) {
        this.id = id;
        this.proveedor_id = proveedor_id;
        this.fecha = fecha;
        this.numero_compra = numero_compra;
        this.total = total;
        this.observaciones = observaciones;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProveedor_id() {
        return proveedor_id;
    }
    public void setProveedor_id(Long proveedor_id) {
        this.proveedor_id = proveedor_id;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public String getNumero_compra() {
        return numero_compra;
    }
    public void setNumero_compra(String numero_compra) {
        this.numero_compra = numero_compra;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
