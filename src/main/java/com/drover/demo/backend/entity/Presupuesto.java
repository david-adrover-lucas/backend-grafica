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
@Table(name = "presupuestos")
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nro_presupuesto", nullable = false, unique = true, length = 30)
    private String nro_presupuesto;
    @Column(name = "cliente_id")
    private Long cliente_id;
    @Column(name = "revendedor_id")
    private Long revendedor_id;
    @Column(name = "responsable_id") 
    private Long responsable_id;
    @Column(name = "fecha", nullable = false) 
    private LocalDateTime fecha;
    @Column(name = "estado",nullable = false,length = 20) 
    private String estado;
    @Column(name = "monto_total",nullable = false, length = 15) 
    private BigDecimal monto_total;
    @Column(name = "observaciones") 
    private String observaciones;

    public Presupuesto() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNro_presupuesto() {
        return nro_presupuesto;
    }
    public void setNro_presupuesto(String nro_presupuesto) {
        this.nro_presupuesto = nro_presupuesto;
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
    public LocalDateTime getFecha() {
        return fecha;
    }
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
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
    
    public Presupuesto(Long id, String nro_presupuesto, Long cliente_id, Long revendedor_id, Long responsable_id,
            LocalDateTime fecha, String estado, BigDecimal monto_total, String observaciones) {
        this.id = id;
        this.nro_presupuesto = nro_presupuesto;
        this.cliente_id = cliente_id;
        this.revendedor_id = revendedor_id;
        this.responsable_id = responsable_id;
        this.fecha = fecha;
        this.estado = estado;
        this.monto_total = monto_total;
        this.observaciones = observaciones;
    }
}
