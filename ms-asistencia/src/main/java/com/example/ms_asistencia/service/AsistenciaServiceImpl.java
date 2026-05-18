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

/**
 * Implementación del servicio de asistencias.
 * Contiene la lógica de negocio para validar atletas, verificar membresías
 * y registrar ingresos permitidos o denegados.
 */
@Service
@RequiredArgsConstructor
public class AsistenciaServiceImpl implements AsistenciaService {

    /**
     * Repositorio utilizado para guardar y consultar registros de asistencia.
     */
    private final AsistenciaRepository asistenciaRepository;

    /**
     * Cliente Feign utilizado para consultar información de atletas.
     */
    private final AtletaClient atletaClient;

    /**
     * Cliente Feign utilizado para consultar la membresía actual de un atleta.
     */
    private final MembresiaClient membresiaClient;

    /**
     * Registra la asistencia de un atleta.
     * Primero valida que el atleta exista y luego verifica si posee una membresía activa.
     * Si ambas condiciones se cumplen, el ingreso queda como PERMITIDO; en caso contrario,
     * queda como DENEGADO.
     *
     * @param rutAtleta RUT del atleta que intenta ingresar.
     * @return asistencia registrada con la fecha, hora y estado correspondiente.
     */
    @Override
    public Asistencia registrarAsistencia(String rutAtleta) {
        String estadoFinal = "DENEGADO";

        try {
            atletaClient.obtenerAtletaPorRut(rutAtleta);

            Object membresia = membresiaClient.obtenerActual(rutAtleta);

            if (membresia != null) {
                estadoFinal = "PERMITIDO";
            }

        } catch (FeignException.NotFound e) {
            System.out.println("AVISO - Acceso denegado para RUT " + rutAtleta + ": Atleta no encontrado o sin membresía activa.");

        } catch (FeignException e) {
            System.err.println("ERROR CRÍTICO - Falló la comunicación con otros microservicios al validar el RUT: " + rutAtleta);
            throw new RuntimeException("Error temporal al validar los accesos. Por favor, intente más tarde.");
        }

        Asistencia nuevaAsistencia = new Asistencia();
        nuevaAsistencia.setRutAtleta(rutAtleta);
        nuevaAsistencia.setFechaHoraIngreso(LocalDateTime.now());
        nuevaAsistencia.setEstado(estadoFinal);

        return asistenciaRepository.save(nuevaAsistencia);
    }

    /**
     * Obtiene el historial de asistencias de un atleta.
     *
     * @param rutAtleta RUT del atleta consultado.
     * @return lista de asistencias asociadas al atleta.
     */
    @Override
    public List<Asistencia> obtenerAsistenciasPorRut(String rutAtleta) {
        return asistenciaRepository.findByRutAtleta(rutAtleta);
    }

    /**
     * Obtiene todas las asistencias registradas.
     *
     * @return lista completa de asistencias.
     */
    @Override
    public List<Asistencia> obtenerTodasAsistencias() {
        return asistenciaRepository.findAll();
    }
}
