package com.carbik.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.carbik.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
    //public List<Usuario> findAllPublicUsers(); // ❌ No se permite cuerpo en métodos de interfaz
    List<Usuario> findByPrivadoFalse();
    
}