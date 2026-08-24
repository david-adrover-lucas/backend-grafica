package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long> {

    @Query("select c from Compra c where c.proveedor_id = :proveedorId")
    List<Compra> findByProveedorId(@Param("proveedorId") Long proveedorId);

    @Query("select c from Compra c where c.numero_compra = :numeroCompra")
    Optional<Compra> findByNumeroCompra(@Param("numeroCompra") String numeroCompra);

    List<Compra> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
