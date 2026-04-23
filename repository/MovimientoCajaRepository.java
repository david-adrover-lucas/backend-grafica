package com.drover.demo.backend.repository;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.MovimientoCaja;

public interface MovimientoCajaRepository extends JpaRepository<MovimientoCaja, Integer> {
    List<MovimientoCaja> findByCuentaId(Integer cuentaId);
    List<MovimientoCaja> findByTipo(MovimientoCaja.TipoMovimiento tipo);
    List<MovimientoCaja> findByMontoGreaterThanEqual(BigDecimal monto);
    List<MovimientoCaja> findByMontoLessThanEqual(BigDecimal monto);
    List<MovimientoCaja> findByVentaId(Integer ventaId);
}
