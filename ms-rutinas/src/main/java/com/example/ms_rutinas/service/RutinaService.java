package com.example.ms_rutinas.service;

import java.util.List;

import com.example.ms_rutinas.dto.RutinaResponseDTO;
import com.example.ms_rutinas.model.Rutina;

public interface RutinaService {

    Rutina guardarRutina(Rutina rutina);
    Rutina buscarPorId(Long id);
    List<Rutina> listarRutinas();
    void eliminarRutina(Long id);
    RutinaResponseDTO obtenerRutinaCompleta(Long id);
    List<RutinaResponseDTO> listarRutinasCompletas();
    Rutina actualizarRutina(Long id, Rutina rutina);
}
