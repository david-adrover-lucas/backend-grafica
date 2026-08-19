package com.drover.demo.backend.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name ="vendedores")
public class vendedores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="fecha_baja", length = 20)
    private LocalDate fechaBaja;
    @Column(name="fecha_alta", nullable = false, length = 20)
    private LocalDate fechaAlta;
    @Column(name="activo", nullable = false) 
    private Boolean activo;
    
    public vendedores() {
    }
    public vendedores(Long id, LocalDate fechaBaja, LocalDate fechaAlta, Boolean activo) {
        this.id = id;
   
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
