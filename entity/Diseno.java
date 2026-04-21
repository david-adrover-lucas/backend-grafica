package com.drover.demo.backend.entity;

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
@Table(name = "disenos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diseno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "reutilizable")
    private Boolean reutilizable;
    
    @Column(name = "nombre", length = 100)
    private String nombre;
    
    @Column(name = "cliente_id")
    private Integer clienteId;
    
    @Column(name = "venta_id")
    private Integer ventaId;
    
    @Column(name = "categoria", length = 50)
    private String categoria;
    
    @Column(name = "ruta_archivo", length = 255)
    private String rutaArchivo;
    
    @Column(name = "ruta_preview", length = 255)
    private String rutaPreview;
    @Column(name = "etiquetas", length = 255)
    private String etiquetas;

    @Column(name = "contador_uso")
    private Integer contadorUso;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

