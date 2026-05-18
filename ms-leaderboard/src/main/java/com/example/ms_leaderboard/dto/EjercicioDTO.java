package com.example.ms_leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

/**
 * DTO que representa la información básica de un ejercicio.
 * Se utiliza para validar ejercicios consultados desde el microservicio correspondiente.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EjercicioDTO {

    /**
     * Identificador único del ejercicio.
     */
    private Long idEjercicio;

    /**
     * Nombre del ejercicio.
     */
    private String nombreEjercicio;
}
