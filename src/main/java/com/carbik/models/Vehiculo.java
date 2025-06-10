package com.carbik.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Seccion getSeccion() {
		return seccion;
	}
	public void setSeccion(Seccion seccion) {
		this.seccion = seccion;
	}
	@ManyToOne
    private Usuario usuario;

    private String modelo;
    private String marca;
    private Integer ano;
    private String descripcion;
    private String infoAdicional;
    private Double precio;
    private Boolean publicado;
    private Boolean enVenta;
    private Boolean disponibilidadDeVenta;
    
    @ManyToOne
    private Seccion seccion;


    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FotoVehiculo> fotos = new ArrayList<>();
    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LikeVehiculo> likes = new ArrayList<>();



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public Integer getAno() { return ano; }
    public void setAno(Integer ano) { this.ano = ano; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getInfoAdicional() { return infoAdicional; }
    public void setInfoAdicional(String infoAdicional) { this.infoAdicional = infoAdicional; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Boolean getPublicado() { return publicado; }
    public void setPublicado(Boolean publicado) { this.publicado = publicado; }

    public Boolean getEnVenta() { return enVenta; }
    public void setEnVenta(Boolean enVenta) { this.enVenta = enVenta; }

    public Boolean getDisponibilidadDeVenta() { return disponibilidadDeVenta; }
    public void setDisponibilidadDeVenta(Boolean disponibilidadDeVenta) {
        this.disponibilidadDeVenta = disponibilidadDeVenta;
    }

    public List<FotoVehiculo> getFotos() { return fotos; }
    public void setFotos(List<FotoVehiculo> fotos) { this.fotos = fotos; }
}