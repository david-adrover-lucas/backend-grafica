package com.drover.demo.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByNombre(String nombre);
    List<Persona> findByApellido(String apellido);
    List<Persona> findByRol(String rol);
    List<Persona> findByActivo(Boolean activo);
    Optional<Persona> findById(Long id);
    Optional<Persona> findByTelefono(String telefono);

 

}
