package com.example.ms_membresia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_membresia.client.AsistenciaClient;
import com.example.ms_membresia.client.AtletaClient;
import com.example.ms_membresia.dto.MembresiaRequestDTO;
import com.example.ms_membresia.model.MembresiaModel;
import com.example.ms_membresia.repository.MembresiaRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de membresías.
 * Contiene la lógica de negocio para contratar planes, consultar historial,
 * obtener membresías actuales y acceder a asistencias del atleta.
 */
@Service
@RequiredArgsConstructor
public class MembresiaServiceImpl implements MembresiaService {

    /**
     * Repositorio usado para guardar y consultar membresías en la base de datos.
     */
    private final MembresiaRepository membresiaRepository;

    /**
     * Cliente Feign usado para consultar asistencias desde el microservicio correspondiente.
     */
    private final AsistenciaClient asistenciaClient;

    /**
     * Cliente Feign usado para validar la existencia de atletas.
     */
    private final AtletaClient atletaClient; 

    /**
     * Contrata una nueva membresía para un atleta registrado.
     * Primero valida que el atleta exista y luego calcula las fechas de vigencia del plan.
     *
     * @param dto datos de la membresía solicitada.
     * @return membresía creada y guardada en la base de datos.
     */
    @Override
    public MembresiaModel contratarPlan(MembresiaRequestDTO dto) {
    
        try {
            atletaClient.obtenerAtletaPorRut(dto.getRutAtleta()); 
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Venta denegada: El atleta con RUT " + dto.getRutAtleta() + " no está registrado en el sistema.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con ms-atletas al validar la membresía.");
        }

        MembresiaModel nuevaMembresia = new MembresiaModel();
        nuevaMembresia.setRutAtleta(dto.getRutAtleta());
        nuevaMembresia.setTipoPlan(dto.getTipoPlan());
        nuevaMembresia.setFechaInicio(LocalDate.now());
        nuevaMembresia.setFechaFin(LocalDate.now().plusMonths(dto.getMesesDuracion()));
        nuevaMembresia.setEstado("ACTIVA");

        return membresiaRepository.save(nuevaMembresia);
    }

    /**
     * Obtiene todas las membresías asociadas a un atleta.
     *
     * @param rutAtleta RUT del atleta.
     * @return historial de membresías del atleta.
     */
    @Override
    public List<MembresiaModel> obtenerHistorialPorRut(String rutAtleta) {
        return membresiaRepository.findByRutAtleta(rutAtleta);
    }

    /**
     * Obtiene la membresía más reciente de un atleta.
     *
     * @param rutAtleta RUT del atleta.
     * @return membresía más reciente o null si no existe.
     */
    @Override
    public MembresiaModel obtenerMembresiaActual(String rutAtleta) {
        return membresiaRepository.findTopByRutAtletaOrderByFechaFinDesc(rutAtleta)
                .orElse(null);
    }

    /**
     * Consulta las asistencias del atleta desde el microservicio de asistencia.
     *
     * @param rutAtleta RUT del atleta.
     * @return lista de asistencias del atleta.
     */
    @Override
    public List<Object> verAsistenciasDesdeMembresia(String rutAtleta) {
        return asistenciaClient.obtenerAsistenciasPorRut(rutAtleta);
    }
}