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

@Service
@RequiredArgsConstructor
public class MembresiaServiceImpl implements MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final AsistenciaClient asistenciaClient;
    private final AtletaClient atletaClient; 

    // Ahora recibe el DTO completo
    @Override
    public MembresiaModel contratarPlan(MembresiaRequestDTO dto) {
    
        try {
            atletaClient.obtenerAtletaPorRut(dto.getRutAtleta()); 
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Venta denegada: El atleta con RUT " + dto.getRutAtleta() + " no está registrado en el sistema.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con ms-atletas al validar la membresía.");
        }

        membresiaRepository.findTopByRutAtletaOrderByFechaFinDesc(dto.getRutAtleta())
                .ifPresent(membresiaActual -> {
                    boolean membresiaActiva = "ACTIVA".equalsIgnoreCase(membresiaActual.getEstado());
                    boolean membresiaNoVencida = !membresiaActual.getFechaFin().isBefore(LocalDate.now());

                    if (membresiaActiva && membresiaNoVencida) {
                        throw new IllegalArgumentException("El atleta ya tiene una membresía activa. Solo puede contratar un nuevo plan cuando la membresía esté vencida.");
                    }
                });

        MembresiaModel nuevaMembresia = new MembresiaModel();
        nuevaMembresia.setRutAtleta(dto.getRutAtleta());
        nuevaMembresia.setTipoPlan(dto.getTipoPlan());
        nuevaMembresia.setFechaInicio(LocalDate.now());
        nuevaMembresia.setFechaFin(LocalDate.now().plusMonths(dto.getMesesDuracion()));
        nuevaMembresia.setEstado("ACTIVA");

        return membresiaRepository.save(nuevaMembresia);
    }

    @Override
    public List<MembresiaModel> obtenerHistorialPorRut(String rutAtleta) {
        try {
            atletaClient.obtenerAtletaPorRut(rutAtleta);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("No se encontró un atleta con RUT " + rutAtleta + ".");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con ms-atletas al validar el RUT.");
        }
        
        return membresiaRepository.findByRutAtleta(rutAtleta);
    }

    @Override
    public MembresiaModel obtenerMembresiaActual(String rutAtleta) {
        try {
            atletaClient.obtenerAtletaPorRut(rutAtleta);
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("No se encontró un atleta con RUT " + rutAtleta + ".");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación con ms-atletas al validar el RUT.");
        }

        return membresiaRepository.findTopByRutAtletaOrderByFechaFinDesc(rutAtleta)
                .orElseThrow(() -> new RuntimeException("No se encontró una membresía para el atleta con RUT " + rutAtleta + "."));
    }

    @Override
    public List<Object> verAsistenciasDesdeMembresia(String rutAtleta) {
        return asistenciaClient.obtenerAsistenciasPorRut(rutAtleta);
    }
}