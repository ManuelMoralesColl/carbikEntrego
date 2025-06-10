package com.carbik.controllers;

import com.carbik.dto.PublicacionDTO;
import com.carbik.models.FotoPublicacion;
import com.carbik.models.Publicacion;
import com.carbik.models.Usuario;
import com.carbik.repository.PublicacionRepository;
import com.carbik.repository.UsuarioRepository;
import com.carbik.repository.SeccionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private SeccionRepository seccionRepository;

    @PostMapping("/usuario/{usuarioId}")
    public ResponseEntity<PublicacionDTO> crearPublicacion(@PathVariable Long usuarioId, @RequestBody Publicacion publicacion) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        publicacion.setUsuario(usuarioOpt.get());
        publicacion.setFechaPublicacion(LocalDateTime.now());

        if (publicacion.getFotos() != null) {
            for (FotoPublicacion foto : publicacion.getFotos()) {
                foto.setPublicacion(publicacion);
            }
        }

        Publicacion nuevaPublicacion = publicacionRepository.save(publicacion);
        return ResponseEntity.ok(new PublicacionDTO(nuevaPublicacion));
    }


    // Editar publicación
    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> editarPublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionEditada) {
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(id);
        if (!publicacionOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Publicacion publicacion = publicacionOpt.get();
        publicacion.setPieDeFoto(publicacionEditada.getPieDeFoto());
        publicacion.setSeccion(publicacionEditada.getSeccion());
        Publicacion actualizada = publicacionRepository.save(publicacion);
        return ResponseEntity.ok(actualizada);
    }

    // Eliminar publicación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPublicacion(@PathVariable Long id) {
        if (!publicacionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        publicacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicacionDTO> verPublicacion(@PathVariable Long id) {
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(id);
        if (!publicacionOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PublicacionDTO(publicacionOpt.get()));
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PublicacionDTO>> verPublicacionesPorUsuario(@PathVariable Long usuarioId) {
        List<PublicacionDTO> publicaciones = publicacionRepository.findByUsuario_Id(usuarioId).stream()
            .map(PublicacionDTO::new)
            .toList();
        return ResponseEntity.ok(publicaciones);
    }


    @GetMapping("/seccion/{seccionId}")
    public ResponseEntity<List<PublicacionDTO>> verPublicacionesPorSeccion(@PathVariable Long seccionId) {
        List<PublicacionDTO> publicaciones = publicacionRepository.findBySeccion_Id(seccionId).stream()
            .map(PublicacionDTO::new)
            .toList();
        return ResponseEntity.ok(publicaciones);
    }



}
