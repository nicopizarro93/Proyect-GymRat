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
        Asistencia nuevaAsistencia = new Asistencia();
        nuevaAsistencia.setRutAtleta(rutAtleta);
        nuevaAsistencia.setFechaHoraIngreso(LocalDateTime.now());
        nuevaAsistencia.setEstado("PERMITIDO"); 
        
        return asistenciaRepository.save(nuevaAsistencia);
    }

    public List<Asistencia> obtenerAsistenciasPorRut(String rutAtleta) {
        return asistenciaRepository.findByRutAtleta(rutAtleta);
    }
}