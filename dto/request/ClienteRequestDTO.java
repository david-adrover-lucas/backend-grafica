package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ClienteRequestDTO {

    @NotBlank(message = "el nombre es obligatorio")
    private String nombre;
    @NotNull
    private String telefono;
    @NotNull
    private String email;
    @NotNull    
    private String direccion;
    @NotNull
    private BigDecimal deuda;

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
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public BigDecimal getDeuda() {
        return deuda;
    }
    public void setDeuda(BigDecimal deuda) {
        this.deuda = deuda;
    }

}