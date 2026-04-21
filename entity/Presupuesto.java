package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;


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
@Table(name = "presupuestos")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Presupuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "codigo")
    private String codigo ;    
    @Column(name = "cliente_id")
    private Integer clienteId;
    @Column(name = "total")
    private BigDecimal total ;      
    @Column(name = "created_at")    
    private LocalDateTime createdAt;
   
    
}

