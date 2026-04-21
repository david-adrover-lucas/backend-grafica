package com.drover.demo.backend.entity;

import java.time.LocalDateTime;
import java.time.LocalDate;
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
@Table(name = "ventas")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    
    public enum EstadoVenta {
        DISENAR,
        CONFIRMAR,
        ENVIAR,
        RETIRAR,
        PREPARACION,
        ENTREGA
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "cliente_id", nullable = false)
    private Integer clienteId;
    
    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;
    
    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoVenta estado;
    
    @Column(name = "tiene_diseno")
    private Boolean tieneDiseno;
    
    @Column(name = "tiene_tercerizado")
    private Boolean tieneTercerizado;
    
    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;
    
    @Column(name = "comentario", columnDefinition = "TEXT")
    private String comentario;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}

