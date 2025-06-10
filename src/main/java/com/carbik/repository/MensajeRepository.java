package com.carbik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Mensaje;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByChat_IdOrderByFechaEnvioAsc(Long chatId);
}
