package com.example.ms_asistencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_asistencia.model.Asistencia;

@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> { // Trabajaremos unicamente con la tabla asistencia y con el extends traeremos los metodos.
    
    // Metodo para buscar todo el historial de un atleta
    List<Asistencia> findByRutAtleta(String rutAtleta); 
}