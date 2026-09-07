package com.drover.demo.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drover.demo.backend.entity.MensajeWhatsApp;

@Repository
public interface MensajeWhatsAppRepository extends JpaRepository<MensajeWhatsApp, Long> {

    List<MensajeWhatsApp> findByTelefonoOrderByFechaDesc(String telefono);

    List<MensajeWhatsApp> findByVentaIdOrderByFechaDesc(Long ventaId);

    Optional<MensajeWhatsApp> findByWhatsappMessageId(String whatsappMessageId);
}
