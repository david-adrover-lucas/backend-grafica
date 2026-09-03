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
@Table(name = "detalle_ventas")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN 1: Relación real con el Comprobante de Venta madre (FK a ventas.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos de serialización en Postman
    private Venta venta;

    // CORRECCIÓN 2: Relación real con el Producto vendido (FK a productos.id)
    @ManyToOne(fetch = FetchType.EAGER) // Trae los datos básicos del producto de inmediato al consultar
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    // CORRECCIÓN DECIMAL: Cantidades y medidas industriales exactas (DECIMAL 14,4 según tu PDF)
    @Column(name = "cantidad", nullable = false, precision = 14, scale = 4)
    private BigDecimal cantidad;

    @Column(name = "ancho", precision = 14, scale = 4)
    private BigDecimal ancho; // Puede ser NULL si el producto se vende por unidad suelta o lineal

    @Column(name = "alto", precision = 14, scale = 4)
    private BigDecimal alto; // Puede ser NULL si el producto se vende por unidad suelta o lineal

    // CORRECCIÓN DECIMAL: Auditorías contables e históricos llevan escala 2 (DECIMAL 14,2)
    @Column(name = "precio_unitario_historico", nullable = false, precision = 14, scale = 2)
    private BigDecimal precioUnitarioHistorico; // Cambiado a camelCase

    @Column(name = "costo_historico", nullable = false, precision = 14, scale = 2)
    private BigDecimal costoHistorico;

    @Column(name = "monto_ganancia_historico", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoGananciaHistorico;

    @Column(name = "monto_comision_historico", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoComisionHistorico;

    @Column(name = "subtotal", nullable = false, precision = 14, scale = 2)
    private BigDecimal subtotal;

    // Constructor vacío obligatorio para JPA
    public DetalleVenta() {}

    // Constructor completo actualizado con objetos y buenas prácticas
    public DetalleVenta(Long id, Venta venta, Producto producto, BigDecimal cantidad, BigDecimal ancho,
                        BigDecimal alto, BigDecimal precioUnitarioHistorico, BigDecimal costoHistorico,
                        BigDecimal montoGananciaHistorico, BigDecimal montoComisionHistorico, BigDecimal subtotal) {
        this.id = id;
        this.venta = venta;
        this.producto = producto;
        this.cantidad = cantidad;
        this.ancho = ancho;
        this.alto = alto;
        this.precioUnitarioHistorico = precioUnitarioHistorico;
        this.costoHistorico = costoHistorico;
        this.montoGananciaHistorico = montoGananciaHistorico;
        this.montoComisionHistorico = montoComisionHistorico;
        this.subtotal = subtotal;
    }

    // --- GETTERS Y SETTERS CORREGIDOS A CAMELCASE Y ENFOQUE DE OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public BigDecimal getAncho() { return ancho; }
    public void setAncho(BigDecimal ancho) { this.ancho = ancho; }

    public BigDecimal getAlto() { return alto; }
    public void setAlto(BigDecimal alto) { this.alto = alto; }

    public BigDecimal getPrecioUnitarioHistorico() { return precioUnitarioHistorico; }
    public void setPrecioUnitarioHistorico(BigDecimal precioUnitarioHistorico) { this.precioUnitarioHistorico = precioUnitarioHistorico; }

    public BigDecimal getCostoHistorico() { return costoHistorico; }
    public void setCostoHistorico(BigDecimal costoHistorico) { this.costoHistorico = costoHistorico; }

    public BigDecimal getMontoGananciaHistorico() { return montoGananciaHistorico; }
    public void setMontoGananciaHistorico(BigDecimal montoGananciaHistorico) { this.montoGananciaHistorico = montoGananciaHistorico; }

    public BigDecimal getMontoComisionHistorico() { return montoComisionHistorico; }
    public void setMontoComisionHistorico(BigDecimal montoComisionHistorico) { this.montoComisionHistorico = montoComisionHistorico; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}

