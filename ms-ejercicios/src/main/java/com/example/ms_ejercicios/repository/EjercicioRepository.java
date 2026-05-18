package com.example.ms_ejercicios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;

/**
 * Repositorio encargado del acceso a datos de la entidad {@link Ejercicio}.
 * Extiende JpaRepository para obtener operaciones CRUD básicas.
 */
public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

    /**
     * Busca un ejercicio por su nombre exacto.
     *
     * @param nombreEjercicio nombre del ejercicio a buscar.
     * @return un Optional con el ejercicio si existe.
     */
    Optional<Ejercicio> findByNombreEjercicio(String nombreEjercicio);

    /**
     * Busca todos los ejercicios asociados a un grupo muscular específico.
     *
     * @param grupoMuscular grupo muscular usado como filtro.
     * @return lista de ejercicios encontrados.
     */
    List<Ejercicio> findByGrupoMuscular(GrupoMuscularEnum grupoMuscular);
}
