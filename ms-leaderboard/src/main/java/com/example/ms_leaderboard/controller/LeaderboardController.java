package com.example.ms_leaderboard.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_leaderboard.dto.LeaderboardResponse;
import com.example.ms_leaderboard.service.LeaderboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/leaderboard")
@RequiredArgsConstructor
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @GetMapping("/{ejercicio}")
    public ResponseEntity<List<LeaderboardResponse>> obtenerPantallaTop10(@PathVariable String ejercicio) {
        return ResponseEntity.ok(leaderboardService.generarTop10(ejercicio));
    }
}
