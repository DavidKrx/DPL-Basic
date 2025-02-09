package com.cifp.myikea.controller;


import com.cifp.myikea.model.Carrito;
import com.cifp.myikea.model.Producto;
import com.cifp.myikea.model.User;
import com.cifp.myikea.repository.CarritoRepository;
import com.cifp.myikea.repository.ProductoRepository;
import com.cifp.myikea.service.CarritoService;
import com.cifp.myikea.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/carrito/")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public String listarCarrito(Model model,Authentication authentication) {
        User pedro=(User) authentication.getPrincipal();
        int idUsuario=pedro.getId();
        model.addAttribute("carrito",carritoRepository.findCarritoByUserAndDoneFalse(pedro));
        return "carrito/index"; // Página de registro
    }

    @GetMapping("add/{id}")
    public String agregarAlCarrito(@PathVariable Integer id,Authentication authentication) {
            // Obtener el usuario autenticado
            User pedro = (User) authentication.getPrincipal();
            int idUsuario = pedro.getId();

            // Buscar el carrito activo del usuario (sin finalizar)
            Carrito carrito = carritoRepository.findAll()
                    .stream()
                    .filter(c -> c.getUser().getId() == idUsuario && !c.isDone())
                    .findFirst()
                    .orElseThrow();

            // Buscar el producto por su id
            Optional<Producto> optionalProducto = productoRepository.findById(id);
            if (optionalProducto.isEmpty()) {
                return "redirect:/error?message=Producto+no+encontrado";
            }

            Producto producto = optionalProducto.get();

            // Agregar el producto al carrito si tiene stock
            carrito.setPrecioTotal(
                    (carrito.getPrecioTotal() == null ? 0.0 : carrito.getPrecioTotal()) + producto.getPrecio()
            );
            carrito.getProductos().add(producto);

            carritoRepository.save(carrito);
            //productoRepository.save(producto);
            return "redirect:/carrito/";
        }

    @GetMapping("remove/{id}")
    public String borrarDelCarrito(@PathVariable Integer id, Authentication authentication) {
        // Obtener el usuario autenticado
        User pedro = (User) authentication.getPrincipal();
        int idUsuario = pedro.getId();

        // Buscar el carrito activo del usuario (sin finalizar)
        Carrito carrito = carritoRepository.findAll()
                .stream()
                .filter(c -> c.getUser().getId() == idUsuario && !c.isDone())
                .findFirst()
                .orElse(null);

        if (carrito == null) {
            return "redirect:/error?message=Carrito+no+encontrado";
        }

        // Buscar el producto en el carrito
        Optional<Producto> optionalProducto = productoRepository.findById(id);
        if (optionalProducto.isEmpty()) {
            return "redirect:/error?message=Producto+no+encontrado";
        }
        Producto producto = optionalProducto.get();
        // Verificar si el producto está en el carrito
        if (carrito.getProductos().contains(producto)) {
            carrito.getProductos().remove(producto);
            carrito.setPrecioTotal(
                    (carrito.getPrecioTotal() == null ? 0.0 : carrito.getPrecioTotal()) - producto.getPrecio()
            );
            carritoRepository.save(carrito);
            //productoRepository.save(producto);
            return "redirect:/carrito/";
        }

        return "redirect:/error?message=El+producto+no+está+en+el+carrito";
    }

    @GetMapping("guardar/")
    public String guardarCarritoEnPedido(Authentication authentication) {
        User pedro = (User) authentication.getPrincipal();
        int idUsuario = pedro.getId();

        // Buscar el carrito activo del usuario (sin finalizar)
        Carrito carrito = carritoRepository.findAll()
                .stream()
                .filter(c -> c.getUser().getId() == idUsuario && !c.isDone())
                .findFirst()
                .orElse(null);

        if (carrito == null) {
            return "redirect:/error?message=Carrito+no+encontrado";
        }
        // Verificar si el producto está en el carrito
        if (!carrito.getProductos().isEmpty()) {
            carrito.setDone(true);
            carrito.setFecha(new Date());
            carritoRepository.save(carrito);

            // Si no hay carrito activo, creamos uno nuevo
            Carrito nuevoCarrito = new Carrito();
            nuevoCarrito.setDone(false);
            nuevoCarrito.setPrecioTotal(0.0);
            nuevoCarrito.setUser(pedro);
            carritoRepository.save(nuevoCarrito);
            return "redirect:/pedido/";
        }
        return "redirect:/error?message=El+producto+no+está+en+el+carrito";
    }
}
