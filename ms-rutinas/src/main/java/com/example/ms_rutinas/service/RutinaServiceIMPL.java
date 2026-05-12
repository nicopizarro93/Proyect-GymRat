package com.example.ms_rutinas.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_rutinas.client.EjerciciosClient;
import com.example.ms_rutinas.dto.EjercicioResponseDTO;
import com.example.ms_rutinas.dto.RutinaResponseDTO;
import com.example.ms_rutinas.model.Rutina;
import com.example.ms_rutinas.repository.RutinaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RutinaServiceIMPL implements RutinaService {

    private final RutinaRepository rutinaRepository;
    private final EjerciciosClient ejerciciosClient;

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

    @Override
    public List<Rutina> listarRutinas() {
        return rutinaRepository.findAll();
    }

    @Override
    public void eliminarRutina(Long id) {
        rutinaRepository.deleteById(id);
    }

    private void validarEjercicios(List<Long> ejerciciosIds){
        for(Long id: ejerciciosIds){
            ejerciciosClient.buscarEjercicioPorId(id);
        }
    }

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
