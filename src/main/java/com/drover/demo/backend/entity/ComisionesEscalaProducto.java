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
@Table(name = "comisiones_escala_producto")
public class ComisionesEscalaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")   
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // 🌟 CORRECCIÓN CLAVE: Nombres neutros y universales para m2, lineales o unidades
    @Column(name = "cantidad_desde", nullable = false, precision = 14, scale = 4)
    private BigDecimal cantidadDesde;

    @Column(name = "cantidad_hasta", precision = 14, scale = 4)
    private BigDecimal cantidadHasta;

    @Column(name = "monto_comision", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoComision;

    @Column(name = "activo", nullable = false)       
    private Boolean activo = true;

    public ComisionesEscalaProducto() {}

    public ComisionesEscalaProducto(Long id, Producto producto, BigDecimal cantidadDesde, BigDecimal cantidadHasta, 
                                    BigDecimal montoComision, Boolean activo) {
        this.id = id;
        this.producto = producto;
        this.cantidadDesde = cantidadDesde;
        this.cantidadHasta = cantidadHasta;
        this.montoComision = montoComision;
        this.activo = activo;
    }

    // --- GETTERS Y SETTERS CORREGIDOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public BigDecimal getCantidadDesde() { return cantidadDesde; }
    public void setCantidadDesde(BigDecimal cantidadDesde) { this.cantidadDesde = cantidadDesde; }

    public BigDecimal getCantidadHasta() { return cantidadHasta; }
    public void setCantidadHasta(BigDecimal cantidadHasta) { this.cantidadHasta = cantidadHasta; }

    public BigDecimal getMontoComision() { return montoComision; }
    public void setMontoComision(BigDecimal montoComision) { this.montoComision = montoComision; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
