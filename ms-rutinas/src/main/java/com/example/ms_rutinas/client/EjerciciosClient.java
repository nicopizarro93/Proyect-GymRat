package com.example.ms_rutinas.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_rutinas.dto.EjercicioResponseDTO;

/**
 * Cliente Feign encargado de comunicarse con el microservicio de ejercicios.
 * Permite obtener ejercicios disponibles y consultar un ejercicio específico por su ID.
 */
@FeignClient(name = "ms-ejercicios", path = "/api/v1/ejercicios")
public interface EjerciciosClient {

    /**
     * Obtiene la lista completa de ejercicios registrados en el microservicio de ejercicios.
     *
     * @return lista de ejercicios disponibles.
     */
    @GetMapping
    List<EjercicioResponseDTO> listarEjercicios();

    /**
     * Busca un ejercicio específico mediante su identificador.
     *
     * @param id identificador del ejercicio.
     * @return datos del ejercicio encontrado.
     */
    @GetMapping("/{id}")
    EjercicioResponseDTO buscarEjercicioPorId(@PathVariable("id") Long id);

}
