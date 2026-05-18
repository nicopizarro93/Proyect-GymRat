package com.example.ms_verificaciones.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.ms_verificaciones.dto.EstadoUpdateRequestDTO;

/**
 * Cliente Feign encargado de comunicarse con el microservicio ms-marcas.
 * Permite validar la existencia de una marca y actualizar su estado.
 */
@FeignClient(name = "ms-marcas", path = "/api/v1/marcas")
public interface MarcaClient {

    /**
     * Obtiene una marca registrada usando su identificador.
     *
     * @param id identificador de la marca.
     * @return información de la marca encontrada.
     */
    @GetMapping("/{id}") 
    Object obtenerMarcaPorId(@PathVariable("id") Long id);

    /**
     * Actualiza el estado de una marca después de ser aprobada o rechazada
     * durante el proceso de verificación.
     *
     * @param id identificador de la marca.
     * @param request DTO con el nuevo estado que se asignará a la marca.
     */
    @PutMapping("/{id}/estado")
    void actualizarEstadoMarca(@PathVariable("id") Long id, @RequestBody EstadoUpdateRequestDTO request);
}
