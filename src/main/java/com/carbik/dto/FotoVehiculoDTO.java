package com.carbik.dto;

import com.carbik.models.FotoVehiculo;

public class FotoVehiculoDTO {
    private Long id;
    private String url;

    public FotoVehiculoDTO(FotoVehiculo foto) {
        this.id = foto.getId();
        this.url = foto.getUrl();
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}
