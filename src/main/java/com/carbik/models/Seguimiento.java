package com.carbik.models;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Usuario seguidor;

    @ManyToOne
    private Usuario seguido;

    private LocalDateTime fechaSeguimiento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getSeguidor() {
		return seguidor;
	}

	public void setSeguidor(Usuario seguidor) {
		this.seguidor = seguidor;
	}

	public Usuario getSeguido() {
		return seguido;
	}

	public void setSeguido(Usuario seguido) {
		this.seguido = seguido;
	}

	public LocalDateTime getFechaSeguimiento() {
		return fechaSeguimiento;
	}

	public void setFechaSeguimiento(LocalDateTime fechaSeguimiento) {
		this.fechaSeguimiento = fechaSeguimiento;
	}
    
}

