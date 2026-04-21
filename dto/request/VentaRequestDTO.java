package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.drover.demo.backend.entity.Venta.EstadoVenta;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class VentaRequestDTO {

    @NotNull(message = "El número no puede ser nulo")
    private Integer clienteId;
    @NotNull(message = "El número no puede ser nulo")
    private Integer usuarioId;
    @NotNull(message = "El campo no puede ser nulo")
    @Positive(message = "El valor debe ser mayor a 0")
    private BigDecimal total;
    @NotNull(message = "El campo no puede ser nulo")
    private EstadoVenta estado;
    @NonNull
    private String comentario;
    @NonNull
    private LocalDate fechaEntrega;
    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public EstadoVenta getEstado() {
        return estado;
    }

    public void setEstado(EstadoVenta estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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


}
