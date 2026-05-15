package com.example.ms_asistencia.service;

import java.util.List;

import com.example.ms_asistencia.model.Asistencia;

public interface AsistenciaService {

    Asistencia registrarAsistencia(String rutAtleta);

    List<Asistencia> obtenerAsistenciasPorRut(String rutAtleta);

    List<Asistencia> obtenerTodasAsistencias();
}
