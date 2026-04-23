package com.drover.demo.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drover.demo.backend.entity.Diseno;

public interface DisenoRepository  extends JpaRepository< Diseno, Integer>{
    List<Diseno> findByClienteId(Integer clienteId);
    List<Diseno> findByVentaId(Integer ventaId);
    List<Diseno> findByCategoria(String categoria);
    List<Diseno> findByEtiquetasContainingIgnoreCase(String etiqueta);
    List<Diseno> findTop10ByOrderByContadorUsoDesc();
    List<Diseno> findByNombreContainingIgnoreCase(String nombre);
   // List<Diseno> findByCategoriaAndEtiquetasContainingIgnoreCaseAndNombreContainingIgnoreCase(String categoria,String etiqueta, String nombre);
    List<Diseno> findByCategoriaAndEtiquetasContainingIgnoreCaseAndNombreContainingIgnoreCase(String categoria,
            String etiqueta, String nombre);
    List<Diseno> findByCategoriaAndEtiquetasContainingIgnoreCase(String categoria, String etiqueta);
    List<Diseno> findByCategoriaAndNombreContainingIgnoreCase(String categoria, String nombre);
    List<Diseno> findByEtiquetasContainingIgnoreCaseAndNombreContainingIgnoreCase(String etiqueta, String nombre);
    List<Diseno> findByCategoriaAndEtiquetasContainingIgnoreCaseAndNombreContainingIgnoreCaseAndReutilizable(
            String trim, String trim2, String trim3, Boolean reutilizable);
    
}
