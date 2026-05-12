package com.example.ms_membresia.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-atletas")
public interface AtletaClient {

    // Usamos la ruta exacta
    @GetMapping("/api/v1/atletas/{rut}")
    Object obtenerAtletaPorRut(@PathVariable("rut") String rut);
    
}