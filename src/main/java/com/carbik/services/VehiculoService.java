package com.carbik.services;
import com.carbik.models.Vehiculo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
public interface VehiculoService {
	  Vehiculo subirVehiculo(Vehiculo vehiculo);
	    Vehiculo editarVehiculo(Long id, Vehiculo vehiculo);
	    void eliminarVehiculo(Long id);
	    List<Vehiculo> listarVehiculosPublicos();
	    List<Vehiculo> listarVehiculosEnVentaPublicos();
	    List<Vehiculo> listarVehiculosDeUsuariosSeguidos(Long usuarioId);
	    List<Vehiculo> buscarVehiculosPorModelo(String modelo);
	    Optional<Vehiculo> buscarPorId(Long id);
	    void darMeGusta(Long vehiculoId, Long usuarioId);
	    void quitarMeGusta(Long vehiculoId, Long usuarioId);
	    
	    @Query("SELECT v FROM Vehiculo v WHERE v.seccion.id = :id OR v.seccion.parent.id = :id")
	    List<Vehiculo> findBySeccionOrSubsecciones(Long id);
	    @Query("SELECT v FROM Vehiculo v WHERE v.seccion.id = :id AND v.seccion.parent IS NULL")
	    List<Vehiculo> findDirectInPadre(Long id);

	}