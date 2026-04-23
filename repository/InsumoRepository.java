package com.drover.demo.backend.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.drover.demo.backend.entity.Insumo;

public interface InsumoRepository  extends JpaRepository<Insumo, Integer> {
    List<Insumo> findByNombre(String nombre);
    List<Insumo> findByTipo(Insumo.TipoInsumos tipo);
    List<Insumo> findByEmpresaId(Integer empresaId);
    List<Insumo> findByActivo(Boolean activo);
    List<Insumo> findByNombreOrTipo(String nombre, Insumo.TipoInsumos tipo);
    Optional<Insumo> findById(@NonNull Integer id);

} 