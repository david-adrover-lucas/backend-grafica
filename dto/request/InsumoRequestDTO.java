package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import com.drover.demo.backend.entity.Insumo.TipoInsumos;

import jakarta.validation.constraints.NotNull;

public class InsumoRequestDTO {
    @NotNull(message = "El nombre es obligatorio")    
    private String nombre;
    @NotNull(message = "El tipo es obligatorio")    
    private TipoInsumos tipo;
    @NotNull(message = "El costo debe ser mayor a 0")    
    private BigDecimal costoUnitario;
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public TipoInsumos getTipo() {
        return tipo;
    }
    public void setTipo(TipoInsumos tipo) {
        this.tipo = tipo;
    }
    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }
    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }   
}
