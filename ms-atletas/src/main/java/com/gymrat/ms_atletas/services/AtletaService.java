package com.gymrat.ms_atletas.services;

import java.util.List;

import com.gymrat.ms_atletas.model.Atleta;

public interface AtletaService {

    Atleta guardarAtleta(Atleta atleta);
    Atleta buscarPorRut(String rut);
    List<Atleta> listarTodos();
    void eliminarPorRut(String rut);
    
}
