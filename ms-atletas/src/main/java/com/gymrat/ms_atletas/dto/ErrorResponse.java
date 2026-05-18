package com.gymrat.ms_atletas.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO utilizado para enviar respuestas de error
 * de forma ordenada y consistente al cliente.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    /**
     * Fecha y hora en que ocurrió el error.
     */
    private LocalDateTime timestamp;

    /**
     * Código HTTP del error.
     * Ejemplo: 400, 404, 500.
     */
    private int status;

    /**
     * Nombre o tipo del error.
     */
    private String error;

    /**
     * Mensaje descriptivo del error.
     */
    private String message;
}
