package com.drover.demo.backend.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.drover.demo.backend.entity.Proveedor;
import com.drover.demo.backend.repository.ProveedorRepository;

import org.springframework.transaction.annotation.Transactional;




@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final List<String> tiposPermitidos = List.of("tercerizado", "stock");

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Transactional
    public void guardar(Proveedor proveedor) {
        Proveedor proveedorLimpio = validarProveedor(proveedor);
        String telefonoValidado = validarYLimpiarTelefono(proveedorLimpio.getTelefono());
        
        if (telefonoValidado != null && proveedorRepository.existsByTelefono(telefonoValidado)) {
            throw new IllegalArgumentException("El teléfono ya se encuentra registrado por otro proveedor.");
        }
        
        proveedorLimpio.setTelefono(telefonoValidado);
        proveedorRepository.save(proveedorLimpio);
    }
   
    @Transactional
    public void editar(Long id, Proveedor proveedor){
        Proveedor proveedorEncontrado = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con el ID: " + id));
        
        Proveedor proveedorLimpio = validarProveedor(proveedor);
        String telefonoValidado = validarYLimpiarTelefono(proveedorLimpio.getTelefono());


        if (telefonoValidado != null && proveedorRepository.existsByTelefonoAndIdNot(telefonoValidado, id)) {
            throw new IllegalArgumentException("El teléfono ya se encuentra registrado por otro proveedor.");
        }
        
        proveedorEncontrado.setNombre(proveedorLimpio.getNombre());
        proveedorEncontrado.setTelefono(telefonoValidado);
        proveedorEncontrado.setEmail(proveedorLimpio.getEmail());
        proveedorEncontrado.setRed_social(proveedorLimpio.getRed_social());
        proveedorEncontrado.setTipo(proveedorLimpio.getTipo());
        proveedorEncontrado.setActivo(proveedorLimpio.getActivo());     

        proveedorRepository.save(proveedorEncontrado);
    }
   
    public List<Proveedor> listar() {
        return proveedorRepository.findAll();
    }

    @Transactional
    public void eliminarPorId(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con el ID: " + id));
        
        proveedorRepository.delete(proveedor);
    }

    @Transactional
    public void desactivarProvedor(Long id){
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con el ID: " + id));  
        
        proveedor.setActivo(false);
        proveedorRepository.save(proveedor); 
    }

    public Proveedor buscarPorTelefono(String telefono){
        String telefonoLimpio = validarYLimpiarTelefono(telefono);
        if (telefonoLimpio == null) {
            throw new IllegalArgumentException("El teléfono de búsqueda no puede estar vacío.");
        }
        return proveedorRepository.findByTelefono(telefonoLimpio)
                .orElseThrow(() -> new RuntimeException("Teléfono no encontrado: " + telefono));
    }

    public List<Proveedor> mostrarActivos(Boolean activos) {
        Boolean estadoFiltro = (activos != null) ? activos : true;
        return proveedorRepository.findByActivo(estadoFiltro);
    }

    public List<Proveedor> buscarPorTipo(String tipo){
        tipo = textoLimpio(tipo);
        return proveedorRepository.findByTipo(tipo);
    }
   
    // Métodos privados
   
    private Proveedor validarProveedor(Proveedor proveedor) {
        if (proveedor == null) {
            throw new IllegalArgumentException("El proveedor no puede ser nulo.");
        }

        if (proveedor.getNombre() == null || proveedor.getNombre().strip().isEmpty() ||
            proveedor.getTipo() == null || proveedor.getTipo().strip().isEmpty()) {
            throw new IllegalArgumentException("Hay campos obligatorios vacíos (Nombre y Tipo son requeridos).");
        }

        if (proveedor.getActivo() == null) {
            proveedor.setActivo(true);
        }

        proveedor.setNombre(proveedor.getNombre().strip().toLowerCase());
        
        // Guardamos de forma limpia el tipo en minúsculas
        String tipoLimpio = proveedor.getTipo().strip().toLowerCase();
        
        if (!tiposPermitidos.contains(tipoLimpio)) {
            throw new IllegalArgumentException("Tipo de proveedor inválido. Permatidos: " + tiposPermitidos);
        }
        proveedor.setTipo(tipoLimpio);

        if (proveedor.getEmail() != null) {
            proveedor.setEmail(proveedor.getEmail().strip().toLowerCase());
        }
        if (proveedor.getRed_social() != null) {
            proveedor.setRed_social(proveedor.getRed_social().strip());
        }

        return proveedor;
    }

    private String textoLimpio(String string){
        if (string == null || string.strip().isEmpty()) {
            throw new IllegalArgumentException("El texto de búsqueda no puede estar vacío o ser nulo.");
        }
        return string.strip().toLowerCase();
    }
    
    private String validarYLimpiarTelefono(String telefono) {
        if (telefono == null) {
            return null;
        }

        String telefonoLimpio = telefono.strip();

        if (telefonoLimpio.isEmpty()) {
            return null; 
        }

        String regexTelefono = "^\\+?[0-9]{7,15}$";

        if (!Pattern.matches(regexTelefono, telefonoLimpio)) {
            throw new IllegalArgumentException("El formato del teléfono no es válido. Use solo números (entre 7 y 15 dígitos).");
        }
        
        return telefonoLimpio;
    }
}


