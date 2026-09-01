package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;

@Entity 
@Table(name = "insumos")
public class Insumo {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "id")   
    private Long id;
  
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "proveedor_id", nullable = false) 
    private Proveedor proveedor; 
  
    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;
  
    @Column(name = "unidad", nullable = false, length = 20)
    private String unidad;
  
    @Column(name = "costo_unitario", nullable = false, precision = 14, scale = 4)
    private BigDecimal costoUnitario; 
  
    @Column(name = "stock_actual", nullable = false, precision = 14, scale = 4)
    private BigDecimal stockActual;
  
    @Column(name = "stock_minimo", nullable = false, precision = 14, scale = 4)
    private BigDecimal stockMinimo;
  
    @Column(name = "activo", nullable = false)
    private Boolean activo = true; 

    public Insumo() {}
    
    public Insumo(Long id, Proveedor proveedor, String nombre, String unidad, BigDecimal costoUnitario,
                  BigDecimal stockActual, BigDecimal stockMinimo, Boolean activo) {
        this.id = id;
        this.proveedor = proveedor;
        this.nombre = nombre;
        this.unidad = unidad;
        this.costoUnitario = costoUnitario;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.activo = activo;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Proveedor getProveedor() { return proveedor; }
    public void setProveedor(Proveedor proveedor) { this.proveedor = proveedor; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public BigDecimal getCostoUnitario() { return costoUnitario; }
    public void setCostoUnitario(BigDecimal costoUnitario) { this.costoUnitario = costoUnitario; }

    public BigDecimal getStockActual() { return stockActual; }
    public void setStockActual(BigDecimal stockActual) { this.stockActual = stockActual; }

    public BigDecimal getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(BigDecimal stockMinimo) { this.stockMinimo = stockMinimo; }

    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
}
