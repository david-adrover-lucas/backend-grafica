package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;

import com.drover.demo.backend.entity.Insumo.TipoInsumos;



public class InsumoResponseDTO {
    private Integer id;  

    private String nombre;
    private TipoInsumos tipo;
    private BigDecimal costoUnitario;
    private Integer empresaId;
    private Boolean activo;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
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
    public Integer getEmpresaId() {
        return empresaId;
    }
    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}
