package com.carbik.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.LikeVehiculo;

public interface LikeVehiculoRepository extends JpaRepository<LikeVehiculo, Long> {
    Optional<LikeVehiculo> findByUsuario_IdAndVehiculo_Id(Long usuarioId, Long vehiculoId);
    Long countByVehiculo_Id(Long vehiculoId);
}
