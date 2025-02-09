package com.cifp.myikea.repository;

import com.cifp.myikea.model.Carrito;
import com.cifp.myikea.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito,Integer> {
    public List<Carrito> findCarritoByUserAndDoneFalse(User user);
    public List<Carrito> findCarritoByUserAndDoneTrue(User user);
}
