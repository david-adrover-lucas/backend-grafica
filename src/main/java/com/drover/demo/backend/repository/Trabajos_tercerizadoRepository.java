package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Trabajos_tercerizado;

@Repository
public interface Trabajos_tercerizadoRepository extends JpaRepository<Trabajos_tercerizado, Long> {

    @Query("select t from Trabajos_tercerizado t where t.proveedor_id = :proveedorId")
    List<Trabajos_tercerizado> findByProveedorId(@Param("proveedorId") Long proveedorId);

    List<Trabajos_tercerizado> findByActivo(Boolean activo);

    List<Trabajos_tercerizado> findByNombreContainingIgnoreCase(String nombre);
}
