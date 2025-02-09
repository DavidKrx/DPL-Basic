package com.cifp.myikea.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "provincias")
public class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Agregado para clave autoincremental
    @Column(name = "id_provincia") // Corregido JoinColumn por Column
    private Integer id;

    @NotNull
    @Column(length = 30, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private List<Municipio> municipios = new ArrayList<>();

    public Provincia() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }
}
