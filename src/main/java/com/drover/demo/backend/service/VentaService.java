package com.drover.demo.backend.service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.Venta;
import com.drover.demo.backend.repository.InsumoRepository;
import com.drover.demo.backend.repository.ProductoInsumoRepository;
import com.drover.demo.backend.repository.ProductoRepository;
import com.drover.demo.backend.repository.VentaRepository;
import com.drover.demo.backend.entity.DetalleVenta;
import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.entity.ProductoInsumo;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;
    private final  InsumoRepository insumoRepository;
    private final List<String> estadosVenta = List.of("diseñar", "confirmar", "retirar", "entrega", "entregado", "posponer");
    private final List<String> pagosEstados = List.of("pendiente", "señeado", "pagado", "deuda");
 
    public VentaService(VentaRepository ventaRepository, ProductoRepository productoRepository,
            InsumoRepository insumoRepository) {
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.insumoRepository = insumoRepository;
    }


    @Transactional
    public Venta guardar(Venta venta) {
        Venta ventaLimpia = validarVenta(venta);
        
        if (ventaLimpia.getNroVenta() == null || ventaLimpia.getNroVenta().strip().isEmpty()) {
            ventaLimpia.setNroVenta("VTA-" + System.currentTimeMillis());
        }

        // 🌟 REGLA SOLICITADA: Evaluamos la situación de la caja antes de tocar el depósito
        String estadoPago = ventaLimpia.getEstadoPago().strip().toLowerCase();
        
        if ("señeado".equals(estadoPago) || "pagado".equals(estadoPago)) {
            // Si el cliente dejó dinero, congelamos los materiales y descontamos stock
            descontarStockDeLaVenta(ventaLimpia.getDetalles());
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

        // Validamos y normalizamos el estado del taller (Producción)
        if (venta.getEstadoVenta() == null || venta.getEstadoVenta().strip().isEmpty()) {
            venta.setEstadoVenta("confirmar"); // Estado inicial natural por defecto
        } else {
            venta.setEstadoVenta(venta.getEstadoVenta().strip().toLowerCase());
            if (!estadosVenta.contains(venta.getEstadoVenta())) {
                throw new IllegalArgumentException("Estado de taller '" + venta.getEstadoVenta() + "' no permitido. Use: " + estadosVenta);
            }
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
    // --- MOTOR DE STOCK EN VENTAS (MÉTODO PRIVADO AUXILIAR) ---

    private void descontarStockDeLaVenta(List<DetalleVenta> detalles) {
        for (DetalleVenta detalle : detalles) {
            // Buscamos el producto con su receta completa cargada desde la BD
            Producto productoReal = productoRepository.findById(detalle.getProducto().getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado para descontar stock."));

            String unidadVenta = productoReal.getUnidadVenta().strip().toLowerCase();
            
            // Calculamos el multiplicador industrial (Superficie si es m2, cantidad plana si no)
            BigDecimal factorEscalaTotal = "m2".equals(unidadVenta) 
                ? detalle.getCantidad().multiply(detalle.getAncho().multiply(detalle.getAlto()))
                : detalle.getCantidad();

            // Recorremos la receta de insumos del artículo
            for (ProductoInsumo recetaComponente : productoReal.getInsumosComponentes()) {
                Insumo insumoDeposito = recetaComponente.getInsumo();
                
                // Cantidad exacta de material consumido para este trabajo
                BigDecimal cantidadAConsumir = recetaComponente.getCantidad().multiply(factorEscalaTotal);
                
                // Restamos existencias
                BigDecimal nuevoStock = insumoDeposito.getStockActual().subtract(cantidadAConsumir);
                
                // Freno de mano de seguridad: si no alcanzan los materiales, cancelamos la operación
                if (nuevoStock.compareTo(BigDecimal.ZERO) < 0) {
                    throw new RuntimeException("No hay suficiente stock en el depósito del insumo '" 
                        + insumoDeposito.getNombre() + "' para confirmar este trabajo. Stock actual: " 
                        + insumoDeposito.getStockActual() + ". Requerido: " + cantidadAConsumir);
                }

                insumoDeposito.setStockActual(nuevoStock);
                insumoRepository.save(insumoDeposito); // Guardamos el inventario actualizado
            }
        }
    }    
}

