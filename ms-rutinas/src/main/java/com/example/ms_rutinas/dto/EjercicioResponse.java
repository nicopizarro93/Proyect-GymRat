package com.example.ms_rutinas.dto;

import lombok.Data;

@Data
public class EjercicioResponse {

    private Long idEjercicio;
    private String nombreEjercicio;
    private String grupoMuscular;
    private String dificultad;
}
