package com.example.ms_membresia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Cliente Feign encargado de comunicarse con el microservicio de atletas.
 * Se utiliza para validar que un atleta exista antes de contratar una membresía.
 */
@FeignClient(name = "ms-atletas", path = "/api/v1/atletas")
public interface AtletaClient {

    /**
     * Busca un atleta registrado en el sistema mediante su RUT.
     *
     * @param rut RUT del atleta a consultar.
     * @return información del atleta encontrado.
     */
    @GetMapping("/{rut}")
    Object obtenerAtletaPorRut(@PathVariable("rut") String rut);
}