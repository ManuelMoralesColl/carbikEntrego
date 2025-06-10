package com.carbik.controllers;

import com.carbik.dto.VehiculoDTO;
import com.carbik.models.FotoVehiculo;
import com.carbik.models.Seccion;
import com.carbik.models.Usuario;
import com.carbik.models.Vehiculo;
import com.carbik.repository.VehiculoRepository;
import com.carbik.repository.SeccionRepository;
import com.carbik.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
	@Autowired
	private SeccionRepository seccionRepository;

	@Autowired
	private VehiculoRepository vehiculoRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	// Crear vehículo para un usuario
	@PostMapping("/usuario/{usuarioId}")
	public ResponseEntity<VehiculoDTO> crearVehiculo(@PathVariable Long usuarioId, @RequestBody Vehiculo vehiculo) {
	    Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
	    if (usuarioOpt.isEmpty()) {
	        return ResponseEntity.notFound().build();
	    }

	    // Validar que se recibió una sección
	    if (vehiculo.getSeccion() == null || vehiculo.getSeccion().getId() == null) {
	        return ResponseEntity.badRequest().body(null);
	    }

	    // Buscar la sección en la base de datos (muy importante)
	    Optional<Seccion> seccionOpt = seccionRepository.findById(vehiculo.getSeccion().getId());
	    if (seccionOpt.isEmpty()) {
	        return ResponseEntity.badRequest().build(); // o notFound()
	    }

	    vehiculo.setSeccion(seccionOpt.get());
	    vehiculo.setUsuario(usuarioOpt.get());

	    // Asignar relación inversa en las fotos
	    if (vehiculo.getFotos() != null) {
	        for (FotoVehiculo foto : vehiculo.getFotos()) {
	            foto.setVehiculo(vehiculo);
	        }
	    }

	    Vehiculo nuevoVehiculo = vehiculoRepository.save(vehiculo);
	    return ResponseEntity.ok(new VehiculoDTO(nuevoVehiculo));
	}




	@PutMapping("/{id}")
	public ResponseEntity<Vehiculo> editarVehiculo(@PathVariable Long id, @RequestBody Vehiculo vehiculoEditado) {
	    Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(id);
	    if (!vehiculoOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
	    
	    Vehiculo vehiculo = vehiculoOpt.get();
	    vehiculo.setModelo(vehiculoEditado.getModelo());
	    vehiculo.setMarca(vehiculoEditado.getMarca());
	    vehiculo.setAno(vehiculoEditado.getAno());
	    vehiculo.setDescripcion(vehiculoEditado.getDescripcion());
	    vehiculo.setInfoAdicional(vehiculoEditado.getInfoAdicional());
	    vehiculo.setPrecio(vehiculoEditado.getPrecio());
	    vehiculo.setEnVenta(vehiculoEditado.getEnVenta());
	    vehiculo.setDisponibilidadDeVenta(vehiculoEditado.getEnVenta()); // Asumimos que son lo mismo
	    // Actualiza la sección si es necesario
	    if(vehiculoEditado.getSeccion() != null) {
	        vehiculo.setSeccion(vehiculoEditado.getSeccion());
	    }
	    
	    Vehiculo actualizado = vehiculoRepository.save(vehiculo);
	    return ResponseEntity.ok(actualizado);
	}

	// Eliminar vehículo
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> eliminarVehiculo(@PathVariable Long id) {
		if (!vehiculoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		vehiculoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	// Ver vehículo por id
	@GetMapping("/{id}")
	public ResponseEntity<VehiculoDTO> verVehiculo(@PathVariable Long id) {
	    Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(id);
	    if (!vehiculoOpt.isPresent()) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok(new VehiculoDTO(vehiculoOpt.get()));
	}


	@GetMapping("/usuario/{usuarioId}")
	public ResponseEntity<List<VehiculoDTO>> verVehiculosPorUsuario(@PathVariable Long usuarioId) {
	    List<VehiculoDTO> vehiculos = vehiculoRepository.findByUsuario_Id(usuarioId)
	        .stream()
	        .map(VehiculoDTO::new)
	        .toList();

	    return ResponseEntity.ok(vehiculos);
	}


	@GetMapping("/buscar")
	public ResponseEntity<List<VehiculoDTO>> buscarVehiculos(@RequestParam String query) {
	    List<VehiculoDTO> encontrados = vehiculoRepository.findAll().stream()
	        .filter(v -> v.getModelo().toLowerCase().contains(query.toLowerCase()))
	        .map(VehiculoDTO::new)
	        .toList();

	    return ResponseEntity.ok(encontrados);
	}


	@GetMapping("/venta/publicos")
	public ResponseEntity<List<VehiculoDTO>> verVehiculosVentaPublicos() {
	    List<VehiculoDTO> vehiculos = vehiculoRepository.findAll().stream()
	        .filter(v -> Boolean.TRUE.equals(v.getDisponibilidadDeVenta())
	                && Boolean.FALSE.equals(v.getUsuario().getPrivado()))
	        .map(VehiculoDTO::new)
	        .toList();

	    return ResponseEntity.ok(vehiculos);
	}

}
