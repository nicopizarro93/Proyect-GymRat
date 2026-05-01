package com.example.ms_ejercicios.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_ejercicios.model.Ejercicio;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    Optional<Ejercicio> findByNombreEjercicio(String nombreEjercicio);
}
