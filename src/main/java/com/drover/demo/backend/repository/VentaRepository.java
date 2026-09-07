package com.drover.demo.backend.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.drover.demo.backend.entity.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    List<Venta> findByClienteId(Long clienteId);
    List<Venta> findByRevendedorId(Long revendedorId);

    Optional<Venta> findByNroVenta(String nroVenta);

    List<Venta> findByEstadoVenta(String estadoVenta);

    List<Venta> findByEstadoPago(String estadoPago);

    List<Venta> findByFechaVentaBetween(LocalDateTime desde, LocalDateTime hasta);

    List<Venta> findByResponsableIdAndEstadoVentaAndFechaVentaBetween(Long responsableId, String estadoVenta, LocalDateTime desde, LocalDateTime hasta);

    boolean existsByNroVenta(String nroVenta);

    boolean existsByPresupuestoId(Long presupuestoId);
}
