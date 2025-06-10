package com.carbik.services;

import com.carbik.models.Vehiculo;
import com.carbik.dto.PublicacionDTO;
import com.carbik.dto.UsuarioDTO;
import com.carbik.dto.VehiculoDTO;
import com.carbik.models.Usuario;
import com.carbik.repository.VehiculoRepository;
import com.carbik.repository.PublicacionRepository;
import com.carbik.repository.UsuarioRepository;
import com.carbik.services.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final UsuarioRepository usuarioRepository;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, UsuarioRepository usuarioRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Vehiculo subirVehiculo(Vehiculo vehiculo) {
        // Aquí podrías validar el vehículo antes de guardarlo
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    public Vehiculo editarVehiculo(Long id, Vehiculo vehiculo) {
        Vehiculo existente = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        existente.setMarca(vehiculo.getMarca());
        existente.setModelo(vehiculo.getModelo());
        existente.setAno(vehiculo.getAno());
        existente.setDescripcion(vehiculo.getDescripcion());
        existente.setPrecio(vehiculo.getPrecio());
        existente.setPublicado(vehiculo.getPublicado());
        existente.setEnVenta(vehiculo.getEnVenta());
        existente.setUsuario(vehiculo.getUsuario());
        // Otros campos

        return vehiculoRepository.save(existente);
    }

    @Override
    public void eliminarVehiculo(Long id) {
        vehiculoRepository.deleteById(id);
    }

    @Override
    public List<Vehiculo> listarVehiculosPublicos() {
        return vehiculoRepository.findAll()
                .stream()
                .filter(v -> v.getUsuario() != null && v.getUsuario().getPrivado() != null && !v.getUsuario().getPrivado())
                .filter(Vehiculo::getPublicado)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehiculo> listarVehiculosEnVentaPublicos() {
        return vehiculoRepository.findAll()
                .stream()
                .filter(v -> v.getUsuario() != null && v.getUsuario().getPrivado() != null && !v.getUsuario().getPrivado())
                .filter(Vehiculo::getPublicado)
                .filter(Vehiculo::getEnVenta)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehiculo> listarVehiculosDeUsuariosSeguidos(Long usuarioId) {
        // Para esta función necesitas los seguidos del usuario
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Long> seguidosIds = usuario.getSeguidos().stream()
                .map(s -> s.getSeguido().getId())
                .toList();

        return vehiculoRepository.findAll()
                .stream()
                .filter(v -> v.getUsuario() != null && seguidosIds.contains(v.getUsuario().getId()))
                .filter(Vehiculo::getPublicado)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehiculo> buscarVehiculosPorModelo(String modelo) {
        return vehiculoRepository.findAll()
                .stream()
                .filter(v -> v.getModelo() != null && v.getModelo().toLowerCase().contains(modelo.toLowerCase()))
                .filter(Vehiculo::getPublicado)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Vehiculo> buscarPorId(Long id) {
        return vehiculoRepository.findById(id);
    }
    
    //buscar en secciones
    @Override
    public List<Vehiculo> findBySeccionOrSubsecciones(Long id) {
        return vehiculoRepository.findBySeccionOrSubsecciones(id);
    }

    @Override
    public void darMeGusta(Long vehiculoId, Long usuarioId) {}

	@Override
	public void quitarMeGusta(Long vehiculoId, Long usuarioId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Vehiculo> findDirectInPadre(Long id) {
		// TODO Auto-generated method stub
		return null;
	}}
