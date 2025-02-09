package com.cifp.myikea.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pedido/")
public class PedidoController {
    @Autowired
    private CarritoRepository carritoRepository;

    @GetMapping
    public String listarPedido(Model model, Authentication authentication) {
        User pedro=(User) authentication.getPrincipal();
        int idUsuario=pedro.getId();
        model.addAttribute("pedidos",carritoRepository.findCarritoByUserAndDoneTrue(pedro));
        return "pedido/index"; // Página de registro
    }

    @GetMapping("/details/{id}")
    public String detallesPedido(@PathVariable Integer id, Model model, Authentication authentication) {
        User pedro=(User) authentication.getPrincipal();
        int idUsuario=pedro.getId();
        model.addAttribute("pedido",carritoRepository.findCarritoByUserAndDoneTrue(pedro).stream().filter(carrito-> carrito.getId()==id));
        return "pedido/details"; // Página de registro
    }
}
