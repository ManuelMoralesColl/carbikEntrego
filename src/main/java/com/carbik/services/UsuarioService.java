package com.carbik.services;

import com.carbik.dto.UsuarioDTO;
import com.carbik.models.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Usuario editarUsuario(Long id, Usuario usuario);
    void eliminarUsuario(Long id);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);
    List<Usuario> buscarPorNombre(String nombre);
    void seguirUsuario(Long seguidorId, Long seguidoId);
    void dejarDeSeguirUsuario(Long seguidorId, Long seguidoId);
    List<Usuario> obtenerSeguidores(Long usuarioId);
    List<Usuario> obtenerSeguidos(Long usuarioId);
    List<UsuarioDTO> findAllPublicUsers();
    List<UsuarioDTO> findAllPublicUsersDTO();
    List<Usuario> findByPrivadoFalse();
    boolean existeNombreUsuario(String nombreUsuario);
    boolean existeCorreo(String correo);
}

