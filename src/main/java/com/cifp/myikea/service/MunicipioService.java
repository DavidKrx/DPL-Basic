package com.cifp.myikea.service;

import com.cifp.myikea.model.Municipio;
import com.cifp.myikea.repository.MunicipioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MunicipioService {
    @Autowired
    private MunicipioRepository municipioRepository;

    public MunicipioService(MunicipioRepository municipioRepository) {
        this.municipioRepository = municipioRepository;
    }

    public List<Municipio> getAllMunicipios() {
        return municipioRepository.findAll();
    }

    public Municipio findById(Integer id) {
        return municipioRepository.findById(id).orElse(null);
    }
}
