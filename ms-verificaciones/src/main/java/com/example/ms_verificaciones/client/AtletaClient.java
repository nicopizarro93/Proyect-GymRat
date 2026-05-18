package com.example.ms_verificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_verificaciones.dto.AtletaDTO;

/**
 * Cliente Feign encargado de comunicarse con el microservicio ms-atletas.
 * Permite obtener información de un atleta a partir de su RUT.
 */
@FeignClient(name = "ms-atletas", path = "/api/v1/atletas")
public interface AtletaClient {

    /**
     * Consulta un atleta específico en el microservicio de atletas usando su RUT.
     *
     * @param rut RUT del atleta que se desea buscar.
     * @return datos básicos del atleta encontrado.
     */
    @GetMapping("/{rut}")
    AtletaDTO obtenerAtletaPorRut(@PathVariable("rut") String rut);
}
