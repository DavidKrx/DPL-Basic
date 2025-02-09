package com.cifp.myikea.service;

import com.cifp.myikea.model.Carrito;
import com.cifp.myikea.model.Producto;
import com.cifp.myikea.model.User;
import com.cifp.myikea.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CarritoService {
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoService productoService;

    public List<Carrito> getPedidos() {
        return carritoRepository.findAll();
    }
    //Crear un getCarritoNoFinalizado

    public List<Carrito> getCarrito() {
        return carritoRepository.findAll().stream().filter(carrito -> carrito.isDone()!=true).toList();
    }
    //Cuando agregamos un producto al carrito
    public Carrito saveCarrito(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    public Carrito detailsCarrito(Integer id) {
        Optional<Carrito> optionalProducto = carritoRepository.findById(id);
        return optionalProducto.orElse(null);
    }

    public List<Carrito> carritoByUser(Integer userId) {
        List<Carrito> carDone=carritoRepository.findAll().stream().filter(d-> d.isDone() ==false).toList();
        return carritoRepository.findAll().stream().filter(c -> c.getUser().getId() != userId).toList();
    }
    public List<Carrito> pedidosByUser(Integer userId) {
        List<Carrito> carDone=carritoRepository.findAll().stream().filter(d-> d.isDone() !=false).toList();
        return carritoRepository.findAll().stream().filter(c -> c.getUser().getId() != userId).toList();
    }

}
