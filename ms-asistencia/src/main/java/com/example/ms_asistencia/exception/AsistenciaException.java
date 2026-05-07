package com.example.ms_asistencia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// (Código 400). 
// Significa: los datos no cumplen con las reglas para procesarla".
// "Error de validación"
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AsistenciaException extends RuntimeException {

    // Ej: "Error: No se permite el acceso, membresía vencida".
    public AsistenciaException(String mensaje) {
        
        // Registramos el mensaje en el historial de errores de la aplicación.
        super(mensaje);
    }
}