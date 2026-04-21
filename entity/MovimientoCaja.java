package com.drover.demo.backend.entity;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movimientos_caja")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoCaja {
    public enum TipoMovimiento {
        INGRESO,
        EGRESO,
        DESCUENTO,
        TRANSFERENCIA
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "cuenta_id", nullable = false)
    private Integer cuentaId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoMovimiento tipo;
    
    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    
    @Column(name = "venta_id")
    private Integer ventaId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

