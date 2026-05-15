package com.example.ms_marcas.services;

import java.util.List;

import com.example.ms_marcas.dto.MarcaRequestDTO;
import com.example.ms_marcas.model.Marca;

public interface MarcaService {

    Marca registrarIntento(MarcaRequestDTO dto);
    List<Marca> obtenerPorRut(String rut);
    List<Marca> listarTodos();  
    Marca buscarPorId(Long id);
    List<Marca> obtenerMarcasAprobadasPorEjercicio(String nombreEjercicio);
    Marca actualizarEstado(Long id, String nuevoEstado);
    
}
