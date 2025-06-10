package com.carbik.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.LikePublicacion;

public interface LikeRepository extends JpaRepository<LikePublicacion, Long> {
    Optional<LikePublicacion> findByUsuario_IdAndPublicacion_Id(Long usuarioId, Long publicacionId);
    Long countByPublicacion_Id(Long publicacionId);
}
