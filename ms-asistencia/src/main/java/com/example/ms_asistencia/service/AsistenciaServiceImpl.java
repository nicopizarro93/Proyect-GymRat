package com.example.ms_asistencia.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_asistencia.client.AtletaClient;
import com.example.ms_asistencia.client.MembresiaClient;
import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.repository.AsistenciaRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service // clase service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    private final AsistenciaRepository asistenciaRepository;
    private final AtletaClient atletaClient;
    private final MembresiaClient membresiaClient;

    @Override
    public Asistencia registrarAsistencia(String rutAtleta) {
        String estadoFinal = "DENEGADO";

        try {
            // PASO 1: Validar si existe el atleta
            atletaClient.obtenerAtletaPorRut(rutAtleta);

            // PASO 2: Validar si tiene membresía activa
            Object membresia = membresiaClient.obtenerActual(rutAtleta);

            if (membresia != null) {
                estadoFinal = "PERMITIDO";
            }

        } catch (FeignException.NotFound e) {
            // Error 404: El atleta no existe o no tiene membresía.
            // Usamos impresión estándar para registrar el evento en la terminal de Docker
            System.out.println("AVISO - Acceso denegado para RUT " + rutAtleta + ": Atleta no encontrado o sin membresía activa.");
            
        } catch (FeignException e) {
            // Error 500 o caída de red: ms-atletas o ms-membresia están apagados
            // System.err imprime en rojo en muchas consolas, ideal para errores de comunicación
            System.err.println("ERROR CRÍTICO - Falló la comunicación con otros microservicios al validar el RUT: " + rutAtleta);
            throw new RuntimeException("Error temporal al validar los accesos. Por favor, intente más tarde.");
        }

        // Guardamos el registro independientemente de si pasó o no
        Asistencia nuevaAsistencia = new Asistencia();
        nuevaAsistencia.setRutAtleta(rutAtleta);
        nuevaAsistencia.setFechaHoraIngreso(LocalDateTime.now());
        nuevaAsistencia.setEstado(estadoFinal);

        return asistenciaRepository.save(nuevaAsistencia);
    }

    @Override
    public List<Asistencia> obtenerAsistenciasPorRut(String rutAtleta) {
        return asistenciaRepository.findByRutAtleta(rutAtleta);
    }

    @Override
    public List<Asistencia> obtenerTodasAsistencias() {
        return asistenciaRepository.findAll();
    }
}
