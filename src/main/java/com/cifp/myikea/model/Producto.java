package com.cifp.myikea.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;


@Entity
@Table(name="productoffer")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Integer id;  // ID del producto

    @Size(max=100)
    @Column(name = "product_name",length = 100)  // Nombre del producto
    private String nombre;

    @NotNull
    @Column(name = "product_price")  // Precio del producto
    private double precio;

    @NotNull
    @Column(name = "product_stock")  // Stock del producto
    private Integer stock;

    @Column(name = "product_picture",length = 255)
    private String imagen;

    // Relación ManyToOne con Municipio
    @ManyToOne
    @JoinColumn(name = "id_municipio", nullable = false)
    private Municipio municipio;

    // Relación ManyToMany con Carrito
    @ManyToMany(mappedBy = "productos")  // Relación inversa con la clase Carrito
    private List<Carrito> carritos;

    public Producto() {
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public List<Carrito> getCarritos() {
        return carritos;
    }

    public void setCarritos(List<Carrito> carritos) {
        this.carritos = carritos;
    }
}
