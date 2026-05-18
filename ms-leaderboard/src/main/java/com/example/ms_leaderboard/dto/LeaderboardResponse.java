package com.example.ms_leaderboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO de respuesta utilizado para mostrar una posición del leaderboard.
 * Contiene la ubicación del atleta, su nombre y el peso levantado.
 */
@Data 
@AllArgsConstructor
public class LeaderboardResponse {

    /**
     * Posición del atleta dentro del ranking.
     */
    private int posicion;

    /**
     * Nombre del atleta mostrado en el leaderboard.
     */
    private String nombreAtleta;

    /**
     * Mejor peso levantado por el atleta en el ejercicio consultado.
     */
    private Double pesoLevantado;
}
