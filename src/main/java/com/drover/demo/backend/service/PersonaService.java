package com.drover.demo.backend.service;

import com.drover.demo.backend.entity.Cliente;
import com.drover.demo.backend.entity.Empleado;
import com.drover.demo.backend.entity.Persona;
import com.drover.demo.backend.entity.Revendedor;
import com.drover.demo.backend.entity.Vendedor;
import com.drover.demo.backend.repository.ClienteRepository;
import com.drover.demo.backend.repository.EmpleadoRepository;
import com.drover.demo.backend.repository.PersonaRepository;
import com.drover.demo.backend.repository.RevendedorRepository;
import com.drover.demo.backend.repository.VendedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaService {

    private final PersonaRepository personaRepository;
    private final VendedorRepository vendedorRepository;
    private final EmpleadoRepository empleadoRepository;
    private final RevendedorRepository revendedorRepository;
    private final ClienteRepository clienteRepository;
    
    private final List<String> rolesPermitidos = List.of("vendedores", "empleados", "revendedores", "clientes");

    public PersonaService(PersonaRepository personaRepository, VendedorRepository vendedorRepository,  
                          EmpleadoRepository empleadoRepository, RevendedorRepository revendedorRepository,
                          ClienteRepository clienteRepository) {    
        this.personaRepository = personaRepository;
        this.vendedorRepository = vendedorRepository;
        this.empleadoRepository = empleadoRepository;
        this.revendedorRepository = revendedorRepository;
        this.clienteRepository = clienteRepository;
    }
   
    @Transactional
    public void guardar(Persona persona) {
        Persona personaLimpia = validarDatos(persona);
        
        if (personaRepository.findByTelefono(personaLimpia.getTelefono()).isPresent()) {
            throw new IllegalArgumentException("El número de teléfono ya está registrado por otro usuario.");
        }
        

        Persona personaGuardada = personaRepository.saveAndFlush(personaLimpia);
        Long idGenerado = personaGuardada.getId();
        
        // Delegamos la creación en la tabla hija correspondiente usando el mismo ID
        crearRegistroHijoPorRol(idGenerado, personaGuardada.getRol());
    }
    
    @Transactional
    public void editarPersona(Long id, Persona persona) {
        Persona existentePersona = personaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + id));
        
        Persona personaDatosLimpios = validarDatos(persona);
        validarTelefonoUnicoParaEdicion(id, personaDatosLimpios.getTelefono());
        
        String rolAnterior = existentePersona.getRol();
        String nuevoRol = personaDatosLimpios.getRol();
        Boolean nuevoEstado = personaDatosLimpios.getActivo();

        actualizarCamposPersona(existentePersona, personaDatosLimpios);
        personaRepository.save(existentePersona);
       
        if (!rolAnterior.equals(nuevoRol)) {
            desactivarHijoPorRol(id, rolAnterior);
        } else {
            sincronizarEstadoHijos(id, nuevoRol, nuevoEstado);
        }
    }
  
    @Transactional 
    public void desactivarPersona(Long id) {
        Persona persona = personaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + id));
        
        persona.setActivo(false);
        personaRepository.save(persona);

        desactivarTodosLosHijos(id);
    }

    @Transactional
    public void borrarPersonaDesactivada(Long id) {
        Persona persona = personaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Persona no encontrada con el ID: " + id));
            
        if (persona.getActivo()) { 
            throw new IllegalStateException("Esta persona está activa, no se puede borrar de forma definitiva.");
        }
        
        personaRepository.delete(persona);
    }
   
    public List<Persona> mostrarPersona() {
        return personaRepository.findAll();
    }
        
    public List<Persona> listarPorRol(String rol) {
        return personaRepository.findByRol(limpiarTexto(rol));
    }
    
    public List<Persona> mostrasActivos(Boolean activos) {
        return personaRepository.findByActivo(activos != null ? activos : true);     
    }
    
    public List<Persona> buscarPorNombre(String nombre) {
        return personaRepository.findByNombre(limpiarTexto(nombre));
    }
    
    public Optional<Persona> buscarPorTelefono(String telefono) {
        return personaRepository.findByTelefono(limpiarTexto(telefono));
    }

    // --- MÉTODOS PRIVADOS ---

    private void actualizarCamposPersona(Persona destino, Persona origen) {
        destino.setNombre(origen.getNombre());
        destino.setApellido(origen.getApellido());
        destino.setRol(origen.getRol());
        destino.setTelefono(origen.getTelefono());
        destino.setActivo(origen.getActivo());
    }
  
    private void validarTelefonoUnicoParaEdicion(Long id, String telefono) {
        Optional<Persona> personaConMismoTelefono = personaRepository.findByTelefono(telefono);
        if (personaConMismoTelefono.isPresent() && !personaConMismoTelefono.get().getId().equals(id)) {
            throw new IllegalArgumentException("El número de teléfono ya está registrado por otro usuario.");
        }
    }
  
    private void desactivarTodosLosHijos(Long id) {
        vendedorRepository.findById(id).ifPresent(v -> {
            v.setActivo(false);
            v.setFechaBaja(LocalDate.now()); 
            vendedorRepository.save(v);
        });
        empleadoRepository.findById(id).ifPresent(e -> {
            e.setActivo(false);
            e.setFechaBaja(LocalDate.now()); 
            empleadoRepository.save(e);
        }); 
        revendedorRepository.findById(id).ifPresent(r -> {
            r.setActivo(false);
            r.setFechaBaja(LocalDate.now()); 
            revendedorRepository.save(r);
        }); 
    }

    private void desactivarHijoPorRol(Long id, String rol) {
        if ("vendedores".equals(rol)) {
            vendedorRepository.findById(id).ifPresent(v -> {
                v.setActivo(false);
                v.setFechaBaja(LocalDate.now());
                vendedorRepository.save(v);
            });
        }
        if ("empleados".equals(rol)) {
            empleadoRepository.findById(id).ifPresent(e -> {
                e.setActivo(false);
                e.setFechaBaja(LocalDate.now());
                empleadoRepository.save(e);
            });
        }
        if ("revendedores".equals(rol)) {
            revendedorRepository.findById(id).ifPresent(r -> {
                r.setActivo(false);
                r.setFechaBaja(LocalDate.now());
                revendedorRepository.save(r);
            });
        }
    }

    private void sincronizarEstadoHijos(Long id, String rol, Boolean activo) {
        LocalDate fechaBajaCalculada = Boolean.TRUE.equals(activo) ? null : LocalDate.now();
        if ("vendedores".equals(rol)) {
            vendedorRepository.findById(id).ifPresent(v -> {
                v.setActivo(activo);
                v.setFechaBaja(fechaBajaCalculada);
                vendedorRepository.save(v);
            });
        }
        if ("empleados".equals(rol)) {
            empleadoRepository.findById(id).ifPresent(e -> {
                e.setActivo(activo);
                e.setFechaBaja(fechaBajaCalculada);
                empleadoRepository.save(e);
            });
        }
        if ("revendedores".equals(rol)) {
            revendedorRepository.findById(id).ifPresent(r -> {
                r.setActivo(activo);
                r.setFechaBaja(fechaBajaCalculada);
                revendedorRepository.save(r);
            });
        }
        if ("clientes".equals(rol)) {
            clienteRepository.findById(id).ifPresent(c -> {
                personaRepository.findById(id).ifPresent(p -> c.setNumero(p.getTelefono()));
                clienteRepository.save(c);
            });
        }
    }

    private Persona validarDatos(Persona persona) {
        if (persona.getNombre() == null || persona.getTelefono() == null || persona.getRol() == null) {
            throw new IllegalArgumentException("Por favor, completar los campos necesarios.");
        }
        String nombreLimpio = persona.getNombre().strip().toLowerCase();
        String apellidoLimpio = (persona.getApellido() != null) ? persona.getApellido().strip().toLowerCase() : "";
        String telefonoLimpio = persona.getTelefono().strip().toLowerCase();
        String rolLimpio = persona.getRol().strip().toLowerCase();
        
        if (nombreLimpio.isEmpty() || telefonoLimpio.isEmpty() || rolLimpio.isEmpty()) {
            throw new IllegalArgumentException("Por favor, debe llenar los campos obligatorios con texto válido.");
        }
        if (!rolesPermitidos.contains(rolLimpio)) {
            throw new IllegalArgumentException("Rol no válido. Los roles aceptados son: " + rolesPermitidos);
        }
        if (persona.getActivo() == null) {
            persona.setActivo(true);
        }
        persona.setNombre(nombreLimpio);
        persona.setApellido(apellidoLimpio);
        persona.setTelefono(telefonoLimpio);
        persona.setRol(rolLimpio);
       
        return persona;
    }
    
    private String limpiarTexto(String texto) {
        if (texto == null) {
            throw new IllegalArgumentException("El parámetro de búsqueda no puede ser nulo.");
        }
        String textoLimpio = texto.strip().toLowerCase();
        if (textoLimpio.isEmpty()) {
            throw new IllegalArgumentException("El parámetro de búsqueda no puede estar vacío.");
        }
        return textoLimpio;
    }
    
    private void crearRegistroHijoPorRol(Long id, String rol) {
        switch (rol) {
         case "vendedores":Vendedor vendedor = new Vendedor();
                vendedor.setId(id);
                vendedor.setActivo(true);
                vendedor.setFechaAlta(LocalDate.now());
                vendedorRepository.save(vendedor);
           break;
          case "empleados":Empleado empleado = new Empleado();
                empleado.setId(id);
                empleado.setActivo(true);
                empleado.setFechaAlta(LocalDate.now());
                empleadoRepository.save(empleado);
           break;
          case "revendedores":Revendedor revendedor = new Revendedor();
                revendedor.setId(id);
                revendedor.setActivo(true);
                revendedor.setFechaAlta(LocalDate.now());
                revendedorRepository.save(revendedor);
           break;
           case "clientes":
                String telefonoPersona = personaRepository.findById(id).map(Persona::getTelefono).orElse("S/N"); 
                Cliente cliente = new Cliente();
                cliente.setId(id);
                cliente.setNumero(telefonoPersona); // Asignamos automáticamente el teléfono como su número
                cliente.setDepartamento(null);   // Valor inicial por defecto
                clienteRepository.save(cliente);
            break;
           default:throw new IllegalArgumentException("Rol inesperado al crear entidad hija.");
        }
    }
}