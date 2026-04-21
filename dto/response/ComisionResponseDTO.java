package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;

public class ComisionResponseDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer productoId;
    private BigDecimal montoFijo;
    private BigDecimal porcentaje;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Integer usuarioId) { this.usuarioId = usuarioId; }
    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }
    public BigDecimal getMontoFijo() { return montoFijo; }
    public void setMontoFijo(BigDecimal montoFijo) { this.montoFijo = montoFijo; }
    public BigDecimal getPorcentaje() { return porcentaje; }
    public void setPorcentaje(BigDecimal porcentaje) { this.porcentaje = porcentaje; }
}