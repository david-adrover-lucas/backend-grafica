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
@Table(name = "pagos_venta")
public class Pago_venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "venta_id")
    private Long venta_id;
    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;
    @Column(name = "monto", nullable = false, length = 15)
    private BigDecimal monto;
    @Column(name = "medio_pago", nullable = false, length = 50)
    private String medio_pago;
    @Column(name = "observaciones")
    private String observaciones;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getVenta_id() {
        return venta_id;
    }
    public void setVenta_id(Long venta_id) {
        this.venta_id = venta_id;
    }
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public BigDecimal getMonto() {
        return monto;
    }
    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    public String getMedio_pago() {
        return medio_pago;
    }
    public void setMedio_pago(String medio_pago) {
        this.medio_pago = medio_pago;
    }
    public String getObservacion() {
        return observaciones;
    }
    public void setObservacion(String observacion) {
        this.observaciones = observacion;
    }
    public Pago_venta(Long id, Long venta_id, LocalDateTime fecha, BigDecimal monto, String medio_pago,
            String observaciones) {
        this.id = id;
        this.venta_id = venta_id;
        this.fecha = fecha;
        this.monto = monto;
        this.medio_pago = medio_pago;
        this.observaciones = observaciones;
    }
    public Pago_venta() {
    }

}
