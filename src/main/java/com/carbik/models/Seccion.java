package com.carbik.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Seccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;
    
    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL)
    private List<Publicacion> publicaciones = new ArrayList<>();
    // Relación con categoría padre
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Seccion parent;

    // Relación con subcategorías (inversa)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Seccion> subcategorias;

    // Getters y Setters
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Seccion getParent() {
        return parent;
    }

    public void setParent(Seccion parent) {
        this.parent = parent;
    }

    public List<Seccion> getSubcategorias() {
        return subcategorias;
    }

    public void setSubcategorias(List<Seccion> subcategorias) {
        this.subcategorias = subcategorias;
    }
}
