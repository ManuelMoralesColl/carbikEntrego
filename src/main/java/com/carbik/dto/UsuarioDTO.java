package com.carbik.dto;

import com.carbik.models.Usuario;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    private String correo;
    private String fotoPerfil;
    private Boolean verificado;
    private Boolean privado;

    public UsuarioDTO() {}
    // Constructor
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apellidos = usuario.getApellidos();
        this.nombreUsuario = usuario.getNombreUsuario();
        this.correo = usuario.getCorreo();
        this.fotoPerfil = usuario.getFotoPerfil();
        this.verificado = usuario.getVerificado();
        this.privado = usuario.getPrivado();

    }

	public Boolean getPrivado() {
		return privado;
	}
	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}
    
}
