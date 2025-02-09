package com.cifp.myikea.controller;

import com.cifp.myikea.model.User;
import com.cifp.myikea.repository.UserRepository;
import com.cifp.myikea.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "security/login"; // Una vista de login personalizada
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "security/register"; // Una vista de login personalizada
    }
    //El userService esta null
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user) {
        userService.register(user);
        return "redirect:/";
    }

    @GetMapping("/users/")
    public String listarUsers(Model model,Authentication authentication) {
        User usuarioLogeado= (User) authentication.getPrincipal();
        model.addAttribute("users", userService.ListWithoutMyUser(usuarioLogeado.getId()));
        return "user/index"; // Página de registro
    }

    @GetMapping("/users/delete/{id}")
    public String delete(@PathVariable Integer id) {
        userService.eliminarUsuario(id);
        return "redirect:/users/"; // Página de registro
    }
}
