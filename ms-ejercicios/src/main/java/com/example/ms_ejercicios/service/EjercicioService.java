package com.example.ms_ejercicios.service;

import java.util.List;
import java.util.Optional;

import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;

public interface EjercicioService {

    Ejercicio guardarEjercicio(Ejercicio ejercicio);
    Ejercicio buscarPorId(Long id);
    List<Ejercicio>listarEjercicios();
    List<Ejercicio> listarPorGrupoMuscular(GrupoMuscularEnum grupoMuscular);
    void eliminarPorId(Long id);
    Optional<Ejercicio> buscarPorNombre(String nombre);
}
