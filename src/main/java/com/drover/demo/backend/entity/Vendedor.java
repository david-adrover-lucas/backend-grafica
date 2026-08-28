package com.drover.demo.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name = "vendedores")
@PrimaryKeyJoinColumn(name = "id") 
public class Vendedor extends Persona {

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(name = "fecha_baja") 
    private LocalDate fechaBaja;

    @Column(nullable = false)
    private Boolean activo = true;

    public Vendedor() {
        super();
    }

    
    public LocalDate getFechaAlta() { return fechaAlta; }
    public void setFechaAlta(LocalDate fechaAlta) { this.fechaAlta = fechaAlta; }
    public LocalDate getFechaBaja() { return fechaBaja; }
    public void setFechaBaja(LocalDate fechaBaja) { this.fechaBaja = fechaBaja; }
    @Override
    public Boolean getActivo() { return activo; }
    @Override
    public void setActivo(Boolean activo) { this.activo = activo; }
}
