package com.example.ms_membresia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Agregamos el path base aquí para que quede ordenado
@FeignClient(name = "ms-atletas", path = "/api/v1/atletas")
public interface AtletaClient {

    @GetMapping("/{rut}")
    Object obtenerAtletaPorRut(@PathVariable("rut") String rut);
}