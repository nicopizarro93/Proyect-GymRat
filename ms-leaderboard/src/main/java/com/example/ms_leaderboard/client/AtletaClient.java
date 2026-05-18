package com.example.ms_leaderboard.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ms_leaderboard.dto.AtletaDTO;

/**
 * Cliente Feign encargado de comunicarse con el microservicio de atletas.
 * Permite obtener información de un atleta utilizando su RUT.
 */
@FeignClient(name = "ms-atletas", path = "/api/v1/atletas")
public interface AtletaClient {

    /**
     * Obtiene los datos de un atleta desde el microservicio ms-atletas.
     *
     * @param rut RUT del atleta que se desea consultar.
     * @return datos del atleta encontrado.
     */
    @GetMapping("/{rut}")
    AtletaDTO obtenerAtletaPorRut(@PathVariable("rut") String rut);
}
