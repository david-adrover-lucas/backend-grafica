package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findByActivo(Boolean activo);

    List<Proveedor> findByTipo(String tipo);

    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);
}
