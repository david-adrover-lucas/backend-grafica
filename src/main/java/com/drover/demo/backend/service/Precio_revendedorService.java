package com.drover.demo.backend.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Precio_revendedor;
import com.drover.demo.backend.repository.Precio_revendedorRepository;

@Service
public class Precio_revendedorService {

    private final Precio_revendedorRepository precioRevendedorRepository;

    public Precio_revendedorService(Precio_revendedorRepository precioRevendedorRepository) {
        this.precioRevendedorRepository = precioRevendedorRepository;
    }
    public List<Precio_revendedor> listar() {}
    public Optional<Precio_revendedor> buscarPorId(Long id) {}
    public Precio_revendedor guardar(Precio_revendedor precioRevendedor) {}
    public void eliminarPorId(Long id) {}

    private BigDecimal calcularPrecio(){}
}
