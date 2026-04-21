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
@Table(name = "venta_detalle_insumo_extra")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaDetalleInsumoExtra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "venta_detalle_id", nullable = false)
    private Integer ventaDetalleId;
    
    @Column(name = "insumo_id", nullable = false)
    private Integer insumoId;
    
    @Column(name = "cantidad", precision = 10, scale = 2)
    private Integer cantidad;
    
    @Column(name = "costo_unitario", precision = 10, scale = 2)
    private BigDecimal costoUnitario;
}

