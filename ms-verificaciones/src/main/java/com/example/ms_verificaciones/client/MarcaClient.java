package com.example.ms_verificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.ms_verificaciones.dto.EstadoUpdateRequestDTO;

@FeignClient(name = "ms-marcas", path = "/api/v1/marcas")
public interface MarcaClient {

    @GetMapping("/{id}") 
    Object obtenerMarcaPorId(@PathVariable("id") Long id);

    // CAMBIO CRÍTICO: Ahora envía un DTO en el @RequestBody para coincidir con el ms-marcas refactorizado
    @PutMapping("/{id}/estado")
    void actualizarEstadoMarca(@PathVariable("id") Long id, @RequestBody EstadoUpdateRequestDTO request);
}
