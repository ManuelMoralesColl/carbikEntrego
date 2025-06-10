package com.carbik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.FotoPublicacion;

public interface FotoPublicacionRepository extends JpaRepository<FotoPublicacion, Long> {
    List<FotoPublicacion> findByPublicacion_Id(Long publicacionId);
}
