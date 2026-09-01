package com.drover.demo.backend.repository;

import com.drover.demo.backend.entity.PrecioRevendedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;



@Repository
public interface PrecioRevendedorRepository extends JpaRepository<PrecioRevendedor, Long> {


    Optional<PrecioRevendedor> findByRevendedorIdAndProductoIdAndActivoTrue(Long revendedorId, Long productoId);

    List<PrecioRevendedor> findByRevendedorIdOrderByProductoNombreAsc(Long revendedorId);


    List<PrecioRevendedor> findByProductoId(Long productoId);
    
  
    List<PrecioRevendedor> findByActivo(Boolean activo);

    boolean existsByRevendedorIdAndProductoId(Long revendedorId, Long productoId);
}

