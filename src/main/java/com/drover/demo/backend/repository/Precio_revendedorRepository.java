package com.drover.demo.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Precio_revendedor;

@Repository
public interface Precio_revendedorRepository extends JpaRepository<Precio_revendedor, Long> {

    @Query("select p from Precio_revendedor p where p.revendedor_id = :revendedorId")
    List<Precio_revendedor> findByRevendedorId(@Param("revendedorId") Long revendedorId);

    @Query("select p from Precio_revendedor p where p.producto_id = :productoId")
    List<Precio_revendedor> findByProductoId(@Param("productoId") Long productoId);

    List<Precio_revendedor> findByActivo(Boolean activo);

    @Query("""
            select p from Precio_revendedor p
            where p.revendedor_id = :revendedorId
              and p.producto_id = :productoId
              and p.activo = true
            """)
    Optional<Precio_revendedor> findPrecioActivo(@Param("revendedorId") Long revendedorId,
            @Param("productoId") Long productoId);
}
