package com.example.ms_leaderboard.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EjercicioDTO {

    private Long idEjercicio;
    private String nombreEjercicio;
}
