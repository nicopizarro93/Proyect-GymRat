package com.example.ms_leaderboard.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_leaderboard.dto.MarcaDTO;

@FeignClient(name = "ms-marcas", path = "/api/v1/marcas")
public interface MarcaClient {

    @GetMapping("/ejercicio/{nombreEjercicio}/aprobadas") 
    List<MarcaDTO> obtenerMarcasAprobadas(@PathVariable("nombreEjercicio") String nombreEjercicio);
}
