package com.carbik.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Guardado;

public interface GuardadoRepository extends JpaRepository<Guardado, Long> {
    Optional<Guardado> findByUsuario_IdAndPublicacion_Id(Long usuarioId, Long publicacionId);
}

