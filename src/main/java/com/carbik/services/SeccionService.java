package com.carbik.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbik.dto.SeccionDTO;
import com.carbik.models.Seccion;
import com.carbik.repository.SeccionRepository;

@Service
public class SeccionService {

    @Autowired
    private SeccionRepository seccionRepository;

    public List<SeccionDTO> obtenerCategoriasPrincipales() {
        return seccionRepository.findByParentIsNull();
    }

    public List<Seccion> obtenerSubcategoriasDe(String nombreCategoriaPadre) {
        Optional<Seccion> categoria = seccionRepository.findByNombre(nombreCategoriaPadre);
        return categoria.map(seccionRepository::findByParent).orElse(List.of());
    }

    public Optional<Seccion> buscarPorNombre(String nombre) {
        return seccionRepository.findByNombre(nombre);
    }

    public Seccion guardar(Seccion seccion) {
        return seccionRepository.save(seccion);
    }
}
