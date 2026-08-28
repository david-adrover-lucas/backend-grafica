package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Comisiones_producto;
import com.drover.demo.backend.repository.Comisiones_productoRepository;

@Service
public class Comisiones_productoService {

    private final Comisiones_productoRepository comisionesProductoRepository;

    public Comisiones_productoService(Comisiones_productoRepository comisionesProductoRepository) {
        this.comisionesProductoRepository = comisionesProductoRepository;
    }

    public List<Comisiones_producto> listar() {}
    public Optional<Comisiones_producto> buscarPorId(Long id) {}
    public Comisiones_producto guardar(Comisiones_producto comisionesProducto) {}
    public void eliminarPorId(Long id) {}

    private BigDecimal calcularComision(){}

}
