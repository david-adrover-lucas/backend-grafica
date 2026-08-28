package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Producto;
import com.drover.demo.backend.repository.ProductoRepository;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listar() {}
    public List<Producto> listarNombre(){}
    public List<Producto> listarUnidad_venta(){}
    public Optional<Producto> buscarPorId(Long id){}
    public Producto guardar(Producto producto){}
    public void eliminarPorId(Long id){}

    private BigDecimal calcularPrecioVenta(){}
    private BigDecimal calcularCosto(){}

}
