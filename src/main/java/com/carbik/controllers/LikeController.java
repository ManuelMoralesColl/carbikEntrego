package com.carbik.controllers;

import com.carbik.services.LikePublicacionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikePublicacionService likePublicacionService;

    // Alternar like
    @PostMapping("/publicacion/{publicacionId}/usuario/{usuarioId}")
    public ResponseEntity<String> toggleLike(@PathVariable Long publicacionId, @PathVariable Long usuarioId) {
        boolean liked = likePublicacionService.toggleLike(usuarioId, publicacionId);
        if (liked) {
            return ResponseEntity.ok("Like añadido.");
        } else {
            return ResponseEntity.ok("Like eliminado.");
        }
    }

    // Contar likes
    @GetMapping("/publicacion/{publicacionId}/count")
    public ResponseEntity<Long> countLikes(@PathVariable Long publicacionId) {
        return ResponseEntity.ok(likePublicacionService.countLikes(publicacionId));
    }
}
