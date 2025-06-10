package com.carbik.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.carbik.models.Vehiculo;

public class VehiculoDTO {
    private Long id;
    private String modelo;
    private String marca;
    private Integer ano;
    private String descripcion;
    private String infoAdicional;
    private Double precio;
    private Boolean publicado;
    private Boolean enVenta;
    private Boolean disponibilidadDeVenta;
    private UsuarioDTO usuario;
    private List<FotoVehiculoDTO> fotos;
    private Long seccionId; // Solo ID, no entidad completa


    public Long getSeccionId() {
		return seccionId;
	}


	public void setSeccionId(Long seccionId) {
		this.seccionId = seccionId;
	}


	public VehiculoDTO() {}

    
    public VehiculoDTO(Vehiculo vehiculo) {
        this.id = vehiculo.getId();
        this.modelo = vehiculo.getModelo();
        this.marca = vehiculo.getMarca();
        this.ano = vehiculo.getAno();
        this.descripcion = vehiculo.getDescripcion();
        this.infoAdicional = vehiculo.getInfoAdicional();
        this.precio = vehiculo.getPrecio();
        this.publicado = vehiculo.getPublicado();
        this.enVenta = vehiculo.getEnVenta();
        this.disponibilidadDeVenta = vehiculo.getDisponibilidadDeVenta();
        this.usuario = new UsuarioDTO(vehiculo.getUsuario());
        this.fotos = vehiculo.getFotos().stream()
            .map(FotoVehiculoDTO::new)
            .collect(Collectors.toList());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getInfoAdicional() {
		return infoAdicional;
	}

	public void setInfoAdicional(String infoAdicional) {
		this.infoAdicional = infoAdicional;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Boolean getPublicado() {
		return publicado;
	}

	public void setPublicado(Boolean publicado) {
		this.publicado = publicado;
	}

	public Boolean getEnVenta() {
		return enVenta;
	}

	public void setEnVenta(Boolean enVenta) {
		this.enVenta = enVenta;
	}

	public Boolean getDisponibilidadDeVenta() {
		return disponibilidadDeVenta;
	}

	public void setDisponibilidadDeVenta(Boolean disponibilidadDeVenta) {
		this.disponibilidadDeVenta = disponibilidadDeVenta;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public List<FotoVehiculoDTO> getFotos() {
		return fotos;
	}

	public void setFotos(List<FotoVehiculoDTO> fotos) {
		this.fotos = fotos;
	}
    
    
}
