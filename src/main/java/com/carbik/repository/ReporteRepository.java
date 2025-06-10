package com.carbik.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Reporte;

public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByTipoAndReferenciaId(String tipo, Long referenciaId);
}
