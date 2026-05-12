package com.example.ms_leaderboard.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.ms_leaderboard.dto.ErrorResponse;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Atrapa las excepciones de reglas de negocio (ej: "El ejercicio no existe")
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(), // Devuelve un 400 Bad Request
                "Bad Request - Error de Validación",
                ex.getMessage() // Aquí irá tu texto: "Error: El ejercicio '...' no existe"
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 2. Atrapa los errores 404 directos de Feign (Si se nos escapa alguno en el Service)
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ErrorResponse> handleFeignNotFoundException(FeignException.NotFound ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(), // Devuelve un 404
                "Recurso no encontrado en microservicio externo",
                "El recurso que se intentó validar no existe en el catálogo principal."
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    // 3. El Escudo Final: Atrapa cualquier otro error inesperado (NullPointer, caídas de BD, etc.)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), // 500
                "Internal Server Error",
                "Ocurrió un error inesperado en el servidor: " + ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
