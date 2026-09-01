package com.drover.demo.backend.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "costos_escala_tercerizado")
public class CostosEscalaTercerizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // CORRECCIÓN 1: Relación real con la entidad del trabajo tercerizado (FK a trabajos_tercerizados.id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trabajo_tercerizado_id", nullable = false)
    private TrabajosTercerizado trabajoTercerizado; // Cambiado de Long a la clase de objeto

    // CORRECCIÓN 2: Configuración de escalas exactas DECIMAL(14,4) según tu PDF
    @Column(name = "cantidad_desde", nullable = false, precision = 14, scale = 4)
    private BigDecimal cantidadDesde;

    // Rango límite superior de volumen. Puede ser NULL si es "en adelante"
    @Column(name = "cantidad_hasta", precision = 14, scale = 4)
    private BigDecimal cantidadHasta;

    @Column(name = "precio_unitario", nullable = false, precision = 14, scale = 4)
    private BigDecimal precioUnitario;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true; // Inicializado por defecto en true
   
    // Constructor vacío obligatorio para JPA
    public CostosEscalaTercerizado() {}
   
    // Constructor completo actualizado con objetos y buenas prácticas
    public CostosEscalaTercerizado(Long id, TrabajosTercerizado trabajoTercerizado, BigDecimal cantidadDesde,
                                   BigDecimal cantidadHasta, BigDecimal precioUnitario, Boolean activo) {
        this.id = id;
        this.trabajoTercerizado = trabajoTercerizado;
        this.cantidadDesde = cantidadDesde;
        this.cantidadHasta = cantidadHasta;
        this.precioUnitario = precioUnitario;
        this.activo = activo;
    }

    // --- GETTERS Y SETTERS CORREGIDOS A CAMELCASE Y OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public TrabajosTercerizado getTrabajoTercerizado() { return trabajoTercerizado; }
    public void setTrabajoTercerizado(TrabajosTercerizado trabajoTercerizado) { this.trabajoTercerizado = trabajoTercerizado; }

    public BigDecimal getCantidadDesde() { return cantidadDesde; }
    public void setCantidadDesde(BigDecimal cantidadDesde) { this.cantidadDesde = cantidadDesde; }

    public BigDecimal getCantidadHasta() { return cantidadHasta; }
    public void setCantidadHasta(BigDecimal cantidadHasta) { this.cantidadHasta = cantidadHasta; }

    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
