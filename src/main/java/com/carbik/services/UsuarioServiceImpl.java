package com.carbik.services;

import com.carbik.dto.UsuarioDTO;
import com.carbik.models.Usuario;
import com.carbik.repository.UsuarioRepository;
import com.carbik.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario crearUsuario(Usuario usuario) {
    	   if (existeNombreUsuario(usuario.getNombreUsuario())) {
    	        throw new IllegalArgumentException("El nombre de usuario ya está en uso");
    	    }
    	    
    	    // Verificar si el correo ya existe
    	    if (existeCorreo(usuario.getCorreo())) {
    	        throw new IllegalArgumentException("El correo electrónico ya está registrado");
    	    }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario editarUsuario(Long id, Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findById(id);
        if (existente.isPresent()) {
            Usuario actualizado = existente.get();
            actualizado.setNombre(usuario.getNombre());
            return usuarioRepository.save(actualizado);
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }
    
    @Override
    public List<UsuarioDTO> findAllPublicUsersDTO() {
        return usuarioRepository.findByPrivadoFalse()
                .stream()
                .map(UsuarioDTO::new)
                .toList();
    }


    @Override
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioRepository.findByNombreContainingIgnoreCase(nombre);
    }

	@Override
	public void seguirUsuario(Long seguidorId, Long seguidoId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dejarDeSeguirUsuario(Long seguidorId, Long seguidoId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> obtenerSeguidores(Long usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Usuario> obtenerSeguidos(Long usuarioId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<UsuarioDTO> findAllPublicUsers() {
	    return usuarioRepository.findAll()
	            .stream()
	            .filter(u -> u.getPrivado() == null || !u.getPrivado())
	            .map(UsuarioDTO::new) // Convertir cada Usuario a UsuarioDTO
	            .toList();
	}
    @Override
    public List<Usuario> findByPrivadoFalse() {
        return usuarioRepository.findByPrivadoFalse();
    }
    public boolean existeNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario).isPresent();
    }

    @Override
    public boolean existeCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo).isPresent();
    }

}
