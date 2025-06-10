// SeguimientoController.java
package com.carbik.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.carbik.dto.SeguimientoDTO;
import com.carbik.models.Seguimiento;
import com.carbik.models.Usuario;
import com.carbik.repository.SeguimientoRepository;
import com.carbik.repository.UsuarioRepository;
import com.carbik.services.SeguimientoService;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoController {

	@Autowired
	private SeguimientoRepository seguimientoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@PostMapping("/seguir/{seguidoId}")
	public ResponseEntity<?> seguir(@PathVariable Long seguidoId) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = auth.getName();
		if (!nombreUsuario.isEmpty()) {
			System.out.println("🧪 auth.getName() = " + nombreUsuario);
		} else {
			System.out.println("GET NAME ESTA MAL");
		}

		Usuario seguidor = usuarioRepository.findByNombreUsuario(nombreUsuario).orElseThrow();
		Usuario seguido = usuarioRepository.findById(seguidoId).orElseThrow();

		if (seguimientoRepository.findBySeguidor_IdAndSeguido_Id(seguidor.getId(), seguido.getId()).isPresent()) {
			return ResponseEntity.badRequest().body("Ya estás siguiendo a este usuario");
		}

		Seguimiento seguimiento = new Seguimiento();
		seguimiento.setSeguidor(seguidor);
		seguimiento.setSeguido(seguido);
		seguimiento.setFechaSeguimiento(LocalDateTime.now());

		seguimientoRepository.save(seguimiento);
		return ResponseEntity.ok(Map.of("mensaje", "Siguiendo al usuario"));
	}

	@GetMapping("/estado")
	public ResponseEntity<?> obtenerEstadoSeguimiento(@RequestParam Long idSeguido) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = auth.getName();

		Usuario seguidor = usuarioRepository.findByNombreUsuario(nombreUsuario).orElseThrow(); // Cambiado aquí
		Usuario seguido = usuarioRepository.findById(idSeguido).orElseThrow();

		boolean siguiendo = seguimientoRepository.findBySeguidor_IdAndSeguido_Id(seguidor.getId(), seguido.getId())
				.isPresent();

		return ResponseEntity.ok(Map.of("siguiendo", siguiendo));
	}

	@DeleteMapping("/dejar/{seguidoId}")
	public ResponseEntity<?> dejarDeSeguir(@PathVariable Long seguidoId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String nombreUsuario = auth.getName();

		Usuario seguidor = usuarioRepository.findByNombreUsuario(nombreUsuario).orElseThrow(); // Cambiar también aquí

		Optional<Seguimiento> seguimiento = seguimientoRepository.findBySeguidor_IdAndSeguido_Id(seguidor.getId(),
				seguidoId);

		if (seguimiento.isEmpty()) {
			return ResponseEntity.badRequest().body("No estás siguiendo a este usuario");
		}

		seguimientoRepository.delete(seguimiento.get());
		return ResponseEntity.ok(Map.of("mensaje", "Has dejado de seguir al usuario"));
	}

	@GetMapping("/conteo")
	public ResponseEntity<?> contarSeguidoresYSeguidos(@RequestParam Long userId) {
		int siguiendo = seguimientoRepository.findBySeguidor_Id(userId).size();
		int seguidores = seguimientoRepository.findBySeguido_Id(userId).size();
		Map<String, Integer> conteo = new HashMap<>();
		conteo.put("siguiendo", siguiendo);
		conteo.put("seguidores", seguidores);
		return ResponseEntity.ok(conteo);
	}
}
