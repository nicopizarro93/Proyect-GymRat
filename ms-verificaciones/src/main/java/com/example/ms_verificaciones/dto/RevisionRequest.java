package com.example.ms_verificaciones.dto;

import com.example.ms_verificaciones.model.enums.EstadoValidacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * DTO que representa la información necesaria para revisar
 * una solicitud de verificación.
 */
@Data
public class RevisionRequest {

    /**
     * Estado final que se desea asignar a la solicitud.
     * Debe ser APROBADA o RECHAZADA.
     */

    @NotNull(message = "Debe indicar el nuevo estado (APROBADA o RECHAZADA)")
    private EstadoValidacion nuevoEstado;
    
    @NotBlank(message = "El RUT del validador es obligatorio")
    private String rutValidador;
}
