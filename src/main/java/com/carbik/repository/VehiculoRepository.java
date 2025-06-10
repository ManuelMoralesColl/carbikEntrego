package com.carbik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.carbik.models.Vehiculo;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByUsuario_Id(Long usuarioId);
    
    @Query("SELECT v FROM Vehiculo v WHERE v.seccion.id = :id OR v.seccion.parent.id = :id")
    List<Vehiculo> findBySeccionOrSubsecciones(Long id);
    @Query("SELECT v FROM Vehiculo v WHERE v.seccion.id = :id AND v.seccion.parent IS NULL")
    List<Vehiculo> findDirectInPadre(Long id);
}
