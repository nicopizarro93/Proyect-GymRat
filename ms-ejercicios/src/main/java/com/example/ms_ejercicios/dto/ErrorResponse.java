package com.example.ms_ejercicios.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilizado para representar respuestas de error
 * enviadas al cliente cuando ocurre una excepción.
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
     * Código HTTP asociado al error.
     */
    private int status;

    /**
     * Descripción general del tipo de error.
     */
    private String error;

    /**
     * Mensaje detallado del error.
     */
    private String message;
}
