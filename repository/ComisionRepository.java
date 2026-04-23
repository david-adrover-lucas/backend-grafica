package com.drover.demo.backend.repository;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.drover.demo.backend.entity.Comision;

public interface ComisionRepository extends JpaRepository<Comision, Integer> {
    // Buscar comisiones por id de usuario
     List <Comision> findByUsuarioId(Integer  usuarioId);
    // Buscar comisiones por id de producto (contiene texto)
     List<Comision> findByProductoId( Integer productoId);
     // Buscar comisiones por monto fijo mayor a X
    List<Comision> findByMontoFijoGreaterThan(BigDecimal montoFijo);
    void deleteById(@NonNull Integer id);    

} 

