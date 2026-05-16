package com.example.ms_ejercicios.dto;

import com.example.ms_ejercicios.model.DificultadEnum;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EjercicioRequestDTO {

    @NotBlank(message = "El nombre del ejercicio es obligatorio")
    private String nombreEjercicio;

    @NotNull(message = "Debe especificar el grupo muscular")
    private GrupoMuscularEnum grupoMuscular;

    @NotNull(message = "Debe especificar la dificultad")
    private DificultadEnum dificultad;
}