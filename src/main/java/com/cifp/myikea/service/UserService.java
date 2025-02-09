package com.cifp.myikea.service;

import com.cifp.myikea.model.Role;
import com.cifp.myikea.model.User;
import com.cifp.myikea.repository.RoleRepository;
import com.cifp.myikea.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Buscar el rol USER de forma segura
        Role userRole = roleRepository.findByNombre("USER")
                .orElseThrow(() -> new RuntimeException("El rol 'USER' no existe en la base de datos"));

        // Asignar el rol al usuario y guardar
        user.setRoles(Collections.singletonList(userRole));
        userRepository.save(user);
    }

    public List<User> ListWithoutMyUser(Integer user) {
        return userRepository.findAll().stream().filter(u -> u.getId() != user).toList();
    }

    public void initializeRoles() {
        // Crear roles si no existen
        if (roleRepository.findByNombre("USER").isEmpty()) {
            Role roleUser = new Role();
            roleUser.setNombre("USER");
            roleRepository.save(roleUser);
        }

        if (roleRepository.findByNombre("MANAGER").isEmpty()) {
            Role roleManager = new Role();
            roleManager.setNombre("MANAGER");
            roleRepository.save(roleManager);
        }

        if (roleRepository.findByNombre("ADMIN").isEmpty()) {
            Role roleAdmin = new Role();
            roleAdmin.setNombre("ADMIN");
            roleRepository.save(roleAdmin);
        }
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void eliminarUsuario(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

        /*
        boolean isAdmin = user.getRoles().stream()
                .anyMatch(role -> role.getNombre().equals("ADMIN"));*/

        userRepository.deleteById(id);
    }


}