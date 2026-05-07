package com.example.ms_asistencia.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.repository.AsistenciaRepository;

import lombok.RequiredArgsConstructor;

@Service // clase service
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository; // conectamos la clase service con el repository

    public Asistencia registrarAsistencia(String rutAtleta) {
        // Si el RUT viene vacío o no existe, simplemente devolvemos "null" (nada) 
        // y asi evitamos que el programa intente guardar basura en la base de datos.
        if (rutAtleta == null || rutAtleta.isEmpty()) {
            return null; 
        }

        // Si el RUT sí viene con texto, el código sigue su camino normal:
        Asistencia nuevaAsistencia = new Asistencia();
        nuevaAsistencia.setRutAtleta(rutAtleta);
        nuevaAsistencia.setFechaHoraIngreso(LocalDateTime.now());
        nuevaAsistencia.setEstado("PERMITIDO"); 
        
        return asistenciaRepository.save(nuevaAsistencia);
    }

    public List<Asistencia> obtenerAsistenciasPorRut(String rutAtleta) {
        // Devolvemos lo que encuentre la base de datos.
        // Si el atleta no existe, Spring simplemente devolverá una lista vacía:
        return asistenciaRepository.findByRutAtleta(rutAtleta);
    }
}