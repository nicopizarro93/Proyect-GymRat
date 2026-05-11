package com.example.ms_membresia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-asistencia")
public interface AsistenciaClient {

    @GetMapping("/api/v1/{rutAtleta}")
    List<Object> obtenerAsistenciasPorRut(@PathVariable("rutAtleta") String rutAtleta);
    
}