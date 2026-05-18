package com.example.ms_marcas.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * DTO utilizado para recibir el nuevo estado de una marca.
 * Se usa principalmente al aprobar, rechazar o modificar una marca existente.
 */
@Data
public class EstadoUpdateRequestDTO {

    /**
     * Nuevo estado que será asignado a la marca.
     * No puede estar vacío.
     */
    @NotBlank(message = "El estado no puede estar vacío")
    private String nuevoEstado;
}
