package com.cifp.myikea.repository;

import com.cifp.myikea.model.Municipio;
import com.cifp.myikea.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MunicipioRepository extends JpaRepository<Municipio,Integer> {
    List<Municipio> findByProvincia(Provincia provincia);
}
