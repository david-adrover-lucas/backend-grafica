package com.drover.demo.backend.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta { 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro_venta", nullable = false, unique = true, length = 30)
    private String nroVenta; // Formato camelCase

    // Relación real con el Cliente (Puede ser NULL si es un revendedor)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    // Relación real con el Revendedor (Puede ser NULL si es un cliente común)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "revendedor_id", nullable = true)
    private Revendedor revendedor;

    // Relación real con el Empleado/Vendedor responsable que cerró la venta
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsable_id", nullable = false)
    private Persona responsable;

    @Column(name = "fecha_venta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(name = "estado_venta", nullable = false, length = 30)
    private String estadoVenta; // Ej: "pendiente", "produccion", "finalizado", "entregado"

    @Column(name = "estado_pago", nullable = false, length = 20)
    private String estadoPago; // Ej: "pago_parcial", "total", "deuda"

    @Column(name = "stock_descontado")
    private Boolean stockDescontado = false;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presupuesto_id", unique = true)
    @JsonIgnore
    private Presupuesto presupuesto;

    // Mapeo contable exacto DECIMAL(14,2) según tu PDF
    @Column(name = "monto_total", nullable = false, precision = 14, scale = 2)
    private BigDecimal montoTotal;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    // Conexión relacional para guardar y editar los renglones de la venta en cascada
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetalleVenta> detalles = new ArrayList<>();

    // Constructor vacío obligatorio para JPA
    public Venta() {}

    // Constructor completo actualizado con objetos relacionales
    public Venta(Long id, String nroVenta, Cliente cliente, Revendedor revendedor, Persona responsable,
                 LocalDateTime fechaVenta, String estadoVenta, String estadoPago, BigDecimal montoTotal,
                 String observaciones, List<DetalleVenta> detalles) {
        this.id = id;
        this.nroVenta = nroVenta;
        this.cliente = cliente;
        this.revendedor = revendedor;
        this.responsable = responsable;
        this.fechaVenta = fechaVenta;
        this.estadoVenta = estadoVenta;
        this.estadoPago = estadoPago;
        this.montoTotal = montoTotal;
        this.observaciones = observaciones;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    // --- GETTERS Y SETTERS CORREGIDOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNroVenta() { return nroVenta; }
    public void setNroVenta(String nroVenta) { this.nroVenta = nroVenta; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Revendedor getRevendedor() { return revendedor; }
    public void setRevendedor(Revendedor revendedor) { this.revendedor = revendedor; }

    public Persona getResponsable() { return responsable; }
    public void setResponsable(Persona responsable) { this.responsable = responsable; }

    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public void setFechaVenta(LocalDateTime fechaVenta) { this.fechaVenta = fechaVenta; }

    public String getEstadoVenta() { return estadoVenta; }
    public void setEstadoVenta(String estadoVenta) { this.estadoVenta = estadoVenta; }

    public String getEstadoPago() { return estadoPago; }
    public void setEstadoPago(String estadoPago) { this.estadoPago = estadoPago; }

    public Boolean getStockDescontado() { return stockDescontado; }
    public void setStockDescontado(Boolean stockDescontado) { this.stockDescontado = stockDescontado; }

    public Presupuesto getPresupuesto() { return presupuesto; }
    public void setPresupuesto(Presupuesto presupuesto) { this.presupuesto = presupuesto; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public List<DetalleVenta> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleVenta> detalles) { this.detalles = detalles; }
}

