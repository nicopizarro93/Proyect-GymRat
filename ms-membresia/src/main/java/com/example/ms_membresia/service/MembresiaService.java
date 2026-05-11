package com.example.ms_membresia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_membresia.model.MembresiaModel;
import com.example.ms_membresia.repository.MembresiaRepository;
import com.example.ms_membresia.client.AsistenciaClient; // Importamos el cliente

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final AsistenciaClient asistenciaClient; // Conectamos el cliente al Service

    public MembresiaModel contratarPlan(String rutAtleta, String tipoPlan, int mesesDuracion) {
        MembresiaModel nuevaMembresia = new MembresiaModel();
        nuevaMembresia.setRutAtleta(rutAtleta);
        nuevaMembresia.setTipoPlan(tipoPlan);
        nuevaMembresia.setFechaInicio(LocalDate.now());
        nuevaMembresia.setFechaFin(LocalDate.now().plusMonths(mesesDuracion));
        nuevaMembresia.setEstado("ACTIVA");

        return membresiaRepository.save(nuevaMembresia);
    }

    public List<MembresiaModel> obtenerHistorialPorRut(String rutAtleta) {
        return membresiaRepository.findByRutAtleta(rutAtleta);
    }

    public MembresiaModel obtenerMembresiaActual(String rutAtleta) {
        return membresiaRepository.findTopByRutAtletaOrderByFechaFinDesc(rutAtleta)
                .orElse(null);
    }

    // Este método usa el FeignClient para ir a golpear la puerta de ms-asistencia
    // y traer todas las veces que el atleta pasó por el torniquete.
    public List<Object> verAsistenciasDesdeMembresia(String rutAtleta) {
        return asistenciaClient.obtenerAsistenciasPorRut(rutAtleta);
    }
}