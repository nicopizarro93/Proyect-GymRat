package com.example.ms_marcas.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-atletas", path = "/api/v1/atletas")
public interface AtletaClient {

    // Copiamos la firma del endpoint que ya tienes en ms-atletas
    // Feign se encargará de hacer la petición HTTP por debajo
    @GetMapping("/{rut}")
    Object obtenerAtletaPorRut(@PathVariable("rut") String rut);
}
