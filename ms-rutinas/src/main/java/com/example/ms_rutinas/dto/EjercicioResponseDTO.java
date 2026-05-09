package com.example.ms_rutinas.dto;

import lombok.Data;

@Data
public class EjercicioResponseDTO {

    private Long idEjercicio;
    private String nombreEjercicio;
    private String grupoMuscular;
    private String dificultad;
}
