package com.carbik.services;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.carbik.dto.PublicacionDTO;
import com.carbik.dto.UsuarioDTO;
import com.carbik.models.Publicacion;
import com.carbik.models.Seguimiento;
import com.carbik.models.Usuario;
import com.carbik.repository.PublicacionRepository;
import com.carbik.repository.SeguimientoRepository;

@Service
public class FeedService {

    private final PublicacionRepository publicacionRepository;
    private final SeguimientoRepository seguimientoRepository;

    public FeedService(PublicacionRepository publicacionRepository, SeguimientoRepository seguimientoRepository) {
        this.publicacionRepository = publicacionRepository;
        this.seguimientoRepository = seguimientoRepository;
    }

    public List<Publicacion> obtenerFeedGlobal(List<Usuario> usuariosPublicos) {
        return publicacionRepository.findAll().stream()
                .filter(p -> usuariosPublicos.contains(p.getUsuario()))
                .collect(Collectors.toList());
    }
    public List<Publicacion> obtenerFeedSiguiendoPaginado(Long usuarioId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaPublicacion").descending());

        List<Seguimiento> seguidos = seguimientoRepository.findBySeguidor_Id(usuarioId);
        List<Long> idsSeguidos = seguidos.stream()
                .map(s -> s.getSeguido().getId())
                .toList();

        return publicacionRepository.findByUsuario_IdIn(idsSeguidos, pageable).getContent();
    }

    public List<Publicacion> obtenerFeedSiguiendo(Long usuarioId) {
        List<Seguimiento> seguidos = seguimientoRepository.findBySeguidor_Id(usuarioId);
        return publicacionRepository.findAll().stream()
                .filter(p -> seguidos.stream()
                        .anyMatch(s -> s.getSeguido().getId().equals(p.getUsuario().getId())))
                .collect(Collectors.toList());
    }

    public List<Publicacion> obtenerFeedGlobalPaginado(List<UsuarioDTO> publicUsers, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fechaPublicacion").descending());

        List<Long> publicUserIds = publicUsers.stream()
                .map(UsuarioDTO::getId)
                .toList();

        return publicacionRepository.findByUsuario_IdIn(publicUserIds, pageable).getContent();
    }

}
