package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Compra;
import com.drover.demo.backend.repository.CompraRepository;

@Service
public class CompraService {

    private final CompraRepository compraRepository;

    public CompraService(CompraRepository compraRepository) {
        this.compraRepository = compraRepository;
    }

    public List<Compra> listar() {}
    public List<Compra> listarPorFechCompras() {}
    public Compra listarPorNroCompra() {}
    public Optional<Compra> buscarPorId(Long id) {}
    public Compra guardar(Compra compra) {}
    public void eliminarPorId(Long id) {}
    
    private BigDecimal calcularTotal(){}


}
