package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="sueldo_mensual", length = 20)
    private BigDecimal sueldoMensual;
    @Column(name="fecha_baja", length = 20)
    private LocalDate fechaBaja;
    @Column(name="fecha_alta", nullable = false, length = 20)
    private LocalDate fechaAlta;
    @Column(name="activo", nullable = false, length = 30) 
    private Boolean activo;

    public Empleado() {
    }
    
    public Empleado(Long id, BigDecimal sueldoMensual, LocalDate fechaBaja, LocalDate fechaAlta, Boolean activo) {
        this.id = id;
        this.sueldoMensual = sueldoMensual;
        this.fechaBaja = fechaBaja;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
    }
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    public BigDecimal getSueldoMensual() {
        return sueldoMensual;
    }
    public void setSueldoMensual(BigDecimal sueldoMensual) {
        this.sueldoMensual = sueldoMensual;
    }
    public LocalDate getFechaBaja() {
        return fechaBaja;
    }
    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    public LocalDate getFechaAlta() {
        return fechaAlta;
    }
    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
