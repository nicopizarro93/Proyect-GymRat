package com.example.ms_asistencia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// si este error ocurre,
// el servidor debe responder "Código 404".
// el recurso buscado no existe.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AsistenciaRecursoNoEncontrado extends RuntimeException {

    // recibe un mensaje personalizado (ej: "No existe la asistencia con ese ID")
    public AsistenciaRecursoNoEncontrado(String mensaje) {
        
        // Esto permite que el mensaje se guarde en los logs y se muestre en la respuesta del API.
        super(mensaje);
    }
}