package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;




@Entity
@Table(name = "historial_presupuestos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialPresupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // ID del presupuesto original (opcional)
    @Column(name = "presupuesto_id")
    private Integer presupuestoId;

 
    @Column(name = "cliente_id")
    private Integer clienteId;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "estado")
    private String estado; // PENDIENTE / ACEPTADO / RECHAZADO

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "fecha")
    private LocalDateTime fecha;
}

