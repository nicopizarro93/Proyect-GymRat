package com.example.ms_asistencia.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_asistencia.client.AtletaClient;
import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.repository.AsistenciaRepository;

import lombok.RequiredArgsConstructor;

@Service // clase service
@RequiredArgsConstructor
public class AsistenciaService {

    private final AsistenciaRepository asistenciaRepository; // conectamos la clase service con el repository
    private final AtletaClient atletaClient; // Conectamos el "teléfono"

    public Asistencia registrarAsistencia(String rutAtleta) {
        // Si el RUT viene vacío o no existe, simplemente devolvemos "null" (nada) 
        // evitamos que el programa intente guardar basura en la base de datos.
        if (rutAtleta == null || rutAtleta.isEmpty()) {
            return null; 
        }

        // --- EL ESCUDO: Validación del torniquete con manejo de errores ---
        try {
            // El sistema intenta llamar al microservicio.
            atletaClient.obtenerAtletaPorRut(rutAtleta); 
        } catch (Exception e) {
            // Si el RUT no existe, el microservicio arrojará un error.
            // Lo atrapamos aquí para que la aplicación no colapse, mostramos un mensaje
            // en la consola y detenemos el proceso (retornando null).
            System.out.println("Acceso denegado: El atleta con RUT " + rutAtleta + " no está registrado.");
            return null; 
        }

        // Si el RUT sí existe en ms-atletas, el código sigue su camino normal:
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