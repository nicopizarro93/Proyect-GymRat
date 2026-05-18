package com.example.ms_verificaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para enviar al microservicio de marcas
 * el nuevo estado de una marca verificada.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoUpdateRequestDTO {

    /**
     * Nuevo estado que será asignado a la marca.
     * Se envía como texto para mantener compatibilidad con ms-marcas.
     */
    private String nuevoEstado;
}
