package com.example.ms_membresia.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_membresia.client.AsistenciaClient;
import com.example.ms_membresia.client.AtletaClient;
import com.example.ms_membresia.model.MembresiaModel;
import com.example.ms_membresia.repository.MembresiaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final AsistenciaClient asistenciaClient;
    private final AtletaClient atletaClient; // Conectamos el teléfono de atleta al servicio

    public MembresiaModel contratarPlan(String rutAtleta, String tipoPlan, int mesesDuracion) {
        
        // Evitamos procesar datos vacíos o nulos
        if (rutAtleta == null || rutAtleta.isEmpty()) {
            return null;
        }

        // Validación con manejo de errores ---
        try {
            // Intentamos buscar al atleta en el microservicio
            atletaClient.obtenerAtletaPorRut(rutAtleta); 
        } catch (Exception e) {
            // Si el RUT no existe, atrapamos el error
            // Mostramos un mensaje y cancelamos la creación de la membresía.
            System.out.println("Venta denegada: El atleta con RUT " + rutAtleta + " no está registrado en el sistema.");
            return null; 
        }

        // Si pasó el try sin problemas, creamos la membresía:
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

    public List<Object> verAsistenciasDesdeMembresia(String rutAtleta) {
        return asistenciaClient.obtenerAsistenciasPorRut(rutAtleta);
    }
}