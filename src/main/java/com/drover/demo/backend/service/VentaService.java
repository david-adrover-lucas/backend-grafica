package com.drover.demo.backend.service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.repository.InsumoRepository;
import com.drover.demo.backend.repository.MovimientoStockRepository;
import com.drover.demo.backend.repository.ProductoRepository;
import com.drover.demo.backend.repository.VentaRepository;
import com.drover.demo.backend.entity.DetalleVenta;
import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.entity.MovimientoStock;
import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.entity.ProductoInsumo;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final InsumoRepository insumoRepository;
    private final MovimientoStockRepository movimientoStockRepository;
    private final List<String> estadosVenta = List.of("diseñar", "confirmar", "retirar a tercerizado", "entrega", "entregado", "posponer");
    private final List<String> pagosEstados = List.of("pendiente", "señeado", "pagado", "deuda");
 
    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository,
            InsumoRepository insumoRepository, MovimientoStockRepository movimientoStockRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.insumoRepository = insumoRepository;
        this.movimientoStockRepository = movimientoStockRepository;
    }


    @Transactional
    public Venta guardar(Venta venta) {
        Venta ventaLimpia = validarVenta(venta);
        
        if (ventaLimpia.getNroVenta() == null || ventaLimpia.getNroVenta().strip().isEmpty()) {
            ventaLimpia.setNroVenta("VTA-" + System.currentTimeMillis());
        }

        return ventaRepository.save(ventaLimpia);
    }

    @Transactional
    public Venta editar(Venta venta) {
        if (venta == null || venta.getId() == null) {
            throw new IllegalArgumentException("Debes proveer una venta con un ID válido para editar.");
        }

        Venta ventaExistente = ventaRepository.findById(venta.getId())
            .orElseThrow(() -> new RuntimeException("Comprobante de venta no encontrado con el ID: " + venta.getId()));

        validarVentaEditable(ventaExistente);

        Venta datosNuevosLimpios = validarVenta(venta);

        // Traspasamos las propiedades permitidas a la fila administrada por Hibernate
        ventaExistente.setCliente(datosNuevosLimpios.getCliente());
        ventaExistente.setRevendedor(datosNuevosLimpios.getRevendedor());
        ventaExistente.setResponsable(datosNuevosLimpios.getResponsable());
        ventaExistente.setFechaVenta(datosNuevosLimpios.getFechaVenta());
        ventaExistente.setEstadoVenta(datosNuevosLimpios.getEstadoVenta());
        ventaExistente.setEstadoPago(datosNuevosLimpios.getEstadoPago());
        ventaExistente.setObservaciones(datosNuevosLimpios.getObservaciones());
        ventaExistente.setMontoTotal(datosNuevosLimpios.getMontoTotal());

        // Actualizamos los renglones de los artículos en cascada
        ventaExistente.getDetalles().clear();
        if (datosNuevosLimpios.getDetalles() != null) {
            for (DetalleVenta nuevoDetalle : datosNuevosLimpios.getDetalles()) {
                ventaExistente.getDetalles().add(nuevoDetalle);
                nuevoDetalle.setVenta(ventaExistente); // Mantenemos el vínculo bidireccional
            }
        }

        return ventaRepository.save(ventaExistente);
    }

    public List<Venta> listar() {
        return ventaRepository.findAll();
    }

    public List<Venta> listarEstadoVenta(String estado) {
        if (estado == null || estado.strip().isEmpty()) {
            throw new IllegalArgumentException("El parámetro estado de venta no puede estar vacío.");
        }
        String filtro = estado.strip().toLowerCase();
        if (!estadosVenta.contains(filtro)) {
            throw new IllegalArgumentException("Estado de taller inválido. Opciones permitidas: " + estadosVenta);
        }
        return ventaRepository.findByEstadoVenta(filtro);
    }

    public List<Venta> listarEstadoPago(String estadoPago) {
        if (estadoPago == null || estadoPago.strip().isEmpty()) {
            throw new IllegalArgumentException("El parámetro estado de pago no puede estar vacío.");
        }
        String filtro = estadoPago.strip().toLowerCase();
        if (!pagosEstados.contains(filtro)) {
            throw new IllegalArgumentException("Estado de caja inválido. Opciones permitidas: " + pagosEstados);
        }
        return ventaRepository.findByEstadoPago(filtro);
    }

    public List<Venta> listarPorFecha(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("El rango de fechas provisto es inválido.");
        }
        return ventaRepository.findByFechaVentaBetween(desde, hasta);
    }

    public List<Venta> listarPorCliente(Long clienteId) {
        if (clienteId == null) {
            throw new IllegalArgumentException("El ID del cliente es estrictamente obligatorio para consultar su historial de ventas.");
        }
        return ventaRepository.findByClienteId(clienteId);
    }
 
    public List<Venta> listarPorRevendedor(Long revendedorId) {
        if (revendedorId == null) {
            throw new IllegalArgumentException("El ID del revendedor es estrictamente obligatorio para consultar su historial de ventas.");
        }
        return ventaRepository.findByRevendedorId(revendedorId);
    }

    @Transactional
    public Venta cambiarEstado(Long id, String nuevoEstado) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la venta es obligatorio para cambiar el estado.");
        }

        String estadoLimpio = validarEstadoVenta(nuevoEstado);

        Venta venta = ventaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Comprobante de venta no encontrado con el ID: " + id));

        if ("entrega".equals(estadoLimpio) && !Boolean.TRUE.equals(venta.getStockDescontado())) {
            descontarStockDeLaVenta(venta);
            venta.setStockDescontado(true);
        }

        venta.setEstadoVenta(estadoLimpio);
        return ventaRepository.save(venta);
    }


    // --- MÉTODOS PRIVADOS ---

    private Venta validarVenta(Venta venta) {
        if (venta == null) {
            throw new IllegalArgumentException("La consulta de venta está vacía.");
        }

        // Regla comercial estricta: Exclusión mutua de asignación de cliente
        boolean tieneCliente = (venta.getCliente() != null && venta.getCliente().getId() != null);
        boolean tieneRevendedor = (venta.getRevendedor() != null && venta.getRevendedor().getId() != null);

        if (!tieneCliente && !tieneRevendedor) {
            throw new IllegalArgumentException("La venta debe estar obligatoriamente asignada a un Cliente o a un Revendedor.");
        }
        if (tieneCliente && tieneRevendedor) {
            throw new IllegalArgumentException("Conflicto comercial: El comprobante no puede registrar un Cliente y un Revendedor a la vez.");
        }

        if (venta.getResponsable() == null || venta.getResponsable().getId() == null) {
            throw new IllegalArgumentException("Se requiere un usuario responsable (Vendedor/Empleado) para emitir la venta.");
        }

        if (venta.getFechaVenta() == null) {
            venta.setFechaVenta(LocalDateTime.now()); // Registramos el momento de la caja actual por defecto
        }

        if (venta.getStockDescontado() == null) {
            venta.setStockDescontado(false);
        }

        // Validamos y normalizamos el estado del taller (Producción)
        if (venta.getEstadoVenta() == null || venta.getEstadoVenta().strip().isEmpty()) {
            venta.setEstadoVenta("confirmar"); // Estado inicial natural por defecto
        } else {
            venta.setEstadoVenta(validarEstadoVenta(venta.getEstadoVenta()));
        }

        // Validamos y normalizamos la situación de caja
        if (venta.getEstadoPago() == null || venta.getEstadoPago().strip().isEmpty()) {
            venta.setEstadoPago("pendiente");
        } else {
            venta.setEstadoPago(venta.getEstadoPago().strip().toLowerCase());
            if (!pagosEstados.contains(venta.getEstadoPago())) {
                throw new IllegalArgumentException("Estado de pago '" + venta.getEstadoPago() + "' no permitido. Use: " + pagosEstados);
            }
        }

        // Validamos presencia obligatoria de los artículos comprados (Detalle)
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("No se puede registrar una venta vacía sin artículos en el detalle.");
        }

        // Sincronizamos las relaciones bidireccionales y controlamos los subtotales de los renglones
        for (DetalleVenta detalle : venta.getDetalles()) {
            if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
                throw new IllegalArgumentException("Cada renglón del detalle debe apuntar a un producto válido.");
            }
            if (detalle.getCantidad() == null || detalle.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("La cantidad vendida de cada artículo debe ser mayor a cero.");
            }
            if (detalle.getSubtotal() == null || detalle.getSubtotal().compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("El subtotal consolidado del renglón es obligatorio.");
            }
            
            // Enlazamos el renglón de forma estricta con esta venta madre
            detalle.setVenta(venta);
        }

        // 🌟 DESACOPLAMIENTO FINANCIERO: Invocamos el totalizador plano
        BigDecimal totalCalculado = calcularMontoTotal(venta.getDetalles());
        venta.setMontoTotal(totalCalculado);

        return venta;
    }

 
    private BigDecimal calcularMontoTotal(List<DetalleVenta> detalles) {
        BigDecimal acumulador = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            acumulador = acumulador.add(detalle.getSubtotal());
        }
        return acumulador;
    }

    private String validarEstadoVenta(String estado) {
        if (estado == null || estado.strip().isEmpty()) {
            throw new IllegalArgumentException("El estado de venta es obligatorio.");
        }

        String estadoLimpio = estado.strip().toLowerCase();
        if (!estadosVenta.contains(estadoLimpio)) {
            throw new IllegalArgumentException("Estado de taller '" + estadoLimpio + "' no permitido. Use: " + estadosVenta);
        }

        return estadoLimpio;
    }

    private void validarVentaEditable(Venta venta) {
        if (Boolean.TRUE.equals(venta.getStockDescontado())) {
            throw new IllegalStateException("La venta ya desconto stock y no puede editarse desde la edicion normal.");
        }

        String estadoPago = venta.getEstadoPago() != null ? venta.getEstadoPago().strip().toLowerCase() : "";
        if ("señeado".equals(estadoPago) || "pagado".equals(estadoPago)) {
            throw new IllegalStateException("La venta ya tiene pagos registrados y no puede editarse.");
        }
    }

    // --- MOTOR DE STOCK EN VENTAS (MÉTODO PRIVADO AUXILIAR) ---

    private void descontarStockDeLaVenta(Venta venta) {
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto productoReal = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado para descontar stock."));

            String unidadVenta = productoReal.getUnidadVenta().strip().toLowerCase();
            BigDecimal factorEscalaTotal = calcularFactorConsumo(detalle, unidadVenta);

            for (ProductoInsumo recetaComponente : productoReal.getInsumosComponentes()) {
                Insumo insumoDeposito = insumoRepository.findById(recetaComponente.getInsumo().getId())
                    .orElseThrow(() -> new RuntimeException("Insumo no encontrado para descontar stock: "
                        + recetaComponente.getInsumo().getId()));

                BigDecimal cantidadAConsumir = recetaComponente.getCantidad().multiply(factorEscalaTotal);
                BigDecimal stockAnterior = insumoDeposito.getStockActual();
                BigDecimal stockPosterior = stockAnterior.subtract(cantidadAConsumir);

                if (stockPosterior.compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("No hay suficiente stock en el depósito del insumo '"
                        + insumoDeposito.getNombre() + "' para confirmar este trabajo. Stock actual: "
                        + stockAnterior + ". Requerido: " + cantidadAConsumir);
                }

                insumoDeposito.setStockActual(stockPosterior);
                insumoRepository.save(insumoDeposito);

                movimientoStockRepository.save(crearMovimientoSalidaVenta(
                    insumoDeposito,
                    venta,
                    detalle,
                    cantidadAConsumir,
                    insumoDeposito.getCostoUnitario(),
                    stockAnterior,
                    stockPosterior,
                    "Salida automatica por venta " + venta.getNroVenta()
                ));
            }
        }
    }

    private BigDecimal calcularFactorConsumo(DetalleVenta detalle, String unidadVenta) {
        if ("m2".equals(unidadVenta)) {
            if (detalle.getAncho() == null || detalle.getAlto() == null ||
                detalle.getAncho().compareTo(BigDecimal.ZERO) <= 0 ||
                detalle.getAlto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Para descontar stock de productos por m2, ancho y alto son obligatorios y mayores a cero.");
            }
            return detalle.getCantidad().multiply(detalle.getAncho().multiply(detalle.getAlto()));
        }
        return detalle.getCantidad();
    }

    private MovimientoStock crearMovimientoSalidaVenta(Insumo insumo, Venta venta, DetalleVenta detalle,
                                                       BigDecimal cantidad, BigDecimal costoUnitario,
                                                       BigDecimal stockAnterior, BigDecimal stockPosterior,
                                                       String observaciones) {
        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setInsumo(insumo);
        movimiento.setVenta(venta);
        movimiento.setDetalleVenta(detalle);
        movimiento.setTipo("salida_venta");
        movimiento.setCantidad(cantidad);
        movimiento.setCostoUnitario(costoUnitario);
        movimiento.setCostoTotal(cantidad.multiply(costoUnitario));
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockPosterior(stockPosterior);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setObservaciones(observaciones);
        return movimiento;
    }
}
