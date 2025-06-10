package com.carbik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByReceptor_IdOrderByFechaDesc(Long receptorId);
}
