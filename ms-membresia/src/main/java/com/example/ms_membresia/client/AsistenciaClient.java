package com.example.ms_membresia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Cliente Feign encargado de comunicarse con el microservicio de asistencias.
 * Permite consultar el historial de asistencias de un atleta usando su RUT.
 */
@FeignClient(name = "ms-asistencia", path = "/api/v1/asistencias")
public interface AsistenciaClient {

    /**
     * Obtiene las asistencias registradas para un atleta específico.
     *
     * @param rutAtleta RUT del atleta a consultar.
     * @return lista de asistencias asociadas al atleta.
     */
    @GetMapping("/historial/{rutAtleta}")
    List<Object> obtenerAsistenciasPorRut(@PathVariable("rutAtleta") String rutAtleta);
}