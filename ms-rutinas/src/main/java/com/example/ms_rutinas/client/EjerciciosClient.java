package com.example.ms_rutinas.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_rutinas.dto.EjercicioResponse;

@FeignClient(name = "ms-ejercicios", path = "/api/v1/ejercicios")
public interface EjerciciosClient {

    @GetMapping
    List<EjercicioResponse> listarEjercicios();

    @GetMapping("/{id}")
    EjercicioResponse buscarEjercicioPorId(@PathVariable("id") Long id);
    
}
