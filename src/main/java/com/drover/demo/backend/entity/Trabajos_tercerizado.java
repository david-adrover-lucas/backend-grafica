package com.drover.demo.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "trabajos_tercerizados")
public class Trabajos_tercerizado {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "provedor_id")
    private Long provedor_id;
    @Column(name = "nombre",nullable = false, length = 150)
    private String nombre;
    @Column(name = "unidad_calculo", nullable = false, length = 20)
    private String unidad_calculo;
    @Column(name = "activo", nullable = false)
    private Boolean activo;
    public Trabajos_tercerizado() {
    }
    public Trabajos_tercerizado(Long id, Long provedor_id, String nombre, String unidad_calculo, Boolean activo) {
        this.id = id;
        this.provedor_id = provedor_id;
        this.nombre = nombre;
        this.unidad_calculo = unidad_calculo;
        this.activo = activo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getProvedor_id() {
        return provedor_id;
    }
    public void setProvedor_id(Long provedor_id) {
        this.provedor_id = provedor_id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getUnidad_calculo() {
        return unidad_calculo;
    }
    public void setUnidad_calculo(String unidad_calculo) {
        this.unidad_calculo = unidad_calculo;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
