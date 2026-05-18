package com.example.ms_asistencia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign encargado de comunicarse con el microservicio ms-membresia.
 * Permite consultar si un atleta posee una membresía activa.
 */
@FeignClient(name = "ms-membresia")
public interface MembresiaClient {

    /**
     * Obtiene la membresía actual asociada a un atleta.
     *
     * @param rutAtleta RUT del atleta consultado.
     * @return información de la membresía actual, si existe.
     */
    @GetMapping("/api/v1/membresias/{rutAtleta}/actual")
    Object obtenerActual(@PathVariable("rutAtleta") String rutAtleta);
}