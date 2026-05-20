package com.example.ms_leaderboard.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ms_leaderboard.dto.LeaderboardResponse;
import com.example.ms_leaderboard.repository.ConsultaLeaderboardRepository;
import com.example.ms_leaderboard.service.LeaderboardService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final ConsultaLeaderboardRepository consultaLeaderboardRepository;
    private final LeaderboardService leaderboardService;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (consultaLeaderboardRepository.count() > 0) {
                return;
            }

            try {
                List<LeaderboardResponse> topPressBanca = leaderboardService.generarTop10("press banca");

                System.out.println("✅ Leaderboard inicial de press banca generado correctamente.");

                topPressBanca.forEach(posicion ->
                        System.out.println(
                                posicion.getPosicion() + "° lugar - " +
                                        posicion.getNombreAtleta() + " - " +
                                        posicion.getRutAtleta() + " - " +
                                        posicion.getPesoLevantado() + " kg"
                        )
                );

            } catch (Exception e) {
                System.err.println("⚠️ No se pudo generar el leaderboard inicial de press banca.");
                System.err.println("Verifica que ms-marcas, ms-atletas y ms-ejercicios estén levantados.");
                System.err.println("Detalle: " + e.getMessage());
            }
        };
    }
}
