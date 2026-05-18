package com.example.ms_rutinas.dto;

import java.util.List;

import lombok.Data;

/**
 * DTO utilizado para entregar una rutina con información detallada de sus ejercicios.
 * Combina los datos propios de la rutina con los ejercicios consultados desde otro microservicio.
 */
@Data
public class RutinaResponseDTO {

    /**
     * Identificador único de la rutina.
     */
    private Long idRutina;

    /**
     * Nombre de la rutina.
     */
    private String nombreRutina;

    /**
     * Nivel de dificultad de la rutina.
     */
    private String dificultad;

    /**
     * Cantidad de días recomendados para realizar la rutina.
     */
    private Integer dias;

    /**
     * Lista de ejercicios detallados que componen la rutina.
     */
    private List<EjercicioResponseDTO>ejercicios;
}
