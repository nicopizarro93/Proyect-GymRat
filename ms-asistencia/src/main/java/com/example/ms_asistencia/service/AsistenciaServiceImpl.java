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
            atletaClient.obtenerAtletaPorRut(rutAtleta);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("No se encontró un atleta con RUT " + rutAtleta + ".");
        } catch (FeignException e) {
            System.err.println("ERROR CRÍTICO - Falló la comunicación con ms-atletas al validar el RUT: " + rutAtleta);
            throw new RuntimeException("Error temporal al validar el atleta. Por favor, intente más tarde.");
        }

        try {
            Object membresia = membresiaClient.obtenerActual(rutAtleta);

            if (membresia != null) {
                estadoFinal = "PERMITIDO";
            }

        } catch (FeignException.NotFound e) {
            System.out.println("AVISO - Acceso denegado para RUT " + rutAtleta + ": Sin membresía activa.");

        } catch (FeignException e) {
            System.err.println("ERROR CRÍTICO - Falló la comunicación con ms-membresia al validar el RUT: " + rutAtleta);
            throw new RuntimeException("Error temporal al validar la membresía. Por favor, intente más tarde.");
        }

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
