package com.cifp.myikea.repository;

import com.cifp.myikea.model.Producto;
import com.cifp.myikea.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    Optional<Producto> findById(Integer id);
}