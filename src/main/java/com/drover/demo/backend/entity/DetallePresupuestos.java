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
@Table(name = "detalle_presupuestos")
public class DetallePresupuestos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN 1: Relación real con el Presupuesto madre (FK a presupuestos.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presupuesto_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos de serialización en Postman
    private Presupuesto presupuesto;

    // CORRECCIÓN 2: Relación real con el Producto cotizado (FK a productos.id)
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

    // CORRECCIÓN DECIMAL: Auditorías contables y dinero llevan escala 2 (DECIMAL 14,2)
    @Column(name = "costo_unitario_historico", nullable = false, precision = 14, scale = 2) 
    private BigDecimal costoUnitarioHistorico; // Cambiado a camelCase

    @Column(name = "monto_ganancia_unitario", nullable = false, precision = 14, scale = 2) 
    private BigDecimal montoGananciaUnitario; // Recuerda que actúa como porcentaje o margen según definas

    @Column(name = "precio_unitario", nullable = false, precision = 14, scale = 2) 
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", nullable = false, precision = 14, scale = 2) 
    private BigDecimal subtotal;

    // Constructor vacío obligatorio para JPA
    public DetallePresupuestos() {}

    // Constructor completo actualizado con objetos y buenas prácticas
    public DetallePresupuestos(Long id, Presupuesto presupuesto, Producto producto, BigDecimal cantidad, 
                               BigDecimal ancho, BigDecimal alto, BigDecimal costoUnitarioHistorico, 
                               BigDecimal montoGananciaUnitario, BigDecimal precioUnitario, BigDecimal subtotal) {
        this.id = id;
        this.presupuesto = presupuesto;
        this.producto = producto;
        this.cantidad = cantidad;
        this.ancho = ancho;
        this.alto = alto;
        this.costoUnitarioHistorico = costoUnitarioHistorico;
        this.montoGananciaUnitario = montoGananciaUnitario;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // --- GETTERS Y SETTERS CORREGIDOS A CAMELCASE Y ENFOQUE DE OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Presupuesto getPresupuesto() { return presupuesto; }
    public void setPresupuesto(Presupuesto presupuesto) { this.presupuesto = presupuesto; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public BigDecimal getAncho() { return ancho; }
    public void setAncho(BigDecimal ancho) { this.ancho = ancho; }

    public BigDecimal getAlto() { return alto; }
    public void setAlto(BigDecimal alto) { this.alto = alto; }

    public BigDecimal getCostoUnitarioHistorico() { return costoUnitarioHistorico; }
    public void setCostoUnitarioHistorico(BigDecimal costoUnitarioHistorico) { this.costoUnitarioHistorico = costoUnitarioHistorico; }

    public BigDecimal getMontoGananciaUnitario() { return montoGananciaUnitario; }
    public void setMontoGananciaUnitario(BigDecimal montoGananciaUnitario) { this.montoGananciaUnitario = montoGananciaUnitario; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}

