package com.gymrat.ms_atletas.exception;

import com.gymrat.ms_atletas.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase encargada de capturar y manejar excepciones globales
 * dentro del microservicio de atletas.
 *
 * Permite devolver respuestas de error personalizadas
 * en vez de mostrar errores técnicos directamente.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación producidos por los DTO.
     * Por ejemplo: campos vacíos, emails inválidos o RUT con formato incorrecto.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> manejarValidaciones(MethodArgumentNotValidException ex) {

        // Mapa donde se guardan los errores por nombre de campo.
        Map<String, String> errores = new HashMap<>();

        // Recorre todos los errores de validación y los guarda en el mapa.
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errores.put(error.getField(), error.getDefaultMessage())
        );

        // Se construye una respuesta de error personalizada.
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Error de Validación",
                errores.toString()
        );

        // Retorna HTTP 400 BAD REQUEST.
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Maneja errores de negocio.
     * Por ejemplo: atleta no encontrado o RUT duplicado.
     */
    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class})
    public ResponseEntity<ErrorResponse> manejarErroresNegocio(Exception ex) {

        // Si el mensaje contiene "No se encontró", se responde con 404.
        // En caso contrario, se responde con 400.
        HttpStatus status = ex.getMessage().contains("No se encontró") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;

        // Se construye la respuesta de error personalizada.
        ErrorResponse response = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage()
        );

        // Retorna la respuesta con el estado HTTP correspondiente.
        return new ResponseEntity<>(response, status);
    }
}