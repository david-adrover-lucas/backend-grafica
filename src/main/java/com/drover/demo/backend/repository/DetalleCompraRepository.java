package com.drover.demo.backend.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.drover.demo.backend.entity.DetalleCompra;


@Repository
public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long> {

    List<DetalleCompra> findByCompraId(Long compraId);
    
    List<DetalleCompra> findByInsumoId(Long insumoId);
}
