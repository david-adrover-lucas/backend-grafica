package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Pago_venta;

@Repository
public interface Pago_ventaRepository extends JpaRepository<Pago_venta, Long> {

    @Query("select p from Pago_venta p where p.venta_id = :ventaId")
    List<Pago_venta> findByVentaId(@Param("ventaId") Long ventaId);

    @Query("select p from Pago_venta p where p.medio_pago = :medioPago")
    List<Pago_venta> findByMedioPago(@Param("medioPago") String medioPago);

    List<Pago_venta> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
