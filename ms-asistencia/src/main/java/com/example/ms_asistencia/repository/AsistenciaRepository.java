package com.example.ms_asistencia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ms_asistencia.model.Asistencia;

/**
 * Repositorio encargado del acceso a datos de la entidad Asistencia.
 * Extiende JpaRepository para disponer de operaciones CRUD básicas.
 */
@Repository
public interface AsistenciaRepository extends JpaRepository<Asistencia, Long> {

    /**
     * Busca todas las asistencias registradas para un atleta específico.
     *
     * @param rutAtleta RUT del atleta consultado.
     * @return lista de asistencias asociadas al RUT indicado.
     */
    List<Asistencia> findByRutAtleta(String rutAtleta);
}