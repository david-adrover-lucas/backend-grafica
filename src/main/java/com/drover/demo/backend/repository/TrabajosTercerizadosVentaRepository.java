package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.TrabajosTercerizadosVenta;

@Repository
public interface TrabajosTercerizadosVentaRepository extends JpaRepository<TrabajosTercerizadosVenta, Long> {

    @Query("select t from Trabajos_tercerizados_venta t where t.venta_id = :ventaId")
    List<TrabajosTercerizadosVenta> findByVentaId(@Param("ventaId") Long ventaId);

    @Query("select t from Trabajos_tercerizados_venta t where t.detalle_venta_id = :detalleVentaId")
    List<TrabajosTercerizadosVenta> findByDetalleVentaId(@Param("detalleVentaId") Long detalleVentaId);

    @Query("select t from Trabajos_tercerizados_venta t where t.trabajo_tercerizado_id = :trabajoTercerizadoId")
    List<TrabajosTercerizadosVenta> findByTrabajoTercerizadoId(
            @Param("trabajoTercerizadoId") Long trabajoTercerizadoId);

    @Query("select t from Trabajos_tercerizados_venta t where t.proveedor_id = :proveedorId")
    List<TrabajosTercerizadosVenta> findByProveedorId(@Param("proveedorId") Long proveedorId);

    List<TrabajosTercerizadosVenta> findByEstado(String estado);
}
