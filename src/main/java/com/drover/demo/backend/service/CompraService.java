package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Compra;
import com.drover.demo.backend.entity.DetalleCompra;
import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.entity.MovimientoStock;
import com.drover.demo.backend.repository.CompraRepository;
import com.drover.demo.backend.repository.InsumoRepository;
import com.drover.demo.backend.repository.MovimientoStockRepository;

import jakarta.transaction.Transactional;


@Service
public class CompraService {

    private final CompraRepository compraRepository;
    private final InsumoRepository insumoRepository;
    private final MovimientoStockRepository movimientoStockRepository;

    public CompraService(CompraRepository compraRepository, InsumoRepository insumoRepository,
                         MovimientoStockRepository movimientoStockRepository) {
        this.compraRepository = compraRepository;
        this.insumoRepository = insumoRepository;
        this.movimientoStockRepository = movimientoStockRepository;
    }

    @Transactional
    public void guardar(Compra compra) {
        Compra compraLimpia = validarCompra(compra);
        Compra compraGuardada = compraRepository.saveAndFlush(compraLimpia);
        registrarEntradaStockPorCompra(compraGuardada);
    }
    
    @Transactional
    public void editar(Long id, Compra compra) {
        if (id == null) {
            throw new IllegalArgumentException("Debe marcar un ID válido de compra.");
        }
        
        Compra compraExistente = compraRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ID de compra no encontrado: " + id));

        revertirStockCompra(compraExistente);

        Compra compraLimpia = validarCompra(compra);
        
        compraExistente.setNumeroCompra(compraLimpia.getNumeroCompra());
        compraExistente.setFecha(compraLimpia.getFecha());
        compraExistente.setProveedor(compraLimpia.getProveedor());
        compraExistente.setTotal(compraLimpia.getTotal());
        compraExistente.setObservaciones(compraLimpia.getObservaciones());
        
        compraExistente.getDetalles().clear();
        for (DetalleCompra nuevoDetalle : compraLimpia.getDetalles()) {
            compraExistente.getDetalles().add(nuevoDetalle);
            nuevoDetalle.setCompra(compraExistente);      
        }

        
        Compra compraGuardada = compraRepository.saveAndFlush(compraExistente);
        registrarEntradaStockPorCompra(compraGuardada);
    }

    public List<Compra> listar() {
        return compraRepository.findAll();
    }

    public List<Compra> listarPorFechCompras(LocalDateTime desde, LocalDateTime hasta) {
        if (desde == null || hasta == null) {
            throw new IllegalArgumentException("Las fechas provistas no son válidas.");
        }
        return compraRepository.findByFechaBetween(desde, hasta);
    }

    public Compra listarPorNroCompra(String nroCompra) {
        String nroCompraLimpio = validarString(nroCompra);
        return compraRepository.findByNumeroCompra(nroCompraLimpio)
            .orElseThrow(() -> new RuntimeException("Número de compra no encontrado: " + nroCompra));
    }

    // --- MÉTODOS PRIVADOS DE VALIDACIÓN ---

    private BigDecimal validarEntero(BigDecimal decimal) {
        if (decimal == null || decimal.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Los números o montos no pueden ser nulos ni menores a 0.");
        }
        return decimal;
    }

    private String validarString(String string) {
        if (string == null || string.strip().isEmpty()) {
            throw new IllegalArgumentException("Este campo de texto es obligatorio.");
        }
        return string.strip().toLowerCase();
    }


    private Compra validarCompra(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La consulta de compra está vacía.");
        }
        
        if (compra.getProveedor() == null || compra.getProveedor().getId() == null) {
            throw new IllegalArgumentException("El proveedor es obligatorio.");
        }
        
        compra.setNumeroCompra(validarString(compra.getNumeroCompra()));
        
        if (compra.getFecha() == null) {
            compra.setFecha(LocalDateTime.now());
        }

        if (compra.getDetalles() == null || compra.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("Una compra no puede guardarse sin detalles.");
        }

        BigDecimal totalCalculado = BigDecimal.ZERO;

        for (DetalleCompra detalle : compra.getDetalles()) {
            if (detalle.getInsumo() == null || detalle.getInsumo().getId() == null) {
                throw new IllegalArgumentException("Cada renglón debe especificar un insumo válido.");
            }

            totalCalculado = totalCalculado.add(detalle.getSubtotal());

            detalle.setCompra(compra);
        }

        compra.setTotal(totalCalculado);
        return compra;
    }

    private void registrarEntradaStockPorCompra(Compra compra) {
        for (DetalleCompra detalle : compra.getDetalles()) {
            Insumo insumo = insumoRepository.findById(detalle.getInsumo().getId())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado para actualizar stock: " + detalle.getInsumo().getId()));

            BigDecimal stockAnterior = insumo.getStockActual();
            BigDecimal stockPosterior = stockAnterior.add(detalle.getCantidad());

            insumo.setStockActual(stockPosterior);
            insumo.setCostoUnitario(detalle.getPrecioUnitario());
            insumoRepository.save(insumo);

            movimientoStockRepository.save(crearMovimientoStock(
                insumo,
                compra,
                "entrada_compra",
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                stockAnterior,
                stockPosterior,
                "Entrada automatica por compra " + compra.getNumeroCompra()
            ));
        }
    }

    private void revertirStockCompra(Compra compra) {
        for (DetalleCompra detalle : compra.getDetalles()) {
            Insumo insumo = insumoRepository.findById(detalle.getInsumo().getId())
                .orElseThrow(() -> new RuntimeException("Insumo no encontrado para revertir stock: " + detalle.getInsumo().getId()));

            BigDecimal stockAnterior = insumo.getStockActual();
            BigDecimal stockPosterior = stockAnterior.subtract(detalle.getCantidad());

            if (stockPosterior.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalStateException("No se puede editar la compra porque el insumo '" + insumo.getNombre()
                    + "' ya fue consumido y revertirla dejaria stock negativo.");
            }

            insumo.setStockActual(stockPosterior);
            insumoRepository.save(insumo);

            movimientoStockRepository.save(crearMovimientoStock(
                insumo,
                compra,
                "ajuste_manual",
                detalle.getCantidad(),
                detalle.getPrecioUnitario(),
                stockAnterior,
                stockPosterior,
                "Reversion automatica por edicion de compra " + compra.getNumeroCompra()
            ));
        }
    }

    private MovimientoStock crearMovimientoStock(Insumo insumo, Compra compra, String tipo, BigDecimal cantidad,
                                                 BigDecimal costoUnitario, BigDecimal stockAnterior,
                                                 BigDecimal stockPosterior, String observaciones) {
        MovimientoStock movimiento = new MovimientoStock();
        movimiento.setInsumo(insumo);
        movimiento.setCompra(compra);
        movimiento.setTipo(tipo);
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
