package com.drover.demo.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {
   
    @Id
    @Column(name="id")
    private Long id;
   
    @Column(name = "numero", nullable = false, length = 50)
    private String numero;
   
    @Column(name = "departamento", nullable = false, length = 100)
    private String departamento;
   
    public Cliente() {
    }
    public Cliente(Long id, String numero, String departamento) {
        this.id = id;
        this.numero = numero;
        this.departamento = departamento;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getDepartamento() {
        return departamento;
    }
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
