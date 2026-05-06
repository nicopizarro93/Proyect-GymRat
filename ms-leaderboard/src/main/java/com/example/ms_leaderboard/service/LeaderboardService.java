package com.example.ms_leaderboard.service;

import java.util.List;

import com.example.ms_leaderboard.dto.LeaderboardResponse;

public interface LeaderboardService {

    List<LeaderboardResponse> generarTop10(String nombreEjercicio);
}
