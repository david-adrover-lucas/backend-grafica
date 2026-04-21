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
@Table(name = "pagos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    public enum TipoPago {
        SENA,
        TOTAL
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "venta_id", nullable = false)
    private Integer ventaId;
    
    @Column(name = "cuenta_id", nullable = false)
    private Integer cuentaId;
    
    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago")
    private TipoPago tipoPago;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}    


