package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Ventas;
import com.drover.demo.backend.repository.VentasRepository;

@Service
public class VentasService {

    private final VentasRepository ventasRepository;

    public VentasService(VentasRepository ventasRepository) {
        this.ventasRepository = ventasRepository;
    }

    public List<Ventas> listar() { }
    public List<Ventas> listarEstado(){}
    public List<Ventas> listarEstadoPago(){}
    public List<Ventas> listarPorFecha(){}
    public Optional<Ventas> buscarPorId(Long id) {}
    public Ventas guardar(Ventas ventas) { }
    public void eliminarPorId(Long id) {}

    private BigDecimal calcularMontoTotal(){}
}
