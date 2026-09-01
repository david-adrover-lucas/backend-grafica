package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.MovimientoStock;
import com.drover.demo.backend.repository.Movimiento_stockRepository;

@Service
public class Movimiento_stockService {

    private final Movimiento_stockRepository movimientoStockRepository;

    public Movimiento_stockService(Movimiento_stockRepository movimientoStockRepository) {
        this.movimientoStockRepository = movimientoStockRepository;
    }

    public List<MovimientoStock> listar() {
        return movimientoStockRepository.findAll();
    }

    public Optional<MovimientoStock> buscarPorId(Long id) {
        return movimientoStockRepository.findById(id);
    }

    public MovimientoStock guardar(MovimientoStock movimientoStock) {
        return movimientoStockRepository.save(movimientoStock);
    }

    public void eliminarPorId(Long id) {
        movimientoStockRepository.deleteById(id);
    }
}
