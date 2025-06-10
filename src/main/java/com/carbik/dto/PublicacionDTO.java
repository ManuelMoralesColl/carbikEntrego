package com.carbik.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.carbik.models.Publicacion;

public class PublicacionDTO {
    private Long id;
    private String pieDeFoto;
    private LocalDateTime fechaPublicacion;
    private UsuarioDTO usuario;
    private List<FotoPublicacionDTO> fotos;

    public PublicacionDTO() {}

    public PublicacionDTO(Publicacion pub) {
        this.id = pub.getId();
        this.pieDeFoto = pub.getPieDeFoto();
        this.fechaPublicacion = pub.getFechaPublicacion();
        this.usuario = new UsuarioDTO(pub.getUsuario());
        this.fotos = pub.getFotos().stream()
            .map(FotoPublicacionDTO::new)
            .collect(Collectors.toList());
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPieDeFoto() {
		return pieDeFoto;
	}

	public void setPieDeFoto(String pieDeFoto) {
		this.pieDeFoto = pieDeFoto;
	}

	public LocalDateTime getFechaPublicacion() {
		return fechaPublicacion;
	}

	public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}

	public List<FotoPublicacionDTO> getFotos() {
		return fotos;
	}

	public void setFotos(List<FotoPublicacionDTO> fotos) {
		this.fotos = fotos;
	}
    
}
