package com.cifp.myikea.service;

import com.cifp.myikea.model.Producto;
import com.cifp.myikea.model.Provincia;
import com.cifp.myikea.repository.ProductoRepository;
import com.cifp.myikea.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {
    private final ProvinciaRepository provinciaRepository;

    @Autowired
    public ProvinciaService(ProvinciaRepository provinciaRepository) {
        this.provinciaRepository = provinciaRepository;
    }

    public List<Provincia> getAllProvincia() {
        return provinciaRepository.findAll();
    }
}
