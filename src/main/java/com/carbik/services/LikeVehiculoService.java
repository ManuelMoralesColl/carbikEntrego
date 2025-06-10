package com.carbik.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.carbik.models.LikeVehiculo;
import com.carbik.models.Usuario;
import com.carbik.models.Vehiculo;
import com.carbik.repository.LikeVehiculoRepository;

@Service
public class LikeVehiculoService {

    private final LikeVehiculoRepository likeVehiculoRepository;

    public LikeVehiculoService(LikeVehiculoRepository likeVehiculoRepository) {
        this.likeVehiculoRepository = likeVehiculoRepository;
    }

    public boolean toggleLike(Long usuarioId, Long vehiculoId) {
        Optional<LikeVehiculo> existing = likeVehiculoRepository.findByUsuario_IdAndVehiculo_Id(usuarioId, vehiculoId);

        if (existing.isPresent()) {
            likeVehiculoRepository.delete(existing.get());
            return false;
        } else {
            LikeVehiculo like = new LikeVehiculo();
            like.setUsuario(new Usuario()); // debes setear el usuario real
            like.setVehiculo(new Vehiculo()); // debes setear el vehículo real
            likeVehiculoRepository.save(like);
            return true;
        }
    }

    public Long countLikes(Long vehiculoId) {
        return likeVehiculoRepository.countByVehiculo_Id(vehiculoId);
    }
}
