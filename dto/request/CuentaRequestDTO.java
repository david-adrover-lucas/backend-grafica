package com.drover.demo.backend.dto.request;


import jakarta.validation.constraints.NotNull;


public class CuentaRequestDTO {
    @NotNull(message = "nombre obligatorio")
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



}
