package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Ventas;

@Repository
public interface VentasRepository extends JpaRepository<Ventas, Long> {

    @Query("select v from Ventas v where v.nro_venta = :nroVenta")
    Optional<Ventas> findByNroVenta(@Param("nroVenta") String nroVenta);

    @Query("select v from Ventas v where v.cliente_id = :clienteId")
    List<Ventas> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("select v from Ventas v where v.revendedor_id = :revendedorId")
    List<Ventas> findByRevendedorId(@Param("revendedorId") Long revendedorId);

    @Query("select v from Ventas v where v.responsable_id = :responsableId")
    List<Ventas> findByResponsableId(@Param("responsableId") Long responsableId);

    @Query("select v from Ventas v where v.estado_venta = :estadoVenta")
    List<Ventas> findByEstadoVenta(@Param("estadoVenta") String estadoVenta);

    @Query("select v from Ventas v where v.estado_pago = :estadoPago")
    List<Ventas> findByEstadoPago(@Param("estadoPago") String estadoPago);

    @Query("select v from Ventas v where v.fecha_venta between :desde and :hasta")
    List<Ventas> findByFechaVentaBetween(@Param("desde") LocalDateTime desde, @Param("hasta") LocalDateTime hasta);
}
