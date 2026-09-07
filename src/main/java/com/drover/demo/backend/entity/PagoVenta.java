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
@Table(name = "pagos_venta")
public class PagoVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN 1: Relación real con la Venta madre (FK a ventas.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venta_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos en Postman al serializar la venta y sus pagos
    private Venta venta;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    // CORRECCIÓN DECIMAL: Configuración contable exacta para dinero (DECIMAL 14,2 según tu PDF)
    @Column(name = "monto", nullable = false, precision = 14, scale = 2)
    private BigDecimal monto;

    @Column(name = "medio_pago", nullable = false, length = 50)
    private String medioPago; // Cambiado a camelCase (Ej: "efectivo", "transferencia", "tarjeta")

    @Column(name = "proveedor_pago", length = 50)
    private String proveedorPago;

    @Column(name = "id_pago_externo", length = 100, unique = true)
    private String idPagoExterno;

    @Column(name = "referencia_externa", length = 150)
    private String referenciaExterna;

    @Column(name = "estado_externo", length = 50)
    private String estadoExterno;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    // Constructor vacío obligatorio para JPA
    public PagoVenta() {}

    // Constructor completo actualizado con objetos
    public PagoVenta(Long id, Venta venta, Cuenta cuenta, LocalDateTime fecha, BigDecimal monto, String medioPago, String observaciones) {
        this.id = id;
        this.venta = venta;
        this.cuenta = cuenta;
        this.fecha = fecha;
        this.monto = monto;
        this.medioPago = medioPago;
        this.observaciones = observaciones;
    }

    // --- GETTERS Y SETTERS CORREGIDOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Venta getVenta() { return venta; }
    public void setVenta(Venta venta) { this.venta = venta; }

    public Cuenta getCuenta() { return cuenta; }
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public BigDecimal getMonto() { return monto; }
    public void setMonto(BigDecimal monto) { this.monto = monto; }

    public String getMedioPago() { return medioPago; }
    public void setMedioPago(String medioPago) { this.medioPago = medioPago; }

    public String getProveedorPago() { return proveedorPago; }
    public void setProveedorPago(String proveedorPago) { this.proveedorPago = proveedorPago; }

    public String getIdPagoExterno() { return idPagoExterno; }
    public void setIdPagoExterno(String idPagoExterno) { this.idPagoExterno = idPagoExterno; }

    public String getReferenciaExterna() { return referenciaExterna; }
    public void setReferenciaExterna(String referenciaExterna) { this.referenciaExterna = referenciaExterna; }

    public String getEstadoExterno() { return estadoExterno; }
    public void setEstadoExterno(String estadoExterno) { this.estadoExterno = estadoExterno; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}

