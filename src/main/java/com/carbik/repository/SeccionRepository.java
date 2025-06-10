package com.carbik.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.dto.SeccionDTO;
import com.carbik.models.Seccion;

public interface SeccionRepository extends JpaRepository<Seccion, Long> {

    List<SeccionDTO> findByParentIsNull();

    List<Seccion> findByParent(Seccion parent);

    Optional<Seccion> findByNombre(String nombre);

    List<Seccion> findByNombreContainingIgnoreCase(String nombreParcial);
}
