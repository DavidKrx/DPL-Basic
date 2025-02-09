package com.cifp.myikea.controller;

import com.cifp.myikea.model.Municipio;
import com.cifp.myikea.model.Producto;
import com.cifp.myikea.model.Provincia;
import com.cifp.myikea.repository.MunicipioRepository;
import com.cifp.myikea.repository.ProductoRepository;
import com.cifp.myikea.repository.ProvinciaRepository;
import com.cifp.myikea.service.MunicipioService;
import com.cifp.myikea.service.ProductoService;
import com.cifp.myikea.service.ProvinciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/productos/")
public class ProductoController {
    @Autowired
    private ProductoService productoService;
    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private MunicipioService municipioService;

    @Autowired
    private MunicipioRepository municipioRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public String listProduct(Model model, Authentication authentication) {
        boolean isAdmin= authentication.getAuthorities().stream()
                .anyMatch(auth-> auth.getAuthority().equals("ROLE_ADMIN"));

        boolean isManager= authentication.getAuthorities().stream()
                .anyMatch(auth-> auth.getAuthority().equals("ROLE_MANAGER"));

        List<Producto> productos = productoService.getAllProduct();
        productos.forEach(producto -> {
            String imagePath = "src/main/resources/static/imagenes/" + producto.getImagen();
            if (producto.getImagen() == null || !Files.exists(Paths.get(imagePath))) {
                producto.setImagen("defecto.png");
            }
        });
        model.addAttribute("productos", productos);
        model.addAttribute("ADMIN", isAdmin);
        model.addAttribute("MANAGER", isManager);
        return "productos/index";
    }
    @GetMapping("details/{id}")
    public String detailsProduct(@PathVariable Integer id, Model model){
        Producto producto = productoService.detailsProduct(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));


        String imagePath = "src/main/resources/static/imagenes/" + producto.getImagen();
        if (producto.getImagen() == null || !Files.exists(Paths.get(imagePath))) {
            producto.setImagen("defecto.png");
        }
        model.addAttribute("producto",producto);
        return "productos/details";
    }
    // Mostrar el formulario para crear un nuevo producto
    @GetMapping("/create/")
    public String mostrarFormulario(Model model) {
        List<Provincia> provincias = provinciaRepository.findAll();  // Obtener todas las provincias
        model.addAttribute("producto", new Producto());
        model.addAttribute("provincias", provincias);
        return "productos/create";
    }

    @GetMapping("/municipios/{provinciaId}")
    @ResponseBody
    public List<Municipio> obtenerMunicipiosPorProvincia(@PathVariable Integer provinciaId) {
        Provincia provincia = provinciaRepository.findById(provinciaId).orElse(null);
        if (provincia != null) {
            return municipioRepository.findByProvincia(provincia);  // Retorna todos los municipios de la provincia
        }
        return Collections.emptyList();  // Devuelve una lista vacía si no se encuentra la provincia
    }

    // Procesar el formulario para guardar el producto
    @PostMapping("/create/")
    public String guardarProducto(@ModelAttribute Producto producto) {
        producto.setImagen("defecto.png");
        productoRepository.save(producto);
        return "redirect:/productos/";
    }

}
