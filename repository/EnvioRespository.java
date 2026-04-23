package com.drover.demo.backend.repository;

import com.drover.demo.backend.entity.Envio;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnvioRespository extends JpaRepository<Envio, Integer> {

    // Buscar envíos por venta
    List<Envio> findByVentaId(Integer ventaId);
 
    // Buscar por estado
    List<Envio> findByEstado(Envio.EstadoEnvio estado);


}

