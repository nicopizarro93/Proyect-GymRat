package com.example.ms_leaderboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_leaderboard.dto.EjercicioDTO;

@FeignClient(name = "ms-ejercicios", path = "/api/v1/ejercicios")
public interface EjercicioClient {

    @GetMapping("/nombre/{nombreEjercicio}")
    EjercicioDTO obtenerEjercicioPorNombre(@PathVariable("nombreEjercicio") String nombreEjercicio);

}
