package com.example.ms_leaderboard.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.example.ms_leaderboard.client.AtletaClient;
import com.example.ms_leaderboard.client.EjercicioClient;
import com.example.ms_leaderboard.client.MarcaClient;
import com.example.ms_leaderboard.dto.AtletaDTO;
import com.example.ms_leaderboard.dto.LeaderboardResponse;
import com.example.ms_leaderboard.dto.MarcaDTO;
import com.example.ms_leaderboard.model.ConsultaLeaderboard;
import com.example.ms_leaderboard.repository.ConsultaLeaderboardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeaderboardServiceImpl implements LeaderboardService{

    private final MarcaClient marcaClient;
    private final AtletaClient atletaClient;
    private final ConsultaLeaderboardRepository repositorioVisitas;
    private final EjercicioClient ejercicioClient;

    @Override
    public List<LeaderboardResponse> generarTop10(String nombreEjercicio) {
        
        try {
            ejercicioClient.obtenerEjercicioPorNombre(nombreEjercicio);
        } catch (feign.FeignException.NotFound e) {
            // Si ms-ejercicios responde con 404, cortamos la ejecución y lanzamos tu error
            throw new IllegalArgumentException("Error: El ejercicio '" + nombreEjercicio + "' no existe en el catálogo oficial.");
        } catch (feign.FeignException e) {
            // Si ms-ejercicios está apagado o falla
            throw new RuntimeException("Error de validación: No se pudo comunicar con el catálogo de ejercicios.");
        }
        // 1. Traer TODAS las marcas aprobadas de ese ejercicio
        List<MarcaDTO> marcas = marcaClient.obtenerMarcasAprobadas(nombreEjercicio);

        // 2. Filtrar para tener solo el MEJOR peso de cada atleta
        Map<String, Double> mejoresMarcasPorAtleta = new HashMap<>();
        for (MarcaDTO marca : marcas) {
            mejoresMarcasPorAtleta.put(
                marca.getRutAtleta(), 
                Math.max(mejoresMarcasPorAtleta.getOrDefault(marca.getRutAtleta(), 0.0), marca.getPesoLevantado())
            );
        }

        // 3. Ordenar de mayor a menor peso
        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(mejoresMarcasPorAtleta.entrySet());
        listaOrdenada.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        // 4. Armar el Top 10 pidiendo los nombres a ms-atletas
        List<LeaderboardResponse> top10 = new ArrayList<>();
        int posicion = 1;
        
        for (Map.Entry<String, Double> entry : listaOrdenada) {
            if (posicion > 10) break;

            String rut = entry.getKey();
            Double peso = entry.getValue();
            String nombre = "Atleta Desconocido";

            try {
                AtletaDTO atleta = atletaClient.obtenerAtletaPorRut(rut);
                nombre = atleta.getNombre();
            } catch (Exception e) {
                System.err.println("No se pudo obtener el nombre del atleta RUT: " + rut);
            }

            top10.add(new LeaderboardResponse(posicion, rut, nombre, peso));
            posicion++;
        }

        // 5. Guardar estadística en la base de datos
        repositorioVisitas.save(new ConsultaLeaderboard(null, nombreEjercicio, LocalDateTime.now()));

        return top10;
    }
}
