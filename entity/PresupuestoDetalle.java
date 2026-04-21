package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "presupuesto_detalle")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresupuestoDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "presupuesto_id", nullable = false)
    private Integer presupuestoId;
    
    @Column(name = "producto_id", nullable = false)
    private Integer productoId;
    
    @Column(name = "cantidad")
    private Integer cantidad;
    
    @Column(name = "alto", precision = 10, scale = 2)
    private BigDecimal alto;
    
    @Column(name = "ancho", precision = 10, scale = 2)
    private BigDecimal ancho;
    
    @Column(name = "costo", precision = 10, scale = 2)
    private BigDecimal costo;
    
    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;
}

