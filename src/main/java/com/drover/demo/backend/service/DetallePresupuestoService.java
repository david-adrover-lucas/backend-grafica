package com.drover.demo.backend.service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.DetallePresupuestos;
import com.drover.demo.backend.repository.DetallePresupuestosRepository;
import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.repository.ProductoRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DetallePresupuestoService {

    private final DetallePresupuestosRepository detallePresupuestoRepository;
    private final ProductoRepository productoRepository; // Inyectado para inspeccionar el catálogo real

    public DetallePresupuestoService(DetallePresupuestosRepository detallePresupuestoRepository, 
                                     ProductoRepository productoRepository) {
        this.detallePresupuestoRepository = detallePresupuestoRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public DetallePresupuestos guardar(DetallePresupuestos detallePresupuesto) {
        DetallePresupuestos validado = validarDetallePresupuestos(detallePresupuesto);
        return detallePresupuestoRepository.save(validado);
    }

    @Transactional
    public DetallePresupuestos editar(Long id, DetallePresupuestos detallePresupuesto) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del renglón no puede ser nulo para editar.");
        }
        DetallePresupuestos existente = detallePresupuestoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Renglón de detalle no encontrado con el ID: " + id));

        DetallePresupuestos datosNuevos = validarDetallePresupuestos(detallePresupuesto);

        existente.setProducto(datosNuevos.getProducto());
        existente.setCantidad(datosNuevos.getCantidad());
        existente.setAncho(datosNuevos.getAncho());
        existente.setAlto(datosNuevos.getAlto());
        existente.setCostoUnitarioHistorico(datosNuevos.getCostoUnitarioHistorico());
        existente.setMontoGananciaUnitario(datosNuevos.getMontoGananciaUnitario());
        existente.setPrecioUnitario(datosNuevos.getPrecioUnitario());
        existente.setSubtotal(datosNuevos.getSubtotal());

        return detallePresupuestoRepository.save(existente);
    }

    public List<DetallePresupuestos> listar() {
        return detallePresupuestoRepository.findAll();
    }
    
    // --- TUS MÉTODOS PRIVADOS DE DISEÑO LIMPIO ---

    private DetallePresupuestos validarDetallePresupuestos(DetallePresupuestos detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El renglón del detalle no puede estar vacío.");
        }
        if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
            throw new IllegalArgumentException("Cada renglón debe especificar un producto válido.");
        }
        if (detalle.getCantidad() == null || detalle.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad solicitada debe ser mayor a cero.");
        }

        // Buscamos el producto en la BD para conocer su unidad de venta y costos reales actuales
        Producto productoBd = productoRepository.findById(detalle.getProducto().getId())
            .orElseThrow(() -> new RuntimeException("El producto no existe en el catálogo."));

        String unidad = productoBd.getUnidadVenta().strip().toLowerCase();

        // DETECCIÓN AUTOMÁTICA: Si es m2, exigimos alto y ancho de forma obligatoria
        if ("m2".equals(unidad)) {
            if (detalle.getAncho() == null || detalle.getAncho().compareTo(BigDecimal.ZERO) <= 0 ||
                detalle.getAlto() == null || detalle.getAlto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Para productos vendidos por m2, el ancho y el alto son obligatorios y mayores a cero.");
            }
        }

        // Ejecutamos tus métodos privados en cadena secuencial
        detalle.setCostoUnitarioHistorico(calcularCosto(productoBd));
        detalle.setMontoGananciaUnitario(calcularGanancia(productoBd));
        detalle.setPrecioUnitario(calcularPrecioUnitario(detalle.getCostoUnitarioHistorico(), detalle.getMontoGananciaUnitario()));
        detalle.setSubtotal(calcularSubtotal(detalle, unidad));

        return detalle;
    }

    private BigDecimal calcularCosto(Producto producto) {
        // Congelamos el costo actual que tiene el insumo en el depósito hoy
        return producto.getCostoActual();
    }

    private BigDecimal calcularGanancia(Producto producto) {
        // Congelamos el porcentaje de ganancia base configurado en el catálogo
        return producto.getMontoGanancia();
    }

    private BigDecimal calcularPrecioUnitario(BigDecimal costo, BigDecimal gananciaPorcentaje) {
        // Fórmula idéntica a la de productos: Costo * (1 + Porcentaje / 100)
        BigDecimal factor = gananciaPorcentaje.divide(new BigDecimal("100")).add(BigDecimal.ONE);
        return costo.multiply(factor);
    }

    private BigDecimal calcularSubtotal(DetallePresupuestos detalle, String unidadVenta) {
        if ("m2".equals(unidadVenta)) {
            // Ecuación de superficie: Cantidad * (Ancho * Alto) * PrecioUnitario
            BigDecimal superficie = detalle.getAncho().multiply(detalle.getAlto());
            return detalle.getCantidad().multiply(superficie).multiply(detalle.getPrecioUnitario());
        }
        
        // Ecuación plana (para unidad o lineal): Cantidad * PrecioUnitario
        return detalle.getCantidad().multiply(detalle.getPrecioUnitario());
    }
}
