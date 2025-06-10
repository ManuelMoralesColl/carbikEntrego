package com.carbik.controllers;

import com.carbik.models.Comentario;
import com.carbik.repository.ComentarioRepository;
import com.carbik.repository.PublicacionRepository;
import com.carbik.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Crear comentario en publicación
    @PostMapping("/publicacion/{publicacionId}/usuario/{usuarioId}")
    public ResponseEntity<Comentario> crearComentario(@PathVariable Long publicacionId, @PathVariable Long usuarioId, @RequestBody Comentario comentario) {
        var publicacionOpt = publicacionRepository.findById(publicacionId);
        var usuarioOpt = usuarioRepository.findById(usuarioId);
        if (!publicacionOpt.isPresent() || !usuarioOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        comentario.setPublicacion(publicacionOpt.get());
        comentario.setUsuario(usuarioOpt.get());
        Comentario nuevoComentario = comentarioRepository.save(comentario);
        return ResponseEntity.ok(nuevoComentario);
    }

    // Obtener comentarios de una publicación
    @GetMapping("/publicacion/{publicacionId}")
    public ResponseEntity<List<Comentario>> verComentarios(@PathVariable Long publicacionId) {
        List<Comentario> comentarios = comentarioRepository.findByPublicacion_Id(publicacionId);
        return ResponseEntity.ok(comentarios);
    }
}
