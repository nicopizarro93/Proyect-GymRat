package com.example.ms_asistencia.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_asistencia.client.AtletaClient;
import com.example.ms_asistencia.client.MembresiaClient;
import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.repository.AsistenciaRepository;

import lombok.RequiredArgsConstructor;

@Service // clase service
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository; // conectamos la clase service con el repository
    private final AtletaClient atletaClient; // Conectamos el "teléfono"
    private final MembresiaClient membresiaClient;

public Asistencia registrarAsistencia(String rutAtleta) {
    if (rutAtleta == null || rutAtleta.isEmpty()) return null;

    // Empezamos asumiendo que no puede pasar hasta que los servicios digan lo contrario
    String estadoFinal = "DENEGADO"; 

    try {
        // PASO 1: ¿El atleta existe? (Llamada a ms-atleta)
        atletaClient.obtenerAtletaPorRut(rutAtleta); 

        // PASO 2: ¿Tiene membresía actual? (Llamada a ms-membresia)
        // Usamos el método de tu compañero: obtenerActual
        Object membresia = membresiaClient.obtenerActual(rutAtleta);
        
        // Si ms-membresia devuelve un objeto (un plan), entonces puede pasar
        if (membresia != null) {
            estadoFinal = "PERMITIDO";
        }

    } catch (Exception e) {
        // Si el RUT no existe o ms-membresia arroja error (porque no hay plan),
        // el estado se quedará como "DENEGADO".
        System.out.println("Validación fallida para: " + rutAtleta + ". Motivo: " + e.getMessage());
    }

    // Finalmente, guardamos el registro en TU base de datos (pb_asistencia_db)
    Asistencia nuevaAsistencia = new Asistencia();
    nuevaAsistencia.setRutAtleta(rutAtleta);
    nuevaAsistencia.setFechaHoraIngreso(LocalDateTime.now());
    nuevaAsistencia.setEstado(estadoFinal); 
    
    return asistenciaRepository.save(nuevaAsistencia);
}
    public List<Asistencia> obtenerAsistenciasPorRut(String rutAtleta) {
        // Va al repositorio y busca todos los registros asociados a ese RUT
        return asistenciaRepository.findByRutAtleta(rutAtleta);
    }

    public List<Asistencia> obtenerTodasAsistencias() {
        // Devuelve la lista completa de asistencias
        return asistenciaRepository.findAll();
    }
}