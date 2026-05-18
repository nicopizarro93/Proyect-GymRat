package com.example.ms_rutinas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_rutinas.client.EjerciciosClient;
import com.example.ms_rutinas.dto.EjercicioResponseDTO;
import com.example.ms_rutinas.dto.RutinaResponseDTO;
import com.example.ms_rutinas.model.Rutina;
import com.example.ms_rutinas.repository.RutinaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de rutinas.
 * Contiene la lógica de negocio para crear, consultar, listar, actualizar y eliminar rutinas,
 * además de integrar la información de ejercicios desde el microservicio correspondiente.
 */
@Service
@RequiredArgsConstructor
public class RutinaServiceImpl implements RutinaService {

    private final RutinaRepository rutinaRepository;
    private final EjerciciosClient ejerciciosClient;

    /**
     * Guarda una rutina luego de validar que sus ejercicios existan.
     *
     * @param rutina rutina que se desea guardar.
     * @return rutina guardada en la base de datos.
     */
    @Override
    public Rutina guardarRutina(Rutina rutina) {
        validarEjercicios(rutina.getEjerciciosIds());
       
        return rutinaRepository.save(rutina);
    }

    @Override
    public Rutina buscarPorId(Long id) {
      return rutinaRepository.findById(id)
      .orElseThrow(()-> new RuntimeException("Rutina no encontrada"));
    }

    /**
     * Obtiene todas las rutinas registradas.
     *
     * @return lista de rutinas.
     */
    @Override
    public List<Rutina> listarRutinas() {
        return rutinaRepository.findAll();
    }

    /**
     * Elimina una rutina por su identificador.
     *
     * @param id identificador de la rutina a eliminar.
     */
    @Override
    public void eliminarRutina(Long id) {
        rutinaRepository.deleteById(id);
    }

    /**
     * Valida que todos los ejercicios asociados a una rutina existan.
     *
     * @param ejerciciosIds lista de identificadores de ejercicios.
     */
    private void validarEjercicios(List<Long> ejerciciosIds){
        for(Long id: ejerciciosIds){
            ejerciciosClient.buscarEjercicioPorId(id);
        }
    }

    /**
     * Obtiene una rutina con la información completa de sus ejercicios.
     *
     * @param id identificador de la rutina.
     * @return DTO con la rutina y sus ejercicios detallados.
     */
    @Override
    public RutinaResponseDTO obtenerRutinaCompleta(Long id) {
       Rutina rutina= buscarPorId(id);

       List<EjercicioResponseDTO> ejercicios=
       rutina.getEjerciciosIds()
       .stream()
       .map(ejerciciosClient::buscarEjercicioPorId)
       .toList();

       RutinaResponseDTO dto=new RutinaResponseDTO();

       dto.setIdRutina(rutina.getIdRutina());
       dto.setNombreRutina(rutina.getNombreRutina());
       dto.setDificultad(rutina.getDificultad().name());
       dto.setDias(rutina.getDias());
       dto.setEjercicios(ejercicios);

       return dto;
    }

    @Override
    public List<RutinaResponseDTO> listarRutinasCompletas() {
       return rutinaRepository.findAll()
       .stream()
       .map(rutina->{

            List<EjercicioResponseDTO>ejercicios=
            rutina.getEjerciciosIds()
            .stream()
            .map(ejerciciosClient::buscarEjercicioPorId)
            .toList();

            RutinaResponseDTO dto=new RutinaResponseDTO();
            
            dto.setIdRutina(rutina.getIdRutina());
            dto.setNombreRutina(rutina.getNombreRutina());
            dto.setDificultad(rutina.getDificultad().name());
            dto.setDias(rutina.getDias());
            dto.setEjercicios(ejercicios);

            return dto;
       })
       .toList();
    }

    @Override
    public Rutina actualizarRutina(Long id, Rutina rutinaActializada) {
       Rutina rutinaExistente= rutinaRepository.findById(id)
       .orElseThrow(()-> new RuntimeException("Rutina no encontrada"));

       validarEjercicios(rutinaActializada.getEjerciciosIds());

       rutinaExistente.setNombreRutina(rutinaActializada.getNombreRutina());
       rutinaExistente.setDificultad(rutinaActializada.getDificultad());
       rutinaExistente.setDias(rutinaActializada.getDias());
       rutinaExistente.setEjerciciosIds(rutinaActializada.getEjerciciosIds());

       return rutinaRepository.save(rutinaExistente);
    }

}
