package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.PresupuestoDetalle;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.repository.PresupuestoDetalleRepository;
import com.drover.demo.backend.repository.ProductoRepository;

@Service
public class PresupuestoDetalleService {

    private final PresupuestoDetalleRepository repository;
    private final ProductoService productoService;
    private final ProductoRepository productoRepository;

    public PresupuestoDetalleService(PresupuestoDetalleRepository repository,
                                     ProductoService productoService,
                                     ProductoRepository productoRepository) {
        this.repository = repository;
        this.productoService = productoService;
        this.productoRepository = productoRepository;
    }

    public PresupuestoDetalle agregarProducto(PresupuestoDetalle detalle) {


        Producto producto = productoRepository.findById(detalle.getProductoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        BigDecimal costoBase = productoService.calcularCosto(producto.getId());
        BigDecimal precioBase = productoService.calcularPrecio(producto.getId());

        BigDecimal multiplicador = BigDecimal.valueOf(detalle.getCantidad());

        if (producto.getTipo() != null && producto.getTipo() == Producto.TipoProducto.M2) {
            if (detalle.getAlto() == null || detalle.getAncho() == null) {
                throw new RuntimeException("Faltan medidas");
            }

            BigDecimal area = detalle.getAlto().multiply(detalle.getAncho());
            multiplicador = multiplicador.multiply(area);
        }

        detalle.setCosto(costoBase.multiply(multiplicador));
        detalle.setPrecio(precioBase.multiply(multiplicador));

        return repository.save(detalle);
    }

    public List<PresupuestoDetalle> listarPorPresupuesto( Integer id) {
        return repository.findByPresupuestoId(id);
    }
}