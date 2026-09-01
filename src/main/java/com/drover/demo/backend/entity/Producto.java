package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "unidad_venta", nullable = false, length = 20)
    private String unidadVenta; 

    @Column(name = "costo_actual", nullable = false, precision = 14, scale = 2)
    private BigDecimal costoActual;

    @Column(name = "monto_ganancia", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoGanancia; // Recuerda que actúa como Porcentaje de Ganancia en el Service

    @Column(name = "precio_venta", nullable = false, precision = 14, scale = 2)
    private BigDecimal precioVenta;

    @Column(name = "activo", nullable = false) 
    private Boolean activo = true; 

    // 🌟 CORRECCIÓN CLAVE: Agregamos la relación con la tabla intermedia (receta)
    // Usamos CascadeType.ALL para poder guardar y editar el producto y su receta juntos en un solo paso
    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ProductoInsumo> insumosComponentes = new ArrayList<>();

    // Constructor vacío obligatorio para JPA
    public Producto() {}

    // Constructor completo actualizado incluyendo la lista de componentes
    public Producto(Long id, String nombre, String descripcion, String unidadVenta, BigDecimal costoActual,
                    BigDecimal montoGanancia, BigDecimal precioVenta, Boolean activo, List<ProductoInsumo> insumosComponentes) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadVenta = unidadVenta;
        this.costoActual = costoActual;
        this.montoGanancia = montoGanancia;
        this.precioVenta = precioVenta;
        this.activo = activo;
        this.insumosComponentes = insumosComponentes != null ? insumosComponentes : new ArrayList<>();
    }

    // --- GETTERS Y SETTERS CORREGIDOS Y ACTUALIZADOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    // Corregido nombre del método a getDescripcion para evitar problemas de firmas
    public String getDescripcion() { return descripcion; } 
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUnidadVenta() { return unidadVenta; }
    public void setUnidadVenta(String unidadVenta) { this.unidadVenta = unidadVenta; }

    public BigDecimal getCostoActual() { return costoActual; }
    public void setCostoActual(BigDecimal costoActual) { this.costoActual = costoActual; }

    public BigDecimal getMontoGanancia() { return montoGanancia; }
    public void setMontoGanancia(BigDecimal montoGanancia) { this.montoGanancia = montoGanancia; }

    public BigDecimal getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(BigDecimal precioVenta) { this.precioVenta = precioVenta; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }

    // 🌟 GETTER Y SETTER OBLIGATORIOS PARA EL FUNCIONAMIENTO DEL SERVICE
    public List<ProductoInsumo> getInsumosComponentes() { return insumosComponentes; }
    public void setInsumosComponentes(List<ProductoInsumo> insumosComponentes) { this.insumosComponentes = insumosComponentes; }
}
