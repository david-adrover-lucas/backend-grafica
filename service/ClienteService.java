package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.Cliente;
import com.drover.demo.backend.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public Cliente guardar(Cliente cliente) {


        if (cliente.getActivo() == null) {
            cliente.setActivo(true);
        }

        cliente.setCreatedAt(LocalDateTime.now());

        return repository.save(cliente);
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public List<Cliente> buscar(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }
    public List<Cliente> clientesConDuedas (BigDecimal deuda,  String telefono) {
        return repository.findClientesConDuedas(deuda, true, telefono);
    }

}
