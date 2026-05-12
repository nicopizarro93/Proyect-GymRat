package com.example.ms_asistencia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-membresia")
public interface MembresiaClient {

    @GetMapping("/api/v1/membresias/{rutAtleta}/actual")
    Object obtenerActual(@PathVariable("rutAtleta") String rutAtleta);
}