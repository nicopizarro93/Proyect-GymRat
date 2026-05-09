package com.example.ms_rutinas.dto;

import java.util.List;

import lombok.Data;

@Data
public class RutinaResponseDTO {
    private Long idRutina;
    private String nombreRutina;
    private String dificultad;
    private Integer dias;

    private List<EjercicioResponseDTO>ejercicios;
}
