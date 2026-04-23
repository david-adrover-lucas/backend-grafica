package com.drover.demo.backend.repository;

import com.drover.demo.backend.entity.Cuenta;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


public interface CuentaRepository extends JpaRepository<Cuenta, Integer> {

    List<Cuenta> findByNombre(String nombre);

    List<Cuenta> findByActiva(Boolean activa);
    
    List<Cuenta> findByNombreOrActiva(String nombre, Boolean activa);

} 