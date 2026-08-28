package com.drover.demo.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Proveedor;
import com.drover.demo.backend.repository.ProveedorRepository;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    public List<Proveedor> listar() {}
    public Boolean buscarPorId(Long id) {}
    public Proveedor guardar(Proveedor proveedor) {}
    public void eliminarPorId(Long id) {}
    public Proveedor buscarPorTelefon(){}
    public List<Proveedor> buscarPorActivo(){}
    public List<Proveedor> buscarPorTipo(){}

}
