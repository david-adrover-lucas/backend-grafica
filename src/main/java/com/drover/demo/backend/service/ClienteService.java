package com.drover.demo.backend.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Cliente;
import com.drover.demo.backend.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }
    //metodos
    
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }

    @Transactional
    public void actualizarDepartamento(Long id, String nuevoDepartamento) {
        Cliente cliente= clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente no encontrado con el ID: " + id));
        
        String datosLimpio= textoLimpio(nuevoDepartamento);
        
        cliente.setDepartamento(datosLimpio);
        
        clienteRepository.save(cliente);
    }

    public List<Cliente> listarPorDepartamento(String departamento){
        String departamentoLimpio=textoLimpio(departamento);
        return clienteRepository.findByDepartamento(departamentoLimpio);
    }
    
    public Cliente buscarPorNumero(String numero){
        String numeroValidado= validarTelefono(numero);
        return clienteRepository.findByNumero(numeroValidado)
         .orElseThrow(() -> new RuntimeException("Cliente no encontrado con el número: " + numero));
    }
    
    private String textoLimpio(String departamento){
        if (departamento==null|| departamento.strip().isEmpty()) {
            throw new IllegalArgumentException(" cargue el departamento vacio");
        }
        String departamentoLimpio = departamento.toLowerCase().strip();
        return departamentoLimpio;
    }
    
    private String validarTelefono( String numero){
       if (numero == null || numero.strip().isEmpty()) {
         throw new IllegalArgumentException("El teléfono no puede estar vacío o ser nulo.");
        }
        String regexNumerico = "^[0-9]{7,15}$";
        if (!Pattern.matches(regexNumerico, numero.strip())) {
         throw new IllegalArgumentException("El formato del teléfono no es válido. Solo se permiten entre 7 y 15 números.");
        }
        return numero.strip();
    }
}
