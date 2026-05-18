package com.example.ms_leaderboard.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_leaderboard.dto.MarcaDTO;

/**
 * Cliente Feign encargado de comunicarse con el microservicio de marcas.
 * Permite obtener las marcas aprobadas asociadas a un ejercicio específico.
 */
@FeignClient(name = "ms-marcas", path = "/api/v1/marcas")
public interface MarcaClient {

    /**
     * Obtiene todas las marcas aprobadas para un ejercicio determinado.
     *
     * @param nombreEjercicio nombre del ejercicio utilizado como filtro.
     * @return lista de marcas aprobadas del ejercicio indicado.
     */
    @GetMapping("/ejercicio/{nombreEjercicio}/aprobadas") 
    List<MarcaDTO> obtenerMarcasAprobadas(@PathVariable("nombreEjercicio") String nombreEjercicio);
}
