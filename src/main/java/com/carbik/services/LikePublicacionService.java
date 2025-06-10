package com.carbik.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carbik.models.LikePublicacion;
import com.carbik.models.Publicacion;
import com.carbik.models.Usuario;
import com.carbik.repository.LikeRepository;
import com.carbik.repository.PublicacionRepository;
import com.carbik.repository.UsuarioRepository;

@Service
public class LikePublicacionService {

    private final LikeRepository likeRepository;
    private final UsuarioRepository usuarioRepository;
    private final PublicacionRepository publicacionRepository;

    public LikePublicacionService(LikeRepository likeRepository,
                                   UsuarioRepository usuarioRepository,
                                   PublicacionRepository publicacionRepository) {
        this.likeRepository = likeRepository;
        this.usuarioRepository = usuarioRepository;
        this.publicacionRepository = publicacionRepository;
    }

    public boolean toggleLike(Long usuarioId, Long publicacionId) {
        Optional<LikePublicacion> existing = likeRepository.findByUsuario_IdAndPublicacion_Id(usuarioId, publicacionId);

        if (existing.isPresent()) {
            likeRepository.delete(existing.get());
            return false;
        } else {
            Optional<Usuario> usuario = usuarioRepository.findById(usuarioId);
            Optional<Publicacion> publicacion = publicacionRepository.findById(publicacionId);

            if (usuario.isEmpty() || publicacion.isEmpty()) {
                throw new IllegalArgumentException("Usuario o Publicación no encontrada");
            }

            LikePublicacion like = new LikePublicacion();
            like.setUsuario(usuario.get());
            like.setPublicacion(publicacion.get());
            like.setFechaLike(LocalDateTime.now());
            likeRepository.save(like);
            return true;
        }
    }

    public Long countLikes(Long publicacionId) {
        return likeRepository.countByPublicacion_Id(publicacionId);
    }
}
