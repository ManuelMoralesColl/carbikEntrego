package com.carbik.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private Usuario emisor;

    private String contenido;

    @ManyToOne
    private Publicacion publicacionCompartida; // opcional

    @ManyToOne
    private Vehiculo vehiculoCompartido; // opcional

    private LocalDateTime fechaEnvio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public Usuario getEmisor() {
		return emisor;
	}

	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Publicacion getPublicacionCompartida() {
		return publicacionCompartida;
	}

	public void setPublicacionCompartida(Publicacion publicacionCompartida) {
		this.publicacionCompartida = publicacionCompartida;
	}

	public Vehiculo getVehiculoCompartido() {
		return vehiculoCompartido;
	}

	public void setVehiculoCompartido(Vehiculo vehiculoCompartido) {
		this.vehiculoCompartido = vehiculoCompartido;
	}

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}
    
    
}
