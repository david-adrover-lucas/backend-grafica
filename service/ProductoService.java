package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.entity.ProductoInsumo;

import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.repository.ProductoRepository;

import com.drover.demo.backend.repository.InsumoRepository;
import com.drover.demo.backend.repository.ProductoInsumoRepository;

import org.springframework.stereotype.Service;
import com.drover.demo.backend.exception.RecursoNoEncontradoException;
import com.drover.demo.backend.exception.ValidacionException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoInsumoRepository productoInsumoRepository;
    private final InsumoRepository insumoRepository;

    public ProductoService(ProductoRepository productoRepository,
                           ProductoInsumoRepository productoInsumoRepository,
                           InsumoRepository insumoRepository) {
        this.productoRepository = productoRepository;
        this.productoInsumoRepository = productoInsumoRepository;
        this.insumoRepository = insumoRepository;
    }

    /**
     * 🥇 Crear producto
     */
    public Producto guardar(Producto producto) {


        if (producto.getPorcentajeGanancia() == null) {
            producto.setPorcentajeGanancia(BigDecimal.ZERO);
        }

        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }

        return productoRepository.save(producto);
    }

    /**
     * 🥈 Listar productos activos
     */
    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrue();
    }

     /**
     * 🥉 Calcular costo total del producto
     */
    public BigDecimal calcularCosto( Integer productoId) {

     List<ProductoInsumo> relaciones =productoInsumoRepository.findByProductoId(productoId);

     BigDecimal total = BigDecimal.ZERO;

     for (ProductoInsumo pi : relaciones) {
          Insumo insumo = insumoRepository.findById(pi.getInsumoId()).orElseThrow(() -> new RecursoNoEncontradoException("Insumo no encontrado"));
         // 🔥 Asumiendo cantidad = 1 (ajusta si hay campo real)
         BigDecimal cantidad = BigDecimal.ONE;  // O pi.getCantidad() si existe
         BigDecimal costo = insumo.getCostoUnitario().multiply(cantidad);
         total = total.add(costo);
        }

        return total;
    }


    /**
     * 💣 Calcular precio final (con ganancia)
     */
    public BigDecimal calcularPrecio(Integer productoId) {

        Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        BigDecimal costo = calcularCosto(productoId);

        BigDecimal ganancia = costo.multiply(
                producto.getPorcentajeGanancia().divide(BigDecimal.valueOf(100))
        );

        return costo.add(ganancia);
    }

    /**
     * 📦 Buscar producto por ID
     */
    public Producto obtenerPorId(Integer id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));
    }

    /**
     * ❌ Desactivar producto
     */
    public void desactivar(Integer id) {

        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto no encontrado"));

        producto.setActivo(false);

        productoRepository.save(producto);
    }
}
