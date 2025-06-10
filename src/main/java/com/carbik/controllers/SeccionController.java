package com.carbik.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.carbik.dto.SeccionDTO;
import com.carbik.dto.VehiculoDTO;
import com.carbik.models.Seccion;
import com.carbik.models.Vehiculo;
import com.carbik.repository.VehiculoRepository;
import com.carbik.services.SeccionService;
import com.carbik.services.VehiculoService;

@RestController
@RequestMapping("/api/secciones")
public class SeccionController {
	private final VehiculoService vehiculoService;

	public SeccionController(VehiculoService vehiculoService){
		this.vehiculoService = vehiculoService;
	}
	
    @Autowired
    private SeccionService seccionService;

    // Obtener todas las categorías principales
    @GetMapping("/principales")
    public List<SeccionDTO> getCategoriasPrincipales() {
        return seccionService.obtenerCategoriasPrincipales();
        
    }@GetMapping("/seccion/{id}")
    public ResponseEntity<List<VehiculoDTO>> obtenerVehiculosPorSeccionYSubcategorias(@PathVariable Long id) {
        List<Vehiculo> vehiculos = vehiculoService.findBySeccionOrSubsecciones(id);
        List<VehiculoDTO> vehiculoDTOs = vehiculos.stream().map(VehiculoDTO::new).toList();
        return ResponseEntity.ok(vehiculoDTOs);
    }

    // Obtener subcategorías por nombre de categoría padre
    @GetMapping("/{nombre}/subcategorias")
    public List<Seccion> getSubcategorias(@PathVariable String nombre) {
        return seccionService.obtenerSubcategoriasDe(nombre);
    }
}

