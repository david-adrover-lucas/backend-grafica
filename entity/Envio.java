package com.drover.demo.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "envios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Envio {

    // 📌 ENUM ESTADO
    public enum EstadoEnvio {
        PENDIENTE,
        ENVIADO,
        ENTREGADO,
        CANCELADO
    }

    // 📌 ENUM TIPO
    public enum TipoEnvio {
        ENVIO,
        RETIRO
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
   
    @Column(name = "venta_id", nullable = false)
    private Integer ventaId;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoEnvio estado;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoEnvio tipo;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "fecha_entrega")
    private LocalDateTime fechaEntrega;
}

