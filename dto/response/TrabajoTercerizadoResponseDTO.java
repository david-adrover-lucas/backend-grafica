package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;




public class TrabajoTercerizadoResponseDTO {
  
    private Integer id;
 
    private Integer empresaId;

    private Integer ventaId;

    private Integer insumoId;

    public Integer getVentaId() {
        return ventaId;
    }
    public void setVentaid(Integer ventaId) {
        this.ventaId = ventaId;
    }
    public Integer getInsumoId() {
        return insumoId;
    }
    public void setInsumoId(Integer insumoId) {
        this.insumoId = insumoId;
    }
    private String insumoNombre;

    private BigDecimal costo;

    private String estado;

    private LocalDateTime fechaEnvio;

    private LocalDateTime fechaRetiro;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getEmpresaId() {
        return empresaId;
    }
    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

    public String getInsumoNombre() {
        return insumoNombre;
    }
    public void setInsumoNombre(String insumoNombre) {
        this.insumoNombre = insumoNombre;
    }
    public BigDecimal getCosto() {
        return costo;
    }
    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    public LocalDateTime getFechaRetiro() {
        return fechaRetiro;
    }
    public void setFechaRetiro(LocalDateTime fechaRetiro) {
        this.fechaRetiro = fechaRetiro;
    }    
}
