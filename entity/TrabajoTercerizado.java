package com.drover.demo.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trabajos_tercerizados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrabajoTercerizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   @Column(name = "empresa_id", nullable = false)
    private Integer empresaId;

   @Column(name = "venta_id", nullable = false)
    private Integer ventaId;

   @Column(name = "insumo_id")
    private Integer insumoId;

    @Column(name = "insumo_nombre", length = 100)
    private String insumoNombre;

    @Column(name = "costo", nullable = false)
    private BigDecimal costo;

    @Column(name = "estado", nullable = false)
    private String estado;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @Column(name = "fecha_retiro")
    private LocalDateTime fechaRetiro;
}
