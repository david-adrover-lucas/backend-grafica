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
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "presupuestos")
public class Presupuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nro_presupuesto", nullable = false, unique = true, length = 30)
    private String nroPresupuesto; // Cambiado a camelCase

    // CORRECCIÓN 1: Relación real con el Cliente (Puede ser NULL si es un revendedor)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cliente_id", nullable = true)
    private Cliente cliente;

    // CORRECCIÓN 2: Relación real con el Revendedor (Puede ser NULL si es un cliente común)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "revendedor_id", nullable = true)
    private Revendedor revendedor;

    // CORRECCIÓN 3: Relación real con el Empleado/Vendedor que armó la cotización
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "responsable_id", nullable = false)
    private Persona responsable;

    @Column(name = "fecha", nullable = false) 
    private LocalDateTime fecha;

    @Column(name = "estado", nullable = false, length = 20) 
    private String estado; // Ejemplo: "pendiente", "aceptado", "rechazado"

    // CORRECCIÓN DECIMAL: Precisión contable exacta para montos totales (DECIMAL 14,2)
    @Column(name = "monto_total", nullable = false, precision = 14, scale = 2) 
    private BigDecimal montoTotal;

    @Column(name = "observaciones", columnDefinition = "TEXT") 
    private String observaciones;

    // MEJORA EXCLUSIVA: Permite adjuntar, calcular y guardar los renglones juntos automáticamente
    @OneToMany(mappedBy = "presupuesto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<DetallePresupuestos> detalles = new ArrayList<>();

    // Constructor vacío obligatorio para JPA
    public Presupuesto() {}

    // Constructor completo actualizado con objetos relacionales
    public Presupuesto(Long id, String nroPresupuesto, Cliente cliente, Revendedor revendedor, Persona responsable,
                       LocalDateTime fecha, String estado, BigDecimal montoTotal, String observaciones, 
                       List<DetallePresupuestos> detalles) {
        this.id = id;
        this.nroPresupuesto = nroPresupuesto;
        this.cliente = cliente;
        this.revendedor = revendedor;
        this.responsable = responsable;
        this.fecha = fecha;
        this.estado = estado;
        this.montoTotal = montoTotal;
        this.observaciones = observaciones;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
    }

    // --- GETTERS Y SETTERS ACTUALIZADOS CON ENFOQUE DE OBJETOS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNroPresupuesto() { return nroPresupuesto; }
    public void setNroPresupuesto(String nroPresupuesto) { this.nroPresupuesto = nroPresupuesto; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Revendedor getRevendedor() { return revendedor; }
    public void setRevendedor(Revendedor revendedor) { this.revendedor = revendedor; }

    public Persona getResponsable() { return responsable; }
    public void setResponsable(Persona responsable) { this.responsable = responsable; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getMontoTotal() { return montoTotal; }
    public void setMontoTotal(BigDecimal montoTotal) { this.montoTotal = montoTotal; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }

    public List<DetallePresupuestos> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePresupuestos> detalles) { this.detalles = detalles; }
}

