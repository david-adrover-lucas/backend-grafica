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
@Table(name = "comisiones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;
    
    @Column(name = "producto_id", nullable = false)
    private Integer productoId;
    
    @Column(name = "monto_fijo", precision = 10, scale = 2)
    private BigDecimal montoFijo;
    
    @Column(name = "porcentaje", precision = 5, scale = 2)
    private BigDecimal porcentaje;
}

