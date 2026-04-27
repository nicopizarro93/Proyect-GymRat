package com.example.ms_marcas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_marcas.client.AtletaClient;
import com.example.ms_marcas.model.Marca;
import com.example.ms_marcas.repository.MarcaRepository;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;
    private final AtletaClient atletaClient;

    @Override
    public Marca registrarIntento(Marca marca) {
        try {
            // 1. Validación remota mediante Feign
            atletaClient.obtenerAtletaPorRut(marca.getRutAtleta());
            
        } catch (FeignException e) { 
            // AL CAMBIAR A FeignException (A secas), ATRAPAMOS CUALQUIER ERROR (400, 404, etc)
            throw new RuntimeException("Error: El atleta con RUT " + marca.getRutAtleta() + " no existe o hubo un problema al consultarlo.");
        }

        // 2. Regla de Negocio
        marca.setEstado("PENDIENTE");
        return marcaRepository.save(marca);
    }

    @Override
    public List<Marca> obtenerPorRut(String rut) {
        return marcaRepository.findByRutAtleta(rut);
    }

}
