package com.drover.demo.backend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.drover.demo.backend.entity.TrabajoTercerizadoVenta;


@Repository
public interface TrabajoTercerizadoVentaRepository extends JpaRepository<TrabajoTercerizadoVenta, Long> {
    List<TrabajoTercerizadoVenta> findByVentaId(Long ventaId);
    List<TrabajoTercerizadoVenta> findByProveedorId(Long proveedorId);
    List<TrabajoTercerizadoVenta> findByEstado(String estado);
}

