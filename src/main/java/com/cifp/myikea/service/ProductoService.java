package com.cifp.myikea.service;


import com.cifp.myikea.model.Producto;
import com.cifp.myikea.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Autowired
    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> getAllProduct() {
        return productoRepository.findAll();
    }

    public Optional<Producto> detailsProduct(Integer id) {
        return productoRepository.findById(id);
    }

    public Producto createProduct(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Integer id, Producto producto) {
        return productoRepository.findById(id).map(existing -> {
            existing.setNombre(producto.getNombre());
            existing.setPrecio(producto.getPrecio());
            existing.setStock(producto.getStock());
            existing.setImagen(producto.getImagen());
            existing.setMunicipio(producto.getMunicipio());
            return productoRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminarProducto(Integer id) {
        productoRepository.deleteById(id);
    }
}
