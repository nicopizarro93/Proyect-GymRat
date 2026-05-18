package com.example.ms_rutinas.dto;

import lombok.Data;

/**
 * DTO que representa la información de un ejercicio recibido desde el microservicio de ejercicios.
 * Se utiliza para transportar datos de ejercicios sin exponer directamente la entidad externa.
 */
@Data
public class EjercicioResponseDTO {

    /**
     * Identificador único del ejercicio.
     */
    private Long idEjercicio;

    /**
     * Nombre descriptivo del ejercicio.
     */
    private String nombreEjercicio;

    /**
     * Grupo muscular principal trabajado por el ejercicio.
     */
    private String grupoMuscular;

    /**
     * Nivel de dificultad asociado al ejercicio.
     */
    private String dificultad;
}
