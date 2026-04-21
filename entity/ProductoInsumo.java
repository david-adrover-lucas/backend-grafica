package com.drover.demo.backend.entity;

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
@Table(name = "producto_insumo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoInsumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   
    @Column(name = "producto_id")
    private Integer productoId;

    @Column(name = "insumo_id")
    private Integer insumoId;

    // Cantidad usada
    @Column(name = "cantidad")
    private Integer cantidad;



}

