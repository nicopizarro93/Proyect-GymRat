package com.example.ms_leaderboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LeaderboardResponse {
    private int posicion;
    private String rutAtleta;
    private String nombreAtleta;
    private Double pesoLevantado;
}
