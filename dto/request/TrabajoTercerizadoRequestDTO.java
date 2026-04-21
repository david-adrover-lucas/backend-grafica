package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TrabajoTercerizadoRequestDTO {
    @NotNull  (message = " nombre del insumo obligatorio") 
    private String insumoNombre;
    @NotNull (message = " costo obligatorio") 
    @Positive( message = "el numero debe ser mayor a 0") 
    private BigDecimal costo;
    @NotNull   (message = " estado obligatorio") 
    private String estado;
    @NotNull  (message = " fecha de envio es  obligatorio")  
    private LocalDateTime fechaEnvio;
    @NotNull (message = " fecha de retiros es  obligatorio") 
    private LocalDateTime fechaRetiro;
    @NotNull (message = " venta id es  obligatorio")  
    @Positive( message = "el numero debe ser mayor a 0") 
    private Integer ventaId;
    @NotNull (message = " insumo id es  obligatorio")  
    @Positive( message = "el numero debe ser mayor a 0") 
    private Integer insumoId;
    @NotNull (message = " empresa id es obligatorio") 
    @Positive( message = "el numero debe ser mayor a 0")     
    private Integer empresaId;
    public Integer getEmpresaId() {
        return empresaId;
    }
    public void setEmpresaId(Integer empresaId) {
        this.empresaId = empresaId;
    }

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
