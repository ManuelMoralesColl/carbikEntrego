package com.carbik.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carbik.models.Publicacion;
import com.carbik.models.Usuario;
import com.carbik.repository.PublicacionRepository;
import com.carbik.repository.UsuarioRepository;

@Service
public class PublicacionService {

    private final PublicacionRepository publicacionRepository;
    private final UsuarioRepository usuarioRepository;

    public PublicacionService(PublicacionRepository publicacionRepository, UsuarioRepository usuarioRepository) {
        this.publicacionRepository = publicacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Publicacion crearPublicacion(Publicacion publicacion, Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        publicacion.setUsuario(usuario);
        return publicacionRepository.save(publicacion);
    }

    public void eliminarPublicacion(Long publicacionId, Long usuarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));
        if (!publicacion.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No autorizado");
        }
        publicacionRepository.delete(publicacion);
    }

    public List<Publicacion> obtenerPublicacionesDeUsuario(Long usuarioId) {
        return publicacionRepository.findByUsuario_Id(usuarioId);
    }

    public List<Publicacion> obtenerPublicacionesPorSeccion(Long seccionId) {
        return publicacionRepository.findBySeccion_Id(seccionId);
    }

    public Optional<Publicacion> obtenerPublicacionPorId(Long id) {
        return publicacionRepository.findById(id);
    }

    public Publicacion editarPublicacion(Publicacion actualizada, Long publicacionId, Long usuarioId) {
        Publicacion original = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new RuntimeException("Publicación no encontrada"));

        if (!original.getUsuario().getId().equals(usuarioId)) {
            throw new RuntimeException("No autorizado");
        }

        original.setPieDeFoto(actualizada.getPieDeFoto());
        return publicacionRepository.save(original);
    }
}
