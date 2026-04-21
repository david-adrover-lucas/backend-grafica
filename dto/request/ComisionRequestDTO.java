package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ComisionRequestDTO {
    @NotNull(message = "usuario obligatorio")
    @Positive(message = "el id debe ser mayor a 0")
    private Integer usuarioId;
    @NotNull(message = "producto obligatorio")
    @Positive(message = "el id debe ser mayor a 0")
    private Integer productoId;
    private BigDecimal montoFijo;
    @Positive(message = "el id debe ser mayor a 0") 
    @NotNull(message = "Cuenata obligatorio")
    private BigDecimal porcentaje;

    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public BigDecimal getMontoFijo() { return montoFijo; }
    public void setMontoFijo(BigDecimal montoFijo) { this.montoFijo = montoFijo; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }
}
