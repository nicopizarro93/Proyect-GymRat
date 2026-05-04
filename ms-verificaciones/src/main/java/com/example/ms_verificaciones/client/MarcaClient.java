package com.example.ms_verificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-marcas", path = "/api/v1/marcas")
public interface MarcaClient {

    @GetMapping("/{id}") 
    Object obtenerMarcaPorId(@PathVariable("id") Long id);

}
