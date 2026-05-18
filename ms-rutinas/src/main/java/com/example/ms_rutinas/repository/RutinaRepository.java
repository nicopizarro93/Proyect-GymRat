package com.example.ms_rutinas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_rutinas.model.Rutina;

/**
 * Repositorio encargado del acceso a datos de las rutinas.
 * Hereda operaciones CRUD básicas desde JpaRepository.
 */
public interface RutinaRepository extends JpaRepository<Rutina,Long> {

}
