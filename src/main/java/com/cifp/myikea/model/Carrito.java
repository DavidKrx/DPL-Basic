package com.cifp.myikea.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    private Double precioTotal;

    private boolean done=false;

    // Relación @ManyToMany con la tabla de productos
    @ManyToMany
    @JoinTable(
            name = "carrito_producto",  // Tabla de unión corregida
            joinColumns = @JoinColumn(name = "carrito_id"),  // Clave foránea de carrito
            inverseJoinColumns = @JoinColumn(name = "product_id")  // Clave foránea de producto
    )
    private List<Producto> productos;

    // Relación ManyToOne con Usuario
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;

    public Carrito() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
