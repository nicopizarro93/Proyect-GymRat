package com.example.ms_leaderboard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// ... existing code ...

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    /**
     * Servicio que contiene la lógica para generar el ranking de atletas.
     */
    private final LeaderboardService leaderboardService;

    /**
     * Obtiene el Top 10 de atletas para el ejercicio indicado.
     *
     * @param ejercicio nombre del ejercicio consultado.
     * @return respuesta HTTP con la lista ordenada del leaderboard.
     */
    @GetMapping("/{ejercicio}")
    public ResponseEntity<List<LeaderboardResponse>> obtenerPantallaTop10(@PathVariable("ejercicio") String ejercicio) {
        return ResponseEntity.ok(leaderboardService.generarTop10(ejercicio));
    }
}
