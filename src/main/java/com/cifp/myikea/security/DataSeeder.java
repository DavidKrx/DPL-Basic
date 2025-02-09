package com.cifp.myikea.security;

import com.cifp.myikea.model.Carrito;
import com.cifp.myikea.model.Role;
import com.cifp.myikea.model.User;
import com.cifp.myikea.repository.CarritoRepository;
import com.cifp.myikea.repository.RoleRepository;
import com.cifp.myikea.repository.UserRepository;
import com.cifp.myikea.service.CarritoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CarritoService carritoRepository;

    public DataSeeder(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder,CarritoService carritoRepository){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.carritoRepository=carritoRepository;
    }

    @Override
    public void run(String... args) {
        // Crear roles si no existen
        Role userRole = roleRepository.findByNombre("USER").orElseGet(() -> {
            Role role = new Role();
            role.setNombre("USER");
            return roleRepository.save(role);
        });

        Role managerRole = roleRepository.findByNombre("MANAGER").orElseGet(() -> {
            Role role = new Role();
            role.setNombre("MANAGER");
            return roleRepository.save(role);
        });

        Role adminRole = roleRepository.findByNombre("ADMIN").orElseGet(() -> {
            Role role = new Role();
            role.setNombre("ADMIN");
            return roleRepository.save(role);
        });

        // Crear usuarios si no existen
        if (userRepository.count() == 0) {
            User user = new User();
            user.setNombre("user");
            user.setEmail("user@myikea.com");
            user.setPassword(passwordEncoder.encode("Qwer123!"));
            user.setRoles(List.of(userRole));
            userRepository.save(user);
            Carrito carrito= new Carrito();
            carrito.setUser(user);
            carritoRepository.saveCarrito(carrito);

            User manager = new User();
            manager.setNombre("manager");
            manager.setEmail("manager@myikea.com");
            manager.setPassword(passwordEncoder.encode("Qwer123!"));
            manager.setRoles(List.of(managerRole));
            userRepository.save(manager);
            Carrito carrit= new Carrito();
            carrit.setUser(manager);
            carritoRepository.saveCarrito(carrit);

            User admin1 = new User();
            admin1.setNombre("admin1");
            admin1.setEmail("admin1@myikea.com");
            admin1.setPassword(passwordEncoder.encode("Qwer123!"));
            admin1.setRoles(List.of(adminRole, managerRole));
            userRepository.save(admin1);
            Carrito carri= new Carrito();
            carri.setUser(admin1);
            carritoRepository.saveCarrito(carri);


            User admin2 = new User();
            admin2.setNombre("admin2");
            admin2.setEmail("admin2@myikea.com");
            admin2.setPassword(passwordEncoder.encode("Qwer123!"));
            admin2.setRoles(List.of(adminRole, managerRole));
            userRepository.save(admin2);
            Carrito carr= new Carrito();
            carr.setUser(admin2);
            carritoRepository.saveCarrito(carr);
        }
    }
}