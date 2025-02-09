package com.cifp.myikea.controller;

import com.cifp.myikea.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class MainController {
    @ModelAttribute
    public void allPages(Model model, Authentication authentication) {
        boolean isLogged= authentication!=null;
        String usuarioLogeado="";
        boolean isAdmin=false;
        boolean isManager=false;
        if(isLogged){
            User pedro=(User) authentication.getPrincipal();
            usuarioLogeado=pedro.getEmail();
            isAdmin=authentication.getAuthorities().stream()
                    .anyMatch(auth-> auth.getAuthority().equals("ROLE_ADMIN"));
            isManager=authentication.getAuthorities().stream()
                    .anyMatch(auth-> auth.getAuthority().equals("ROLE_MANAGER"));
        }
        model.addAttribute("LOGED", isLogged);
        model.addAttribute("ADMIN", isAdmin);
        model.addAttribute("MANAGER", isManager);
        model.addAttribute("user", usuarioLogeado );
    }
}
