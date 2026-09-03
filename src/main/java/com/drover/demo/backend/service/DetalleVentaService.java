package com.drover.demo.backend.service;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.DetalleVenta;
import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.entity.ProductoInsumo;
import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.repository.*;
import org.springframework.transaction.annotation.Transactional;
import java.math.RoundingMode;

@Service
public class DetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;
    private final ProductoRepository productoRepository;
    private final InsumoRepository insumoRepository;
    private final PreciosEscalaProductoRepository preciosEscalaRepository;
    private final ComisionesEscalaProductoRepository comisionesEscalaRepository;

    // Constructor completo para inyectar de forma segura todos los motores de consulta necesarios
    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository, 
                               ProductoRepository productoRepository,
                               InsumoRepository insumoRepository,
                               PreciosEscalaProductoRepository preciosEscalaRepository,
                               ComisionesEscalaProductoRepository comisionesEscalaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
        this.productoRepository = productoRepository;
        this.insumoRepository = insumoRepository;
        this.preciosEscalaRepository = preciosEscalaRepository;
        this.comisionesEscalaRepository = comisionesEscalaRepository;
    }
  
    @Transactional
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        // El renglón se valida, calcula sus históricos y subtotales comerciales,
        // pero NO toca el depósito todavía.
        DetalleVenta validado = validarYCalcularDetalle(detalleVenta);
        return detalleVentaRepository.save(validado);
    }


    @Transactional
    public DetalleVenta editar(Long id, DetalleVenta detalleVenta) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del renglón de venta es obligatorio para editar.");
        }
        DetalleVenta existente = detalleVentaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Renglón de venta no encontrado con el ID: " + id));

        // Nota: En una edición real de sistemas de stock se debería revertir el stock anterior antes de aplicar el nuevo.
        DetalleVenta datosNuevos = validarYCalcularDetalle(detalleVenta);

        existente.setProducto(datosNuevos.getProducto());
        existente.setCantidad(datosNuevos.getCantidad());
        existente.setAncho(datosNuevos.getAncho());
        existente.setAlto(datosNuevos.getAlto());
        existente.setCostoHistorico(datosNuevos.getCostoHistorico());
        existente.setMontoGananciaHistorico(datosNuevos.getMontoGananciaHistorico());
        existente.setMontoComisionHistorico(datosNuevos.getMontoComisionHistorico());
        existente.setPrecioUnitarioHistorico(datosNuevos.getPrecioUnitarioHistorico());
        existente.setSubtotal(datosNuevos.getSubtotal());

        return detalleVentaRepository.save(existente);
    }

    public List<DetalleVenta> listar() {
        return detalleVentaRepository.findAll();
    }

    // --- TUS MÉTODOS PRIVADOS DE DISEÑO LIMPIO Y MEJORADO ---

    private DetalleVenta ordenarYProcesarMatematica(DetalleVenta detalle, Producto productoBd) {
        String unidad = productoBd.getUnidadVenta().strip().toLowerCase();

        // 1. Validamos obligatoriedad de medidas físicas si el producto es un cartel (m2)
        if ("m2".equals(unidad)) {
            if (detalle.getAncho() == null || detalle.getAncho().compareTo(BigDecimal.ZERO) <= 0 ||
                detalle.getAlto() == null || detalle.getAlto().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Para productos por m2, el ancho y el alto son obligatorios y mayores a cero.");
            }
        }

        // 2. CONGELAMOS LOS VALORES HISTÓRICOS ACTUALES (Para cumplir la regla del PDF de congelar precios)
        detalle.setCostoHistorico(productoBd.getCostoActual());
        
        // 3. BUSCAMOS ESCALAS DINÁMICAS COMERCIALES
        // Miramos si tiene un porcentaje de ganancia por volumen (escala de m2, lineales o tazas)
        BigDecimal porcentajeGanancia = preciosEscalaRepository
            .encontrarPorcentajePorEscala(productoBd.getId(), detalle.getCantidad())
            .orElse(productoBd.getMontoGanancia()); // Si no hay escala, usa la ganancia base del catálogo
        
        detalle.setMontoGananciaHistorico(porcentajeGanancia);

        // Buscamos si hay una comisión por escala según el volumen vendido
        BigDecimal comisionDinámica = comisionesEscalaRepository
            .encontrarComisionPorRango(productoBd.getId(), detalle.getCantidad())
            .orElse(BigDecimal.ZERO); // Si no configuraste comisiones, queda en 0
            
        detalle.setMontoComisionHistorico(comisionDinámica);

        // 4. EJECUTAMOS TUS MÉTODOS PRIVADOS EN CADENA
        detalle.setPrecioUnitarioHistorico(calcularPrecioUnitario(detalle.getCostoHistorico(), detalle.getMontoGananciaHistorico()));
        detalle.setSubtotal(calcularSubtotal(detalle, unidad));

        return detalle;
    }

    private BigDecimal calcularPrecioUnitario(BigDecimal costo, BigDecimal porcentajeGanancia) {
        BigDecimal factor = porcentajeGanancia.divide(new BigDecimal("100"), 4, RoundingMode.HALF_UP).add(BigDecimal.ONE);
        return costo.multiply(factor).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calcularSubtotal(DetalleVenta detalle, String unidadVenta) {
        if ("m2".equals(unidadVenta)) {
            // Ecuación de superficie: Cantidad * (Ancho * Alto) * Precio Unitario por m2
            BigDecimal superficie = detalle.getAncho().multiply(detalle.getAlto());
            return detalle.getCantidad().multiply(superficie).multiply(detalle.getPrecioUnitarioHistorico()).setScale(2, RoundingMode.HALF_UP);
        }
        // Ecuación plana (unidades o lineales): Cantidad * Precio Unitario
        return detalle.getCantidad().multiply(detalle.getPrecioUnitarioHistorico()).setScale(2, RoundingMode.HALF_UP);
    }


    private void descontarInsumosDelStock(DetalleVenta detalle) {
        Producto productoReal = productoRepository.findById(detalle.getProducto().getId()).orElse(null);
        if (productoReal == null || productoReal.getInsumosComponentes() == null) return;

        String unidadVenta = productoReal.getUnidadVenta().strip().toLowerCase();
        
        // Multiplicador de escala: Si es m2, multiplicamos por la superficie del cartel, sino multiplicamos por la cantidad plana
        BigDecimal factorEscalaTotal = "m2".equals(unidadVenta) 
            ? detalle.getCantidad().multiply(detalle.getAncho().multiply(detalle.getAlto()))
            : detalle.getCantidad();

        for (ProductoInsumo recetaComponente : productoReal.getInsumosComponentes()) {
            Insumo insumoDeposito = recetaComponente.getInsumo();
            
            // Cantidad total a restar = (Cantidad que gasta la receta base * el factor de escala de la venta)
            BigDecimal cantidadAConsumir = recetaComponente.getCantidad().multiply(factorEscalaTotal);
            
            // Restamos del depósito
            BigDecimal nuevoStock = insumoDeposito.getStockActual().subtract(cantidadAConsumir);
            
            // Opcional: Podrías lanzar un aviso si nuevoStock es menor que stockMinimo, cumpliendo la regla de control
            if (nuevoStock.compareTo(BigDecimal.ZERO) < 0) {
                throw new RuntimeException("No hay suficiente stock en el depósito del insumo '" 
                    + insumoDeposito.getNombre() + "'. Stock actual: " + insumoDeposito.getStockActual());
            }

            insumoDeposito.setStockActual(nuevoStock);
            insumoRepository.save(insumoDeposito); // Impactamos el nuevo stock real en la BD de insumos
        }
    }

    private DetalleVenta validarYCalcularDetalle(DetalleVenta detalle) {
        if (detalle == null) {
            throw new IllegalArgumentException("El renglón del detalle no puede estar vacío.");
        }
        if (detalle.getProducto() == null || detalle.getProducto().getId() == null) {
            throw new IllegalArgumentException("Cada renglón debe especificar un producto válido.");
        }
        if (detalle.getCantidad() == null || detalle.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad vendida debe ser mayor a cero.");
        }

        Producto productoBd = productoRepository.findById(detalle.getProducto().getId())
            .orElseThrow(() -> new RuntimeException("El producto no existe en el catálogo maestro."));

        return ordenarYProcesarMatematica(detalle, productoBd);
    }
}
