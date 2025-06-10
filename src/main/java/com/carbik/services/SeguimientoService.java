// SeguimientoService.java
package com.carbik.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.carbik.models.Seguimiento;
import com.carbik.models.Usuario;
import com.carbik.repository.SeguimientoRepository;
import com.carbik.repository.UsuarioRepository;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepository seguimientoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean seguir(Long seguidorId, Long seguidoId) {
        if (seguidorId.equals(seguidoId)) return false; // No seguirte a ti mismo

        Optional<Seguimiento> existente = seguimientoRepository.findBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
        if (existente.isPresent()) return false; // Ya sigue

        Usuario seguidor = usuarioRepository.findById(seguidorId).orElseThrow();
        Usuario seguido = usuarioRepository.findById(seguidoId).orElseThrow();

        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setSeguidor(seguidor);
        seguimiento.setSeguido(seguido);
        seguimiento.setFechaSeguimiento(LocalDateTime.now());

        seguimientoRepository.save(seguimiento);
        return true;
    }

    public boolean dejarDeSeguir(Long seguidorId, Long seguidoId) {
        Optional<Seguimiento> seguimientoOpt = seguimientoRepository.findBySeguidor_IdAndSeguido_Id(seguidorId, seguidoId);
        if (seguimientoOpt.isEmpty()) return false;

        seguimientoRepository.delete(seguimientoOpt.get());
        return true;
    }
}
