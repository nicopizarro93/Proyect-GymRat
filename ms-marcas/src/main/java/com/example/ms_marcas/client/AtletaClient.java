package com.example.ms_marcas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign encargado de comunicarse con el microservicio de atletas.
 * Permite validar o consultar información de un atleta usando su RUT.
 */
@FeignClient(name = "ms-atletas", path = "/api/v1/atletas")
public interface AtletaClient {

    /**
     * Consulta un atleta por su RUT en el microservicio ms-atletas.
     *
     * @param rut RUT del atleta que se desea consultar.
     * @return información del atleta encontrado.
     */
    @GetMapping("/{rut}")
    Object obtenerAtletaPorRut(@PathVariable("rut") String rut);
}
