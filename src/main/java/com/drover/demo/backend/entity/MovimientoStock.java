package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;


@Entity
@Table(name = "movimientos_stock")
public class MovimientoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN 1: Relación obligatoria con el Insumo auditado (FK a insumos.id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insumo_id", nullable = false)
    private Insumo insumo;

    // CORRECCIÓN 2: Relación opcional con la Compra que generó la entrada (FK a compras.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "compra_id", nullable = true)
    private Compra compra;

    // CORRECCIÓN 3: Relación opcional con la Venta que generó la salida (FK a ventas.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = true)
    private Venta venta;

    // CORRECCIÓN 4: Relación opcional con el renglón específico de la venta (FK a detalle_ventas.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_venta_id", nullable = true)
    private DetalleVenta detalleVenta; // Formato camelCase

    @Column(name = "tipo", nullable = false, length = 30)
    private String tipo; // Ejemplos: "entrada_compra", "salida_venta", "ajuste_manual", "perdida"

    // CORRECCIÓN DECIMAL: Cantidades y stocks con precisión industrial de 4 decimales (DECIMAL 14,4)
    @Column(name = "cantidad", nullable = false, precision = 14, scale = 4)
    private BigDecimal cantidad;

    @Column(name = "costo_unitario", nullable = false, precision = 14, scale = 4)
    private BigDecimal costoUnitario; // Formato camelCase

    // CORRECCIÓN DECIMAL: Totales monetarios contables con escala 2 (DECIMAL 14,2)
    @Column(name = "costo_total", nullable = false, precision = 14, scale = 2)    
    private BigDecimal costoTotal;

    @Column(name = "stock_anterior", nullable = false, precision = 14, scale = 4)    
    private BigDecimal stockAnterior;

    @Column(name = "stock_posterior", nullable = false, precision = 14, scale = 4)    
    private BigDecimal stockPosterior;

    @Column(name = "fecha", nullable = false)    
    private LocalDateTime fecha;

    @Column(name = "observaciones", columnDefinition = "TEXT")    
    private String observaciones;

    // Constructor vacío obligatorio para JPA
    public MovimientoStock() {}

    // Constructor completo actualizado con objetos relacionales
    public MovimientoStock(Long id, Insumo insumo, Compra compra, Venta venta, DetalleVenta detalleVenta, String tipo,
                           BigDecimal cantidad, BigDecimal costoUnitario, BigDecimal costoTotal, BigDecimal stockAnterior,
                           BigDecimal stockPosterior, LocalDateTime fecha, String observaciones) {
        this.id = id;
        this.insumo = insumo;
        this.compra = compra;
        this.venta = venta;
        this.detalleVenta = detalleVenta;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.costoTotal = costoTotal;
        this.stockAnterior = stockAnterior;
        this.stockPosterior = stockPosterior;
        this.fecha = fecha;
        this.observaciones = observaciones;
    }

    // --- GETTERS Y SETTERS CORREGIDOS A CAMELCASE Y OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Insumo getInsumo() { return insumo; }
    public void setInsumo(Insumo insumo) { this.insumo = insumo; }

    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public DetalleVenta getDetalleVenta() { return detalleVenta; }
    public void setDetalleVenta(DetalleVenta detalleVenta) { this.detalleVenta = detalleVenta; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }

    public BigDecimal getCostoTotal() { return costoTotal; }
    public void setCostoTotal(BigDecimal costoTotal) { this.costoTotal = costoTotal; }

    public BigDecimal getStockAnterior() { return stockAnterior; }
    public void setStockAnterior(BigDecimal stockAnterior) { this.stockAnterior = stockAnterior; }

    public BigDecimal getStockPosterior() { return stockPosterior; }
    public void setStockPosterior(BigDecimal stockPosterior) { this.stockPosterior = stockPosterior; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
