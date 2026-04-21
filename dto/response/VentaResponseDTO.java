package com.drover.demo.backend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.drover.demo.backend.entity.Venta.EstadoVenta;



public class VentaResponseDTO {

    private Integer id;
    private Integer clienteId;
    private Integer usuarioId;
    private EstadoVenta estado;
    private BigDecimal total;
    private Boolean tieneDiseno;
    private Boolean tieneTercerizado;
    private LocalDate fechaEntrega;
    private String comentario;
    private LocalDateTime createdAt;
    public Boolean getTieneDiseno() {
        return tieneDiseno;
    }
    public void setTieneDiseno(Boolean tieneDiseno) {
        this.tieneDiseno = tieneDiseno;
    }
    public Boolean getTieneTercerizado() {
        return tieneTercerizado;
    }
    public void setTieneTercerizado(Boolean tieneTercerizado) {
        this.tieneTercerizado = tieneTercerizado;
    }
    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }
    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getClienteId() {
        return clienteId;
    }
    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
    public Integer getUsuarioId() {
        return usuarioId;
    }
    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }
    public EstadoVenta getEstado() {
        return estado;
    }
    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }

}