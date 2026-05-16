package com.example.ms_marcas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ms_marcas.client.AtletaClient;
import com.example.ms_marcas.model.Marca;
import com.example.ms_marcas.repository.MarcaRepository;
import com.example.ms_marcas.dto.MarcaRequestDTO;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository;
    private final AtletaClient atletaClient;

    @Override
    public Marca registrarIntento(MarcaRequestDTO dto) {
        try {
            // 1. Validación remota
            atletaClient.obtenerAtletaPorRut(dto.getRutAtleta());
        } catch (FeignException.NotFound e) {
            throw new IllegalArgumentException("Error: El atleta con RUT " + dto.getRutAtleta() + " no existe.");
        } catch (FeignException e) {
            throw new RuntimeException("Error de comunicación al validar el atleta.");
        }

        // 2. Mapear DTO a Entidad
        Marca nuevaMarca = new Marca();
        nuevaMarca.setRutAtleta(dto.getRutAtleta());
        nuevaMarca.setNombreEjercicio(dto.getNombreEjercicio());
        nuevaMarca.setPesoLevantado(dto.getPesoLevantado());
        
        // 3. Regla de Negocio
        nuevaMarca.setEstado("PENDIENTE");
        return marcaRepository.save(nuevaMarca);
    }

    @Override
    public List<Marca> obtenerPorRut(String rut) {
        return marcaRepository.findByRutAtleta(rut);
    }

    @Override
    public List<Marca> listarTodos() {
        return marcaRepository.findAll();
    }

    @Override
    public Marca buscarPorId(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada"));
    }

    @Override
    public List<Marca> obtenerMarcasAprobadasPorEjercicio(String nombreEjercicio) {
        return marcaRepository.findByNombreEjercicioAndEstadoOrderByPesoLevantadoDesc(nombreEjercicio, "APROBADA");
    }

    @Override
    public Marca actualizarEstado(Long id, String nuevoEstado) {
        Marca marca = buscarPorId(id);
        marca.setEstado(nuevoEstado);
        return marcaRepository.save(marca);
    }
}