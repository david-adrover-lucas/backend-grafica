package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "producto_trabajo_tercerizado")
public class ProductoTrabajoTercerizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN 1: Relación real con la cabecera del Producto padre (FK a productos.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id", nullable = false)
    @JsonIgnore // Evita bucles infinitos en el JSON al serializar la relación bidireccional
    private Producto producto;

    // CORRECCIÓN 2: Relación real con el servicio externo asociado (FK a trabajos_tercerizados.id)
    @ManyToOne(fetch = FetchType.EAGER) // Trae los datos base del servicio de inmediato al consultar
    @JoinColumn(name = "trabajo_tercerizado_id", nullable = false)
    private TrabajosTercerizado trabajoTercerizado; // Cambiado de Long a la clase Entity

    // CORRECCIÓN DECIMAL: Mapeo numérico industrial exacto (DECIMAL 14,4 según tu PDF)
    @Column(name = "cantidad_requerida", nullable = false, precision = 14, scale = 4)
    private BigDecimal cantidadRequerida; // Cambiado a camelCase

    // Constructor vacío obligatorio para JPA
    public ProductoTrabajoTercerizado() {}

    // Constructor completo actualizado con objetos
    public ProductoTrabajoTercerizado(Long id, Producto producto, TrabajosTercerizado trabajoTercerizado,
                                      BigDecimal cantidadRequerida) {
        this.id = id;
        this.producto = producto;
        this.trabajoTercerizado = trabajoTercerizado;
        this.cantidadRequerida = cantidadRequerida;
    }

    // --- GETTERS Y SETTERS ACTUALIZADOS CON OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public TrabajosTercerizado getTrabajoTercerizado() { return trabajoTercerizado; }
    public void setTrabajoTercerizado(TrabajosTercerizado trabajoTercerizado) { this.trabajoTercerizado = trabajoTercerizado; }

    public BigDecimal getCantidadRequerida() { return cantidadRequerida; }
    public void setCantidadRequerida(BigDecimal cantidadRequerida) { this.cantidadRequerida = cantidadRequerida; }
}
