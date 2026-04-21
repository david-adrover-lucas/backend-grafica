package com.drover.demo.backend.entity;

import java.time.LocalDateTime;
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
@Table(name = "usuarios")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; 
    
    @Column(name = "nombre", length = 100)
    private String nombre;
    
    @Column(name = "usuario", unique = true, length = 50)
    private String usuario;
    
    @Column(name = "contrasena", length = 255)
    private String contrasena;
    
    @Column(name = "rol", length = 50)
    private String rol;
    
    @Column(name = "sueldo_mensual", precision = 10, scale = 2)
    private BigDecimal sueldoMensual;
    
    @Column(name = "activo")
    private Boolean activo;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

