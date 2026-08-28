package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Insumo;
import com.drover.demo.backend.repository.InsumoRepository;

@Service
public class InsumoService {

    private final InsumoRepository insumoRepository;

    public InsumoService(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }
    public List<Insumo> listar() {}
    public Boolean  buscarPorId(Long id) {}
    public Insumo guardar(Insumo insumo) {}
    public List<Insumo> listarPorUnidad(){}
    public void eliminarPorId(Long id){}

    private Boolean validarCosto(BigDecimal costo){}
    private Boolean validarStock(BigDecimal stock){}
}
