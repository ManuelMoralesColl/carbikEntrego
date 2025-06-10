package com.carbik.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Seguimiento;

public interface SeguimientoRepository extends JpaRepository<Seguimiento, Long> {
    List<Seguimiento> findBySeguidor_Id(Long seguidorId);
    List<Seguimiento> findBySeguido_Id(Long seguidoId);
    Optional<Seguimiento> findBySeguidor_IdAndSeguido_Id(Long seguidorId, Long seguidoId);
}

