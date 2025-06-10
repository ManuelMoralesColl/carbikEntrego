package com.carbik.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.carbik.dto.PublicacionDTO;
import com.carbik.dto.UsuarioDTO;
import com.carbik.models.Publicacion;
import com.carbik.models.Usuario;
import com.carbik.services.FeedService;
import com.carbik.services.UsuarioService;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

	private final FeedService feedService;
	private final UsuarioService usuarioService;

	public FeedController(FeedService feedService, UsuarioService usuarioService) {
		this.feedService = feedService;
		this.usuarioService = usuarioService;
	}

//    
//    @GetMapping("/global")
//    public List<Publicacion> getGlobalFeed() {
//        List<Usuario> publicUsers = usuarioService.findAllPublicUsers();
//        return feedService.obtenerFeedGlobal(publicUsers);
//    }
	@GetMapping("/global")
	public ResponseEntity<List<PublicacionDTO>> getGlobalFeed(
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    List<UsuarioDTO> publicUsers = usuarioService.findAllPublicUsers();

	    List<PublicacionDTO> publicacionesDTO = feedService
	        .obtenerFeedGlobalPaginado(publicUsers, page, size)
	        .stream().map(PublicacionDTO::new)
	        .toList();

	    return ResponseEntity.ok(publicacionesDTO);
	}


	@GetMapping("/siguiendo/{id}")
	public ResponseEntity<List<PublicacionDTO>> getFollowingFeed(
	        @PathVariable Long id,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    List<Publicacion> publicaciones = feedService.obtenerFeedSiguiendoPaginado(id, page, size);

	    List<PublicacionDTO> publicacionesDTO = publicaciones.stream()
	            .map(PublicacionDTO::new)
	            .toList();

	    return ResponseEntity.ok(publicacionesDTO);
	}

}
