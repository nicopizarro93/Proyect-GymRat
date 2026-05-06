package com.example.ms_verificaciones.service;

import com.example.ms_verificaciones.client.AtletaClient;
import com.example.ms_verificaciones.client.MarcaClient;
import com.example.ms_verificaciones.dto.AtletaDTO;
import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.model.enums.EstadoValidacion;
import com.example.ms_verificaciones.model.enums.TipoValidacion;
import com.example.ms_verificaciones.repository.VerificacionRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificacionServiceImpl implements VerificacionService {

    private final VerificacionRepository verificacionRepository;
    private final MarcaClient marcaClient;
    private final AtletaClient atletaClient;

    @Override
    public Verificacion solicitarVerificacion(Verificacion verificacion) {
        
        if (verificacion.getTipoValidacion() == TipoValidacion.VIDEO && 
           (verificacion.getUrlVideo() == null || verificacion.getUrlVideo().isBlank())) {
            throw new IllegalArgumentException("Debe proporcionar la URL del video.");
        }

        try {
            marcaClient.obtenerMarcaPorId(verificacion.getIdMarca());
        } catch (FeignException e) {
            throw new RuntimeException("Error: La Marca/Levantamiento con ID " + verificacion.getIdMarca() + " no existe.");
        }

        verificacion.setEstado(EstadoValidacion.PENDIENTE);
        return verificacionRepository.save(verificacion);
    }

    @Override
    public Verificacion revisarVerificacion(Long id, EstadoValidacion nuevoEstado, String rutValidador) {
        
        Verificacion verificacion = verificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: La solicitud de verificación con ID " + id + " no existe."));

        if (verificacion.getEstado() != EstadoValidacion.PENDIENTE) {
            throw new IllegalArgumentException("Esta solicitud ya fue " + verificacion.getEstado() + " anteriormente.");
        }

        if (nuevoEstado == EstadoValidacion.PENDIENTE) {
            throw new IllegalArgumentException("El nuevo estado debe ser APROBADA o RECHAZADA.");
        }

        // 1. Evitar que un atleta vote dos veces en la misma solicitud
        if (verificacion.getRutsValidadores().contains(rutValidador)) {
            throw new IllegalArgumentException("El atleta con RUT " + rutValidador + " ya emitió su evaluación para este levantamiento.");
        }

        // 2. Obtener el rol del validador desde ms-atletas
        AtletaDTO validador;
        try {
            validador = atletaClient.obtenerAtletaPorRut(rutValidador);
        } catch (Exception e) {
            throw new RuntimeException("Error al validar: No se pudo obtener la información del atleta desde ms-atletas.");
        }

        // 3. Reglas de negocio según el rol
        if ("STAFF".equals(validador.getRol())) {
            verificacion.setEstado(nuevoEstado);
            verificacion.getRutsValidadores().add(rutValidador);
            
        } else if ("MIEMBRO".equals(validador.getRol())) {
            if (nuevoEstado == EstadoValidacion.RECHAZADA) {
                verificacion.setEstado(EstadoValidacion.RECHAZADA);
                verificacion.getRutsValidadores().add(rutValidador);
            } 
            else if (nuevoEstado == EstadoValidacion.APROBADA) {
                verificacion.getRutsValidadores().add(rutValidador);
                
                if (verificacion.getRutsValidadores().size() >= 2) {
                    verificacion.setEstado(EstadoValidacion.APROBADA);
                } else {
                    verificacion.setEstado(EstadoValidacion.PENDIENTE); 
                }
            }
        } else {
            throw new IllegalArgumentException("El rol del atleta no está autorizado para validar marcas.");
        }

        // 4. Guardar los cambios localmente
        Verificacion guardada = verificacionRepository.save(verificacion);

        // 5. Comunicar a ms-marcas SOLO si el estado final cambió a APROBADA o RECHAZADA
        if (guardada.getEstado() == EstadoValidacion.APROBADA || guardada.getEstado() == EstadoValidacion.RECHAZADA) {
            try {
                marcaClient.actualizarEstadoMarca(guardada.getIdMarca(), guardada.getEstado().name());
            } catch (FeignException e) {
                throw new RuntimeException("La verificación se guardó, pero hubo un error al actualizar la marca en ms-marcas: " + e.getMessage());
            }
        }

        return guardada;
    }
}