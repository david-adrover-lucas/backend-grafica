package com.drover.demo.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "proveedores")
public class Proveedor {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nombre",nullable = false, length = 150)
    private String nombre;
    @Column(name = "telefono",length = 30)
    private String telefono;
    @Column(name = "email",length = 150)
    private String email;
    @Column(name="red_social", length = 255)
    private String red_social;
    @Column(name = "tipo", nullable = false, length =20)
    private String tipo;
    @Column(name = "activo",nullable = false)
    private Boolean activo;
    public Proveedor() {
    }
    public Proveedor(Long id, String nombre, String telefono, String email, String red_social, String tipo,
         Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.red_social = red_social;
        this.tipo = tipo;
        this.activo = activo;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRed_social() {
        return red_social;
    }
    public void setRed_social(String red_social) {
        this.red_social = red_social;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }


}
