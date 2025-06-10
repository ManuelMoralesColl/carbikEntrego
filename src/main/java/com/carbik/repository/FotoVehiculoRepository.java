package com.carbik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.FotoVehiculo;

public interface FotoVehiculoRepository extends JpaRepository<FotoVehiculo, Long> {
    List<FotoVehiculo> findByVehiculo_Id(Long vehiculoId);
}
