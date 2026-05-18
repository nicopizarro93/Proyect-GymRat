package com.example.ms_verificaciones.service;

import com.example.ms_verificaciones.client.AtletaClient;
import com.example.ms_verificaciones.client.MarcaClient;
import com.example.ms_verificaciones.dto.AtletaDTO;
import com.example.ms_verificaciones.dto.EstadoUpdateRequestDTO;
import com.example.ms_verificaciones.dto.VerificacionRequestDTO;
import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.model.enums.EstadoValidacion;
import com.example.ms_verificaciones.model.enums.TipoValidacion;
import com.example.ms_verificaciones.repository.VerificacionRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;

import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implementación del servicio de verificaciones.
 * Contiene la lógica de negocio para solicitar, revisar y listar
 * verificaciones de marcas.
 */
@Service
@RequiredArgsConstructor
public class VerificacionServiceImpl implements VerificacionService {

    /**
     * Repositorio utilizado para guardar y consultar verificaciones.
     */
    private final VerificacionRepository verificacionRepository;

    /**
     * Cliente Feign utilizado para comunicarse con el microservicio de marcas.
     */
    private final MarcaClient marcaClient;

    /**
     * Cliente Feign utilizado para comunicarse con el microservicio de atletas.
     */
    private final AtletaClient atletaClient;

    /**
     * Registra una nueva solicitud de verificación para una marca.
     * Valida que la marca exista y que, si la validación es por video,
     * se entregue una URL válida.
     *
     * @param dto datos de la solicitud de verificación.
     * @return verificación creada con estado PENDIENTE.
     */
    public Verificacion solicitarVerificacion(VerificacionRequestDTO dto) {
    
        if (dto.getTipoValidacion() == TipoValidacion.VIDEO && 
           (dto.getUrlVideo() == null || dto.getUrlVideo().isBlank())) {
            throw new IllegalArgumentException("Debe proporcionar la URL del video.");
        }

        try {
            marcaClient.obtenerMarcaPorId(dto.getIdMarca());
        } catch (FeignException e) {
            throw new RuntimeException("Error: La Marca/Levantamiento con ID " + dto.getIdMarca() + " no existe.");
        }

        Verificacion verificacion = new Verificacion();
        verificacion.setRutAtleta(dto.getRutAtleta());
        verificacion.setIdMarca(dto.getIdMarca());
        verificacion.setTipoValidacion(dto.getTipoValidacion());
        verificacion.setUrlVideo(dto.getUrlVideo());
        verificacion.setEstado(EstadoValidacion.PENDIENTE);
    
        return verificacionRepository.save(verificacion);
    }

    /**
     * Revisa una solicitud de verificación existente.
     * Aplica reglas de validación según el rol del evaluador:
     * STAFF puede aprobar o rechazar directamente, mientras que MIEMBRO
     * requiere lógica de votos para aprobar.
     *
     * @param id identificador de la solicitud.
     * @param nuevoEstado nuevo estado que se desea asignar.
     * @param rutValidador RUT del usuario que realiza la evaluación.
     * @return verificación actualizada.
     */
    @Override
    public Verificacion revisarVerificacion(Long id, EstadoValidacion nuevoEstado, String rutValidador) {
        Verificacion verificacion = verificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Error: La solicitud con ID " + id + " no existe."));

        if (verificacion.getEstado() != EstadoValidacion.PENDIENTE) {
            throw new IllegalArgumentException("Esta solicitud ya fue " + verificacion.getEstado() + ".");
        }

        if (nuevoEstado == EstadoValidacion.PENDIENTE) {
            throw new IllegalArgumentException("El nuevo estado debe ser APROBADA o RECHAZADA.");
        }

        if (verificacion.getRutsValidadores().contains(rutValidador)) {
            throw new IllegalArgumentException("El atleta con RUT " + rutValidador + " ya emitió su evaluación.");
        }

        AtletaDTO validador;
        try {
            validador = atletaClient.obtenerAtletaPorRut(rutValidador);
        } catch (Exception e) {
            throw new RuntimeException("Error al validar: No se pudo obtener la información del atleta.");
        }

        if ("STAFF".equals(validador.getRol())) {
            verificacion.setEstado(nuevoEstado);
            verificacion.getRutsValidadores().add(rutValidador);
        } else if ("MIEMBRO".equals(validador.getRol())) {
            if (nuevoEstado == EstadoValidacion.RECHAZADA) {
                verificacion.setEstado(EstadoValidacion.RECHAZADA);
                verificacion.getRutsValidadores().add(rutValidador);
            } else if (nuevoEstado == EstadoValidacion.APROBADA) {
                verificacion.getRutsValidadores().add(rutValidador);
                if (verificacion.getRutsValidadores().size() >= 2) {
                    verificacion.setEstado(EstadoValidacion.APROBADA);
                } else {
                    verificacion.setEstado(EstadoValidacion.PENDIENTE); 
                }
            }
        } else {
            throw new IllegalArgumentException("El rol no está autorizado para validar marcas.");
        }

        Verificacion guardada = verificacionRepository.save(verificacion);

        if (guardada.getEstado() == EstadoValidacion.APROBADA || guardada.getEstado() == EstadoValidacion.RECHAZADA) {
            try {
                EstadoUpdateRequestDTO estadoDto = new EstadoUpdateRequestDTO(guardada.getEstado().name());
                marcaClient.actualizarEstadoMarca(guardada.getIdMarca(), estadoDto);
            } catch (FeignException e) {
                throw new RuntimeException("Verificación guardada, pero falló la actualización en ms-marcas: " + e.getMessage());
            }
        }

        return guardada;
    }

    /**
     * Obtiene todas las solicitudes de verificación registradas.
     *
     * @return lista completa de verificaciones.
     */
    @Override
    public List<Verificacion> listarVerificaciones() {
        return verificacionRepository.findAll();
    }
}