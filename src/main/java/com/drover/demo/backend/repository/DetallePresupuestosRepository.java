package com.drover.demo.backend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.drover.demo.backend.entity.DetallePresupuestos;



@Repository
public interface DetallePresupuestosRepository extends JpaRepository<DetallePresupuestos, Long> {

    List<DetallePresupuestos> findByPresupuestoId(Long presupuestoId);

    List<DetallePresupuestos> findByProductoId(Long productoId);
}

