package com.drover.demo.backend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "precios_escala_producto")
public class PreciosEscalaProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad_desde", nullable = false, precision = 14, scale = 4)
    private BigDecimal cantidadDesde;

    @Column(name = "cantidad_hasta", precision = 14, scale = 4) 
    private BigDecimal cantidadHasta;

    // CORRECCIÓN: Guardamos el porcentaje (ej: 20.00 para el 20%, 8.00 para el 8%)
    @Column(name = "porcentaje_ganancia_escala", nullable = false, precision = 6, scale = 2)
    private BigDecimal porcentajeGananciaEscala;

    public PreciosEscalaProducto() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public BigDecimal getCantidadDesde() { return cantidadDesde; }
    public void setCantidadDesde(BigDecimal cantidadDesde) { this.cantidadDesde = cantidadDesde; }
    public BigDecimal getCantidadHasta() { return cantidadHasta; }
    public void setCantidadHasta(BigDecimal cantidadHasta) { this.cantidadHasta = cantidadHasta; }
    public BigDecimal getPorcentajeGananciaEscala() { return porcentajeGananciaEscala; }
    public void setPorcentajeGananciaEscala(BigDecimal porcentajeGananciaEscala) { this.porcentajeGananciaEscala = porcentajeGananciaEscala; }
}

