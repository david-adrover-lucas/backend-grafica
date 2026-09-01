package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.TrabajosTercerizado;

@Repository
public interface TrabajosTercerizadoRepository extends JpaRepository<TrabajosTercerizado, Long> {

    @Query("select t from Trabajos_tercerizado t where t.proveedor_id = :proveedorId")
    List<TrabajosTercerizado> findByProveedorId(@Param("proveedorId") Long proveedorId);

    List<TrabajosTercerizado> findByActivo(Boolean activo);

    List<TrabajosTercerizado> findByNombreContainingIgnoreCase(String nombre);
}
