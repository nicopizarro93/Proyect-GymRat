package com.example.ms_verificaciones.service;

import org.springframework.stereotype.Service;

import com.example.ms_verificaciones.client.MarcaClient;
import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.model.enums.EstadoValidacion;
import com.example.ms_verificaciones.model.enums.TipoValidacion;
import com.example.ms_verificaciones.repository.VerificacionRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificacionServiceImpl implements VerificacionService {

    private final VerificacionRepository verificacionRepository;
    private final MarcaClient marcaClient;

    @Override
    public Verificacion solicitarVerificacion(Verificacion verificacion) {
        
        if (verificacion.getTipoValidacion() == TipoValidacion.VIDEO && 
           (verificacion.getUrlVideo() == null || verificacion.getUrlVideo().isBlank())) {
            throw new IllegalArgumentException("Debe proporcionar la URL del video.");
        }

        // AHORA PREGUNTAMOS SI LA MARCA (EL LEVANTAMIENTO) EXISTE
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
        // 1. Buscamos la solicitud en la base de datos
        Verificacion verificacion = verificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: La solicitud de verificación con ID " + id + " no existe."));

        // 2. Regla de negocio: Solo podemos evaluar solicitudes pendientes
        if (verificacion.getEstado() != EstadoValidacion.PENDIENTE) {
            throw new IllegalArgumentException("Esta solicitud ya fue " + verificacion.getEstado() + " anteriormente.");
        }

        // 3. Regla de negocio: No se puede cambiar a PENDIENTE de nuevo
        if (nuevoEstado == EstadoValidacion.PENDIENTE) {
            throw new IllegalArgumentException("El nuevo estado debe ser APROBADA o RECHAZADA.");
        }

        // 4. Actualizamos y guardamos
        verificacion.setEstado(nuevoEstado);
        verificacion.setRutValidadorPrincipal(rutValidador);
        
        return verificacionRepository.save(verificacion);
    }

}
