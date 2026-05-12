package com.example.ms_ejercicios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {
    Optional<Ejercicio> findByNombreEjercicio(String nombreEjercicio);
    List<Ejercicio> findByGrupoMuscular(GrupoMuscularEnum grupoMuscular);
}
