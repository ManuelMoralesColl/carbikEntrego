package com.carbik.controllers;

import com.carbik.models.*;
import com.carbik.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MensajeRepository mensajeRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // 1. Listar chats del usuario (por id)
    @GetMapping
    public ResponseEntity<List<Chat>> listarChats(@RequestParam Long usuarioId) {
        List<Chat> chats = chatRepository.findByParticipantes_Id(usuarioId);
        return ResponseEntity.ok(chats);
    }

    // 2. Crear chat o recuperar chat existente entre dos usuarios
    @PostMapping
    public ResponseEntity<Chat> crearORecuperarChat(@RequestParam Long usuario1Id, @RequestParam Long usuario2Id) {
        if (usuario1Id.equals(usuario2Id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<Usuario> u1 = usuarioRepository.findById(usuario1Id);
        Optional<Usuario> u2 = usuarioRepository.findById(usuario2Id);
        if (!u1.isPresent() || !u2.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        // Buscar chat que tenga exactamente estos dos participantes
        List<Chat> chatsUsuario1 = chatRepository.findByParticipantes_Id(usuario1Id);
        for (Chat chat : chatsUsuario1) {
            Set<Usuario> participantes = new HashSet<>(chat.getParticipantes());
            if (participantes.size() == 2 && participantes.contains(u2.get())) {
                return ResponseEntity.ok(chat);
            }
        }
        // Crear nuevo chat
        Chat nuevoChat = new Chat();
        nuevoChat.setParticipantes(Arrays.asList(u1.get(), u2.get()));
        Chat chatGuardado = chatRepository.save(nuevoChat);
        return ResponseEntity.ok(chatGuardado);
    }

    // 3. Ver mensajes de un chat
    @GetMapping("/{chatId}")
    public ResponseEntity<List<Mensaje>> verMensajes(@PathVariable Long chatId, @RequestParam Long usuarioId) {
        Optional<Chat> chatOpt = chatRepository.findById(chatId);
        if (!chatOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Chat chat = chatOpt.get();
        // Validar que el usuario es participante del chat
        boolean participante = chat.getParticipantes().stream().anyMatch(u -> u.getId().equals(usuarioId));
        if (!participante) {
            return ResponseEntity.status(403).build();
        }
        List<Mensaje> mensajes = mensajeRepository.findByChat_IdOrderByFechaEnvioAsc(chatId);
        return ResponseEntity.ok(mensajes);
    }

    // 4. Enviar mensaje en chat
    @PostMapping("/{chatId}/mensaje")
    public ResponseEntity<Mensaje> enviarMensaje(@PathVariable Long chatId, @RequestParam Long usuarioId, @RequestBody String contenido) {
        Optional<Chat> chatOpt = chatRepository.findById(chatId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (!chatOpt.isPresent() || !usuarioOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Chat chat = chatOpt.get();
        Usuario usuario = usuarioOpt.get();

        // Validar participación
        boolean participante = chat.getParticipantes().stream().anyMatch(u -> u.getId().equals(usuarioId));
        if (!participante) {
            return ResponseEntity.status(403).build();
        }

        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setEmisor(usuario);
        mensaje.setContenido(contenido);
        mensaje.setFechaEnvio(LocalDateTime.now());

        Mensaje guardado = mensajeRepository.save(mensaje);
        return ResponseEntity.ok(guardado);
    }

    // 5. Compartir publicación en chat (validar privacidad)
    @PostMapping("/{chatId}/compartirPublicacion/{publicacionId}")
    public ResponseEntity<String> compartirPublicacionEnChat(@PathVariable Long chatId, @PathVariable Long publicacionId, @RequestParam Long usuarioId) {
        Optional<Chat> chatOpt = chatRepository.findById(chatId);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        Optional<Publicacion> publicacionOpt = publicacionRepository.findById(publicacionId);

        if (!chatOpt.isPresent() || !usuarioOpt.isPresent() || !publicacionOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Chat chat = chatOpt.get();
        Usuario usuario = usuarioOpt.get();
        Publicacion publicacion = publicacionOpt.get();

        // Validar que usuario es participante del chat
        boolean participante = chat.getParticipantes().stream().anyMatch(u -> u.getId().equals(usuarioId));
        if (!participante) {
            return ResponseEntity.status(403).build();
        }

        // Validar privacidad de la cuenta del dueño del vehículo en la publicación
        Usuario dueño = publicacion.getUsuario();
        if (Boolean.TRUE.equals(dueño.getPrivado())) {
            // Cuenta privada, no puede compartir
            return ResponseEntity.status(403).body("No se puede compartir publicaciones de cuentas privadas.");
        }

        // Crear mensaje especial que contiene la referencia a la publicación
        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setEmisor(usuario);
        mensaje.setFechaEnvio(LocalDateTime.now());
        mensaje.setContenido("Compartió una publicación: ID " + publicacionId);

        // Aquí podrías añadir un campo extra en Mensaje para la publicación compartida si tienes
        // Por ahora dejamos el texto indicativo

        mensajeRepository.save(mensaje);
        return ResponseEntity.ok("Publicación compartida en el chat.");
    }
}
