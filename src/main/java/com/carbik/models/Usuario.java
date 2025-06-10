package com.carbik.models;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint; // Esta es la importación que faltaba

@Entity
@Table(name = "usuarios", 
uniqueConstraints = {
    @UniqueConstraint(columnNames = "nombreUsuario"),
    @UniqueConstraint(columnNames = "correo")
})
public class Usuario  implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidos;
    private String nombreUsuario;
    private String correo;
    private String contrasena;
    private String fotoPerfil;
    private Boolean verificado = false; // Nuevo campo
    private LocalDateTime fechaRegistro;
    private Boolean privado; // Para la privacidad de la cuenta

    @OneToMany(mappedBy = "seguidor",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Seguimiento> seguidos;

    @OneToMany(mappedBy = "seguido",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Seguimiento> seguidores;
    

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

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contraseña) {
		this.contrasena = contraseña;
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

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public Boolean getPrivado() {
		return privado;
	}

	public void setPrivado(Boolean privado) {
		this.privado = privado;
	}

	public List<Seguimiento> getSeguidos() {
		return seguidos;
	}

	public void setSeguidos(List<Seguimiento> seguidos) {
		this.seguidos = seguidos;
	}

	public List<Seguimiento> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<Seguimiento> seguidores) {
		this.seguidores = seguidores;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // o devuelve roles si los usas
    }

    @Override
    public String getPassword() {
        return contrasena;
    }

    @Override
    public String getUsername() {
        return nombreUsuario; // o correo si prefieres
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}