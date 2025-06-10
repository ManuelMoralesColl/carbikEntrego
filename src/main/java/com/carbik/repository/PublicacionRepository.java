package com.carbik.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Publicacion;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
    List<Publicacion> findByUsuario_Id(Long usuarioId);
    List<Publicacion> findBySeccion_Id(Long seccionId);
    Page<Publicacion> findByUsuario_IdIn(List<Long> usuarioIds, Pageable pageable);
    

}
