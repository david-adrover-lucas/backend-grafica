package com.drover.demo.backend.repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.drover.demo.backend.entity.PagoVenta;


public interface PagoVentaRepository extends JpaRepository<PagoVenta, Long> {

    List<PagoVenta> findByVentaId(Long ventaId);
 
    List<PagoVenta> findByMedioPago(String medioPago);

    List<PagoVenta> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);

    Optional<PagoVenta> findByIdPagoExterno(String idPagoExterno);

    boolean existsByIdPagoExterno(String idPagoExterno);

}
