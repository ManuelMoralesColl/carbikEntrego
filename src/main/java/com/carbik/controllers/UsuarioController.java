package com.carbik.controllers;

import com.carbik.dto.PublicacionDTO;
import com.carbik.dto.UsuarioDTO;
import com.carbik.dto.VehiculoDTO;
import com.carbik.models.Publicacion;
import com.carbik.models.Usuario;
import com.carbik.models.Vehiculo;
import com.carbik.repository.PublicacionRepository;
import com.carbik.repository.UsuarioRepository;
import com.carbik.repository.VehiculoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
	@Autowired
	VehiculoRepository vehiculoRepository ;
	@Autowired
	PublicacionRepository publicacionRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // <-- Inyectamos el PasswordEncoder

    // Crear usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody Usuario usuario) {
        System.out.println(">>> Llegó al endpoint de crear usuario");

        
        if (usuario.getPrivado() == null) {
            usuario.setPrivado(false); // Valor por defecto si no lo manda el cliente
        }
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        Usuario nuevoUsuario = usuarioRepository.save(usuario);

        return ResponseEntity.ok(new UsuarioDTO(nuevoUsuario));
    }

    // Editar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> editarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioEditado) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (!usuarioOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOptional.get();

        usuario.setNombre(usuarioEditado.getNombre());
        usuario.setApellidos(usuarioEditado.getApellidos());
        usuario.setNombreUsuario(usuarioEditado.getNombreUsuario());
        usuario.setCorreo(usuarioEditado.getCorreo());

        // Encriptar contraseña sólo si viene nueva y no está vacía o nula
        if (usuarioEditado.getContrasena() != null && !usuarioEditado.getContrasena().isBlank()) {
            usuario.setContrasena(passwordEncoder.encode(usuarioEditado.getContrasena()));
        }

        usuario.setFotoPerfil(usuarioEditado.getFotoPerfil());
        usuario.setVerificado(usuarioEditado.getVerificado());
        usuario.setPrivado(usuarioEditado.getPrivado());

        Usuario actualizado = usuarioRepository.save(usuario);
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        
        List<Vehiculo> vehiculos = vehiculoRepository.findByUsuario_Id(id);
        vehiculoRepository.deleteAll(vehiculos);
        
        List<Publicacion> publicacion = publicacionRepository.findByUsuario_Id(id);
        publicacionRepository.deleteAll(publicacion);
        
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<Usuario> obtenerMiUsuario(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String username = authentication.getName(); // nombre de usuario (username) del token JWT o sesión
        Optional<Usuario> usuarioOptional = usuarioRepository.findByNombreUsuario(username);

        return usuarioOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(404).build());
    }


    // Ver perfil usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> verUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id)
            .map(usuario -> ResponseEntity.ok(new UsuarioDTO(usuario)))
            .orElse(ResponseEntity.notFound().build());
    }

    // Buscar usuario por query (nombreUsuario o correo)
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioDTO>> buscarUsuario(@RequestParam String query) {
        List<UsuarioDTO> encontrados = usuarioRepository.findAll().stream()
            .filter(u -> u.getNombreUsuario().toLowerCase().contains(query.toLowerCase())
                      || u.getCorreo().toLowerCase().contains(query.toLowerCase()))
            .map(UsuarioDTO::new)
            .toList();

        return ResponseEntity.ok(encontrados);
    }

}
