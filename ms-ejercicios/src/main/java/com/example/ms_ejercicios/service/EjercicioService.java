package com.example.ms_ejercicios.service;

import java.util.List;

import com.example.ms_ejercicios.model.Ejercicio;

public interface EjercicioService {

    Ejercicio guardarEjercicio(Ejercicio ejercicio);
    Ejercicio buscarPorId(Long id);
    List<Ejercicio>listarEjercicios();
    void eliminarPorId(Long id);
}
