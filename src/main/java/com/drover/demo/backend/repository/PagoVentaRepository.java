package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.PagoVenta;

@Repository
public interface PagoVentaRepository extends JpaRepository<PagoVenta, Long> {

    @Query("select p from Pago_venta p where p.venta_id = :ventaId")
    List<PagoVenta> findByVentaId(@Param("ventaId") Long ventaId);

    @Query("select p from Pago_venta p where p.medio_pago = :medioPago")
    List<PagoVenta> findByMedioPago(@Param("medioPago") String medioPago);

    List<PagoVenta> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
