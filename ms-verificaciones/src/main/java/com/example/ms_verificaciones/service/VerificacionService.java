package com.example.ms_verificaciones.service;

import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.model.enums.EstadoValidacion;

public interface VerificacionService {

    Verificacion solicitarVerificacion(Verificacion verificacion);
    Verificacion revisarVerificacion(Long id, EstadoValidacion nuevoEstado, String rutValidador);
}
