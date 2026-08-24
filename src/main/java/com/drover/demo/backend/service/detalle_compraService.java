package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.detalle_compra;
import com.drover.demo.backend.repository.detalle_compraRepository;

@Service
public class detalle_compraService {

    private final detalle_compraRepository detalleCompraRepository;

    public detalle_compraService(detalle_compraRepository detalleCompraRepository) {
        this.detalleCompraRepository = detalleCompraRepository;
    }

    public List<detalle_compra> listar() {
        return detalleCompraRepository.findAll();
    }

    public Optional<detalle_compra> buscarPorId(Long id) {
        return detalleCompraRepository.findById(id);
    }

    public detalle_compra guardar(detalle_compra detalleCompra) {
        return detalleCompraRepository.save(detalleCompra);
    }

    public void eliminarPorId(Long id) {
        detalleCompraRepository.deleteById(id);
    }
}
