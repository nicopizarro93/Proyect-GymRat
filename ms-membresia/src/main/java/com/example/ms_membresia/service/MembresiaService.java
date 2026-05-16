package com.example.ms_membresia.service;

import java.util.List;

import com.example.ms_membresia.dto.MembresiaRequestDTO;
import com.example.ms_membresia.model.MembresiaModel;

public interface MembresiaService {

    MembresiaModel contratarPlan(MembresiaRequestDTO dto);

    List<MembresiaModel> obtenerHistorialPorRut(String rutAtleta);

    MembresiaModel obtenerMembresiaActual(String rutAtleta);

    List<Object> verAsistenciasDesdeMembresia(String rutAtleta);
}
