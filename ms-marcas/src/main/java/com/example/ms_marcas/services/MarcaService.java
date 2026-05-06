package com.example.ms_marcas.services;

import java.util.List;

import com.example.ms_marcas.model.Marca;

public interface MarcaService {

    Marca registrarIntento(Marca marca);
    List<Marca> obtenerPorRut(String rut);
    List<Marca> listarTodos();  
    Marca buscarPorId(Long id);
    List<Marca> obtenerMarcasAprobadasPorEjercicio(String nombreEjercicio);
    Marca actualizarEstado(Long id, String nuevoEstado);
    
}
