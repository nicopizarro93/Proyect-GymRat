package com.example.ms_rutinas.dto;

import java.util.List;

import com.example.ms_rutinas.model.DificultadEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RutinaResponseDTO {
    private Long idRutina;

    @NotBlank(message = "el nombre no puede estar vacio")
    private String nombreRutina;

    @NotNull(message = "la dificultad es obligatoria")
    private DificultadEnum dificultad;

    @NotNull(message = "la cantidad de dias es obligatoria")
    private Integer dias;

    private List<EjercicioResponseDTO>ejercicios;
}
