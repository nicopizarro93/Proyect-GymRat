package com.example.ms_rutinas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_rutinas.model.Rutina;
import com.example.ms_rutinas.repository.RutinaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RutinaServiceIMPL implements RutinaService {

    private final RutinaRepository rutinaRepository;

    @Override
    public Rutina guardarRutina(Rutina rutina) {
       return rutinaRepository.save(rutina);
    }

    @Override
    public Rutina buscarPorId(Long id) {
      return rutinaRepository.findById(id)
      .orElseThrow(()-> new RuntimeException("Rutina no encontrada"));
    }

    @Override
    public List<Rutina> listarRutinas() {
        return rutinaRepository.findAll();
    }

    @Override
    public void eliminarRutina(Long id) {
        rutinaRepository.deleteById(id);
    }

}
