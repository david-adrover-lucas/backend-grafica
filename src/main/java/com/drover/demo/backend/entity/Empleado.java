package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;


@Entity
@Table(name = "empleados")
@PrimaryKeyJoinColumn(name = "id")
public class Empleado extends Persona {

    @Column(name = "sueldo_mensual",  nullable = true, precision = 14, scale = 2)
    private BigDecimal sueldoMensual;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    @Column(nullable = false)
    private Boolean activo = true;

    public Empleado() {
        super();
    }

    public BigDecimal getSueldoMensual() { return sueldoMensual; }
    public void setSueldoMensual(BigDecimal sueldoMensual) { this.sueldoMensual = sueldoMensual; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public LocalDate getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(LocalDate fechaBaja) { this.fechaBaja = fechaBaja; }
    @Override
    public Boolean getActivo() { return activo; }
    @Override
    public void setActivo(Boolean activo) { this.activo = activo; }
}
