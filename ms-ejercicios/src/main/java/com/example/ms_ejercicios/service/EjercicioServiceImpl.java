package com.example.ms_ejercicios.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;
import com.example.ms_ejercicios.repository.EjercicioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EjercicioServiceImpl implements EjercicioService {
    
    private final EjercicioRepository ejercicioRepository;
    
    @Override
    public Ejercicio guardarEjercicio(Ejercicio ejercicio) {
       if(ejercicioRepository.findByNombreEjercicio(ejercicio.getNombreEjercicio()).isPresent()){
        throw new IllegalArgumentException("ya existe un ejercicio con ese nombre");
       }
       return ejercicioRepository.save(ejercicio);
    }

    @Override
    public Ejercicio buscarPorId(Long id) {
       return ejercicioRepository.findById(id)
       .orElseThrow(()-> new RuntimeException("ejercicio no encontrado"));
    }

    @Override
    public List<Ejercicio> listarEjercicios() {
       return ejercicioRepository.findAll();
    }

    @Override
    public void eliminarPorId(Long id) {
       ejercicioRepository.deleteById(id);
    }

    @Override
    public List<Ejercicio> listarPorGrupoMuscular(GrupoMuscularEnum grupoMuscular) {
      return ejercicioRepository.findByGrupoMuscular(grupoMuscular);
    }

    @Override
    public Ejercicio buscarPorNombre(String nombre) {
         return ejercicioRepository.findByNombreEjercicio(nombre)
             .orElseThrow(() -> new RuntimeException("ejercicio no encontrado"));
    }

}
