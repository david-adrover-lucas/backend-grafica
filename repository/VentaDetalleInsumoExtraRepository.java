package com.drover.demo.backend.repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.drover.demo.backend.entity.VentaDetalleInsumoExtra;
public interface  VentaDetalleInsumoExtraRepository extends JpaRepository<VentaDetalleInsumoExtra, Integer> {
    List<VentaDetalleInsumoExtra> findByVentaDetalleId(Integer ventaDetalleId);
    List<VentaDetalleInsumoExtra> findByInsumoId(Integer insumoId);
    Optional<VentaDetalleInsumoExtra> findById(Integer id);
    void deleteById(Integer id);
}

