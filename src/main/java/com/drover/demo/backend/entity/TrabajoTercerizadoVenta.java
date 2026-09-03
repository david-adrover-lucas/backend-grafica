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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "trabajos_tercerizados_venta")
public class TrabajoTercerizadoVenta { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN 1: Relación real con el Comprobante de Venta general (FK a ventas.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonIgnore // Impide bucles infinitos en el JSON de salida de Postman
    private Venta venta;

    // CORRECCIÓN 2: Relación real con el renglón específico del artículo vendido (FK a detalle_ventas.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "detalle_venta_id", nullable = false)
    private DetalleVenta detalleVenta; // Formato camelCase

    // CORRECCIÓN 3: Relación real con la tarea del catálogo de servicios externos (FK a trabajos_tercerizados.id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trabajo_tercerizado_id", nullable = false)
    private TrabajosTercerizado trabajoTercerizado;

    // CORRECCIÓN 4: Relación real con la empresa o Proveedor externo encargado (FK a proveedores.id)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    // CORRECCIÓN DECIMAL: Cantidades y precios de proveedores llevan escala 4 (DECIMAL 14,4 según tu PDF)
    @Column(name = "cantidad", nullable = false, precision = 14, scale = 4)    
    private BigDecimal cantidad;

    @Column(name = "precio_unitario_historico", nullable = false, precision = 14, scale = 4)    
    private BigDecimal precioUnitarioHistorico;

    // CORRECCIÓN DECIMAL: Totales monetarios contables a pagar llevan escala 2 (DECIMAL 14,2)
    @Column(name = "costo_total_historico", nullable = false, precision = 14, scale = 2)    
    private BigDecimal costoTotalHistorico;

    @Column(name = "estado", nullable = false, length = 20)    
    private String estado; // Ejemplos: "pendiente", "enviado", "finalizado"

    @Column(name = "fecha_envio")    
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_finalizacion")    
    private LocalDateTime fechaFinalizacion;

    // Constructor vacío obligatorio para JPA
    public TrabajoTercerizadoVenta() {}

    // Constructor completo actualizado con objetos
    public TrabajoTercerizadoVenta(Long id, Venta venta, DetalleVenta detalleVenta, TrabajosTercerizado trabajoTercerizado,
                                   Proveedor proveedor, BigDecimal cantidad, BigDecimal precioUnitarioHistorico,
                                   BigDecimal costoTotalHistorico, String estado, LocalDateTime fechaEnvio,
                                   LocalDateTime fechaFinalizacion) {
        this.id = id;
        this.venta = venta;
        this.detalleVenta = detalleVenta;
        this.trabajoTercerizado = trabajoTercerizado;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
        this.precioUnitarioHistorico = precioUnitarioHistorico;
        this.costoTotalHistorico = costoTotalHistorico;
        this.estado = estado;
        this.fechaEnvio = fechaEnvio;
        this.fechaFinalizacion = fechaFinalizacion;
    }

    // --- GETTERS Y SETTERS CORREGIDOS A CAMELCASE Y OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public DetalleVenta getDetalleVenta() { return detalleVenta; }
    public void setDetalleVenta(DetalleVenta detalleVenta) { this.detalleVenta = detalleVenta; }

    public TrabajosTercerizado getTrabajoTercerizado() { return trabajoTercerizado; }
    public void setTrabajoTercerizado(TrabajosTercerizado trabajoTercerizado) { this.trabajoTercerizado = trabajoTercerizado; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public BigDecimal getCantidad() { return cantidad; }
    public void setCantidad(BigDecimal cantidad) { this.cantidad = cantidad; }

    public BigDecimal getPrecioUnitarioHistorico() { return precioUnitarioHistorico; }
    public void setPrecioUnitarioHistorico(BigDecimal precioUnitarioHistorico) { this.precioUnitarioHistorico = precioUnitarioHistorico; }

    public BigDecimal getCostoTotalHistorico() { return costoTotalHistorico; }
    public void setCostoTotalHistorico(BigDecimal costoTotalHistorico) { this.costoTotalHistorico = costoTotalHistorico; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDateTime fechaEnvio) { this.fechaEnvio = fechaEnvio; }

    public LocalDateTime getFechaFinalizacion() { return fechaFinalizacion; }
    public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) { this.fechaFinalizacion = fechaFinalizacion; }
}
