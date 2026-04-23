package com.drover.demo.backend.repository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByVentaId(Integer ventaId);
    List<Pago> findByCuentaId(Integer cuentaId);
    List<Pago> findByTipoPago(Pago.TipoPago tipoPago);
    List<Pago> findByMontoGreaterThanEqual(BigDecimal monto);
    List<Pago> findByMontoLessThanEqual(BigDecimal monto);
    
}
