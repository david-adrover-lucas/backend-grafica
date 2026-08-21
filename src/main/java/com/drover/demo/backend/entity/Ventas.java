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
@Table(name = "ventas")
public class Ventas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;
    @Column(name = "nro_venta", nullable = false, unique = true, length = 30)
    private String nro_venta;
    @Column(name= "cliente_id")  
    private Long cliente_id;
    @Column(name= "revendedor_id") 
    private Long revendedor_id;
    @Column(name= "responsable_id")  
    private Long responsable_id;
    @Column(name = "fecha_venta",nullable = false)
    private LocalDateTime  fecha_venta;
    @Column(name = "estado_venta",nullable = false, length = 30)
    private String  estado_venta;
    @Column(name = "estado_pago",nullable = false, length = 20)
    private String estado_pago;
    @Column(name = "monto_total",nullable = false, length = 15)
    private BigDecimal monto_total;
    @Column(name = "observaciones")
    private String observaciones;

    public Ventas() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNro_venta() {
        return nro_venta;
    }
    public void setNro_venta(String nro_venta) {
        this.nro_venta = nro_venta;
    }
    public Long getCliente_id() {
        return cliente_id;
    }
    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }
    public Long getRevendedor_id() {
        return revendedor_id;
    }
    public void setRevendedor_id(Long revendedor_id) {
        this.revendedor_id = revendedor_id;
    }
    public Long getResponsable_id() {
        return responsable_id;
    }
    public void setResponsable_id(Long responsable_id) {
        this.responsable_id = responsable_id;
    }
    public LocalDateTime getFecha_venta() {
        return fecha_venta;
    }
    public void setFecha_venta(LocalDateTime fecha_venta) {
        this.fecha_venta = fecha_venta;
    }
    public String getEstado_venta() {
        return estado_venta;
    }
    public void setEstado_venta(String estado_venta) {
        this.estado_venta = estado_venta;
    }
    public String getEstado_pago() {
        return estado_pago;
    }
    public void setEstado_pago(String estado_pago) {
        this.estado_pago = estado_pago;
    }
    public BigDecimal getMonto_total() {
        return monto_total;
    }
    public void setMonto_total(BigDecimal monto_total) {
        this.monto_total = monto_total;
    }
    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    
    public Ventas(Long id, String nro_venta, Long cliente_id, Long revendedor_id, Long responsable_id,
            LocalDateTime fecha_venta, String estado_venta, String estado_pago, BigDecimal monto_total,
            String observaciones) {
        this.id = id;
        this.nro_venta = nro_venta;
        this.cliente_id = cliente_id;
        this.revendedor_id = revendedor_id;
        this.responsable_id = responsable_id;
        this.fecha_venta = fecha_venta;
        this.estado_venta = estado_venta;
        this.estado_pago = estado_pago;
        this.monto_total = monto_total;
        this.observaciones = observaciones;
    }
    
}
