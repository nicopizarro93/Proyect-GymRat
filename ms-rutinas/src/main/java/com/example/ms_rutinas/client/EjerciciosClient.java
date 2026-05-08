package com.example.ms_rutinas.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_rutinas.dto.EjercicioResponseDTO;

@FeignClient(name = "ms-ejercicios", path = "/api/v1/ejercicios")
public interface EjerciciosClient {

    @GetMapping
    List<EjercicioResponseDTO> listarEjercicios();

    @GetMapping("/{id}")
    EjercicioResponseDTO buscarEjercicioPorId(@PathVariable("id") Long id);
    
}
