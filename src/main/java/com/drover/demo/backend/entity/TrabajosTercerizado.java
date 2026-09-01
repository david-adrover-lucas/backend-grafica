package com.drover.demo.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name = "trabajos_tercerizados")
public class TrabajosTercerizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN CLAVE: Relación real ManyToOne con la tabla de proveedores
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "proveedor_id", nullable = false) // FK real en la BD proveedores.id
    private Proveedor proveedor;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "unidad_calculo", nullable = false, length = 20)
    private String unidadCalculo; // Cambiado a camelCase

    @Column(name = "activo", nullable = false)
    private Boolean activo = true; // Inicializado por defecto en true

    public TrabajosTercerizado() {}

    // Constructor completo actualizado con objetos y nombres estándar
    public TrabajosTercerizado(Long id, Proveedor proveedor, String nombre, String unidadCalculo, Boolean activo) {
        this.id = id;
        this.proveedor = proveedor;
        this.nombre = nombre;
        this.unidadCalculo = unidadCalculo;
        this.activo = activo;
    }

    // --- GETTERS Y SETTERS CORREGIDOS A CAMELCASE Y OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUnidadCalculo() { return unidadCalculo; }
    public void setUnidadCalculo(String unidadCalculo) { this.unidadCalculo = unidadCalculo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
