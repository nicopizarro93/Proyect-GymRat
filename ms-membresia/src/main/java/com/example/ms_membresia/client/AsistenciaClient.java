package com.example.ms_membresia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-asistencia", path = "/api/v1/asistencias")
public interface AsistenciaClient {

    // Actualizamos a la ruta /historial que definimos en la refactorización de ms-asistencia
    @GetMapping("/historial/{rutAtleta}")
    List<Object> obtenerAsistenciasPorRut(@PathVariable("rutAtleta") String rutAtleta);
}