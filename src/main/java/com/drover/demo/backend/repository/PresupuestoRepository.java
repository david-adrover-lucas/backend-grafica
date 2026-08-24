package com.drover.demo.backend.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.Presupuesto;

@Repository
public interface PresupuestoRepository extends JpaRepository<Presupuesto, Long> {

    @Query("select p from Presupuesto p where p.nro_presupuesto = :nroPresupuesto")
    Optional<Presupuesto> findByNroPresupuesto(@Param("nroPresupuesto") String nroPresupuesto);

    @Query("select p from Presupuesto p where p.cliente_id = :clienteId")
    List<Presupuesto> findByClienteId(@Param("clienteId") Long clienteId);

    @Query("select p from Presupuesto p where p.revendedor_id = :revendedorId")
    List<Presupuesto> findByRevendedorId(@Param("revendedorId") Long revendedorId);

    @Query("select p from Presupuesto p where p.responsable_id = :responsableId")
    List<Presupuesto> findByResponsableId(@Param("responsableId") Long responsableId);

    List<Presupuesto> findByEstado(String estado);

    List<Presupuesto> findByFechaBetween(LocalDateTime desde, LocalDateTime hasta);
}
