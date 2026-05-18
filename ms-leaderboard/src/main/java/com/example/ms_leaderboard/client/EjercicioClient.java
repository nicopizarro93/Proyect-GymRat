package com.example.ms_leaderboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_leaderboard.dto.EjercicioDTO;

/**
 * Cliente Feign encargado de comunicarse con el microservicio de ejercicios.
 * Se utiliza para validar y obtener información de ejercicios registrados.
 */
@FeignClient(name = "ms-ejercicios", path = "/api/v1/ejercicios")
public interface EjercicioClient {

    /**
     * Obtiene un ejercicio por su nombre desde el microservicio ms-ejercicios.
     *
     * @param nombreEjercicio nombre del ejercicio a consultar.
     * @return datos del ejercicio encontrado.
     */
    @GetMapping("/nombre/{nombreEjercicio}")
    EjercicioDTO obtenerEjercicioPorNombre(@PathVariable("nombreEjercicio") String nombreEjercicio);

}
