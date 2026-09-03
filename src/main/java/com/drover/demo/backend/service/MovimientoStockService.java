package com.drover.demo.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.drover.demo.backend.entity.MovimientoStock;
import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.repository.MovimientoStockRepository;
import com.drover.demo.backend.repository.InsumoRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class MovimientoStockService {

    private final MovimientoStockRepository movimientoStockRepository;
    private final InsumoRepository insumoRepository;

    private final List<String> tiposPermitidos = List.of("entrada_compra", "salida_venta", "ajuste_manual", "perdida");

    public MovimientoStockService(MovimientoStockRepository movimientoStockRepository, InsumoRepository insumoRepository) {
        this.movimientoStockRepository = movimientoStockRepository;
        this.insumoRepository = insumoRepository;
    }

    @Transactional
    public void registrarMovimiento(MovimientoStock movimiento) {
        // 1. Invocamos al método privado de validación y cálculo interno de saldos
        MovimientoStock limpio = validarMovimientoStock(movimiento);
        
        // 2. Extraemos el insumo modificado por la validación para actualizar la tabla maestra
        Insumo insumoBd = limpio.getInsumo();
        insumoBd.setStockActual(limpio.getStockPosterior());
        
        // 3. Guardamos físicamente ambas filas en sus respectivas tablas de SQL en una sola transacción
        insumoRepository.save(insumoBd);
        movimientoStockRepository.save(limpio);
    }
    @Transactional
    public void editar(Long id, MovimientoStock nuevoMovimiento) {
        if (id == null) {
            throw new IllegalArgumentException("Debes completar el campo ID para editar un movimiento.");
        }

        // 1. Buscamos el registro histórico original guardado en la base de datos
        MovimientoStock movimientoExistente = movimientoStockRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Registro de movimiento de stock no encontrado con el ID: " + id));

        // 2. Buscamos el insumo maestro asociado
        Insumo insumoBd = insumoRepository.findById(movimientoExistente.getInsumo().getId())
            .orElseThrow(() -> new RuntimeException("El insumo asociado no existe en el inventario."));

        // 3. REVERSIÓN LOGÍSTICA: Deshacemos el impacto del movimiento viejo en el stock actual
        BigDecimal stockRevertido = insumoBd.getStockActual();
        if ("entrada_compra".equals(movimientoExistente.getTipo()) || "ajuste_manual".equals(movimientoExistente.getTipo())) {
            stockRevertido = stockRevertido.subtract(movimientoExistente.getCantidad()); // Si antes sumó, ahora lo restamos
        } else {
            stockRevertido = stockRevertido.add(movimientoExistente.getCantidad()); // Si antes restó, ahora lo sumamos
        }
        
        // Seteamos temporalmente el stock del insumo como si ese movimiento viejo nunca hubiera ocurrido
        insumoBd.setStockActual(stockRevertido);

        // 4. VALIDACIÓN NUEVA: Forzamos la reevaluación matemática con los datos nuevos
        // (Pasamos el objeto nuevo adjuntando el ID del insumo original por seguridad)
        nuevoMovimiento.getInsumo().setId(insumoBd.getId());
        MovimientoStock datosNuevosLimpios = validarMovimientoStock(nuevoMovimiento);

        // 5. Traspasamos las propiedades calculadas al registro definitivo de Hibernate
        movimientoExistente.setTipo(datosNuevosLimpios.getTipo());
        movimientoExistente.setCantidad(datosNuevosLimpios.getCantidad());
        movimientoExistente.setCostoUnitario(datosNuevosLimpios.getCostoUnitario());
        movimientoExistente.setCostoTotal(datosNuevosLimpios.getCostoTotal());
        movimientoExistente.setStockAnterior(datosNuevosLimpios.getStockAnterior());
        movimientoExistente.setStockPosterior(datosNuevosLimpios.getStockPosterior());
        movimientoExistente.setObservaciones(datosNuevosLimpios.getObservaciones());
        movimientoExistente.setFecha(datosNuevosLimpios.getFecha());

        // 6. Impactamos las existencias definitivas calculadas en el insumo maestro
        insumoBd.setStockActual(datosNuevosLimpios.getStockPosterior());

        // 7. Consolidamos en las tablas de SQL
        insumoRepository.save(insumoBd);
        movimientoStockRepository.save(movimientoExistente);
    }    

    public List<MovimientoStock> listarTodo() {
        return movimientoStockRepository.findAll();
    }

    public List<MovimientoStock> listarPorInsumo(Long insumoId) {
        if (insumoId == null) {
            throw new IllegalArgumentException("El ID del insumo es requerido para filtrar el historial.");
        }
        return movimientoStockRepository.findByInsumoId(insumoId);
    }

    public List<MovimientoStock> listarPorRangoFechas(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas inicial y final son obligatorias.");
        }
        // Llama al nombre plano original del repositorio
        return movimientoStockRepository.findByFechaBetween(desde, hasta);
    }
    
    // ---  MÉTODO PRIVADO  ---

    private MovimientoStock validarMovimientoStock(MovimientoStock movimiento) {
        if (movimiento == null) {
            throw new IllegalArgumentException("La consulta de movimiento de stock está vacía.");
        }

        if (movimiento.getInsumo() == null || movimiento.getInsumo().getId() == null) {
            throw new IllegalArgumentException("El movimiento debe estar obligatoriamente asociado a un insumo válido.");
        }
        
        if (movimiento.getTipo() == null || movimiento.getTipo().strip().isEmpty()) {
            throw new IllegalArgumentException("El tipo de movimiento es obligatorio.");
        }
        
        String tipoLimpio = movimiento.getTipo().strip().toLowerCase();
        if (!tiposPermitidos.contains(tipoLimpio)) {
            throw new IllegalArgumentException("Tipo de movimiento inválido. Use únicamente: " + tiposPermitidos);
        }
        movimiento.setTipo(tipoLimpio);

        if (movimiento.getCantidad() == null || movimiento.getCantidad().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("La cantidad del movimiento debe ser estrictamente mayor a cero.");
        }

        // Buscamos el estado actual del inventario en la BD
        Insumo insumoBd = insumoRepository.findById(movimiento.getInsumo().getId())
            .orElseThrow(() -> new RuntimeException("El insumo asociado al movimiento no existe en el inventario."));

        // Automatización del Costo Total: Cantidad * Costo Unitario
        if (movimiento.getCostoUnitario() == null) {
            movimiento.setCostoUnitario(insumoBd.getCostoUnitario()); 
        }
        BigDecimal costoTotalCalculado = movimiento.getCantidad().multiply(movimiento.getCostoUnitario()).setScale(2, RoundingMode.HALF_UP);
        movimiento.setCostoTotal(costoTotalCalculado);

        // Capturamos el stock anterior antes de procesar la matemática
        BigDecimal stockAntes = insumoBd.getStockActual();
        movimiento.setStockAnterior(stockAntes);

        BigDecimal stockDespues;

        // Ecuación de stock según tipo de movimiento
        if ("entrada_compra".equals(tipoLimpio) || "ajuste_manual".equals(tipoLimpio)) {
            stockDespues = stockAntes.add(movimiento.getCantidad());
        } else {
            stockDespues = stockAntes.subtract(movimiento.getCantidad());
            
            // Freno de mano contra stock negativo
            if (stockDespues.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("Operación cancelada. El insumo '" + insumoBd.getNombre() 
                    + "' no posee existencias suficientes. Stock actual: " + stockAntes + ". Intenta retirar: " + movimiento.getCantidad());
            }
        }

        movimiento.setStockPosterior(stockDespues);
        
        if (movimiento.getFecha() == null) {
            movimiento.setFecha(LocalDateTime.now());
        }

        // Devolvemos el objeto limpio y adjuntamos la referencia completa del insumo de la BD
        movimiento.setInsumo(insumoBd);
        return movimiento;
    }
}

