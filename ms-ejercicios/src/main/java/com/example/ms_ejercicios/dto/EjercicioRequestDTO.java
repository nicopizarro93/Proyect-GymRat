package com.example.ms_ejercicios.dto;

import com.example.ms_ejercicios.model.DificultadEnum;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO utilizado para recibir los datos necesarios
 * al momento de crear un ejercicio.
 */
@Data
public class EjercicioRequestDTO {

    /**
     * Nombre del ejercicio.
     * No puede estar vacío ni ser nulo.
     */
    @NotBlank(message = "El nombre del ejercicio es obligatorio")
    private String nombreEjercicio;

    /**
     * Grupo muscular asociado al ejercicio.
     * Debe ser especificado obligatoriamente.
     */
    @NotNull(message = "Debe especificar el grupo muscular")
    private GrupoMuscularEnum grupoMuscular;

    /**
     * Nivel de dificultad del ejercicio.
     * Debe ser especificado obligatoriamente.
     */
    @NotNull(message = "Debe especificar la dificultad")
    private DificultadEnum dificultad;
}