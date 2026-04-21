package com.drover.demo.backend.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class VentaDetalleRequestDTO {
    @NotNull(message = "El número no puede ser nulo")
    private Integer ventaId;
    @NotNull(message = "El número no puede ser nulo")
    private Integer productoId;
    @NotNull(message = "El número no puede ser nulo")
    @Positive(message = "El valor debe ser mayor a 0")
    private Integer cantidad;
    @NotNull(message = "El número no puede ser nulo")
    @Positive(message = "El valor debe ser mayor a 0")
    private BigDecimal alto;
    @NotNull(message = "El número no puede ser nulo")
    @Positive(message = "El valor debe ser mayor a 0")    
    private BigDecimal ancho;
    @NotNull(message = "El número no puede ser nulo")     
    @Positive(message = "El valor debe ser mayor a 0")
    private BigDecimal precio;   
    public Integer getVentaId() {
        return ventaId;
    }

     public void setVentaId(Integer ventaId) {
         this.ventaId = ventaId;
     }

     public Integer getProductoId() {
         return productoId;
     }

     public void setProductoId(Integer productoId) {
         this.productoId = productoId;
     }


    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getAlto() {
        return alto;
    }

    public void setAlto(BigDecimal alto) {
        this.alto = alto;
    }

    public BigDecimal getAncho() {
        return ancho;
    }

    public void setAncho(BigDecimal ancho) {
        this.ancho = ancho;
    }

      public BigDecimal getPrecio() {
          return precio;
      }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
