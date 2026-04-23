package com.drover.demo.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.drover.demo.backend.entity.EmpresaTercerizada;

public interface EmpresaTercerizadaRepository extends JpaRepository<EmpresaTercerizada, Integer> {
    List<EmpresaTercerizada> findByNombre(String nombre);
    List<EmpresaTercerizada> findByActivo(Boolean activo);
    List<EmpresaTercerizada> findByNombreOrActivo(String nombre, Boolean activo);
    Optional<EmpresaTercerizada> findById(@NonNull Integer id);
}
