package com.example.ms_asistencia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign encargado de comunicarse con el microservicio ms-atletas.
 * Permite consultar la existencia e información de un atleta mediante su RUT.
 */
@FeignClient(name = "ms-atletas")
public interface AtletaClient {

    /**
     * Obtiene la información de un atleta específico desde ms-atletas.
     *
     * @param rut RUT del atleta que se desea consultar.
     * @return información del atleta encontrado.
     */
    @GetMapping("/api/v1/atletas/{rut}")
    Object obtenerAtletaPorRut(@PathVariable("rut") String rut);
}
