package com.gymrat.ms_atletas.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gymrat.ms_atletas.model.Atleta;
import com.gymrat.ms_atletas.repository.AtletaRepository;
import lombok.RequiredArgsConstructor;

/**
 * Implementación de la interfaz AtletaService.
 *
 * Contiene la lógica de negocio relacionada con atletas,
 * como guardar, buscar, listar y eliminar.
 */
@Service
@RequiredArgsConstructor
public class AtletaServiceImpl implements AtletaService {

    /**
     * Repositorio utilizado para acceder a los datos de atletas.
     * Se inyecta mediante constructor gracias a Lombok.
     */
    private final AtletaRepository atletaRepository;

    /**
     * Guarda un atleta nuevo.
     * Antes de guardar, valida que no exista otro atleta con el mismo RUT.
     */
    @Override
    public Atleta guardarAtleta(Atleta atleta) {

        // Verifica si ya existe un atleta registrado con el mismo RUT.
        if(atletaRepository.findByRut(atleta.getRut()).isPresent()){
            throw new IllegalArgumentException("ya existe un atleta con ese RUT");
        }

        // Guarda el atleta en la base de datos.
        return atletaRepository.save(atleta);
    }

    /**
     * Busca un atleta por su RUT.
     * Si no existe, lanza una excepción.
     */
    @Override
    public Atleta buscarPorRut(String rut) {

       // Busca por RUT y, si no encuentra resultado, lanza un error.
       return atletaRepository.findByRut(rut)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró un atleta con ese RUT" + rut));
    }

    /**
     * Retorna todos los atletas registrados.
     */
    @Override
    public List<Atleta> listarTodos() {

        // Obtiene todos los registros de la tabla atletas.
        return atletaRepository.findAll();
    }

    /**
     * Elimina un atleta según su RUT.
     */
    @Override
    public void eliminarPorRut(String rut) {

        // Primero busca el atleta para verificar que exista.
        Atleta atleta = buscarPorRut(rut);

        // Luego elimina el atleta encontrado.
        atletaRepository.delete(atleta);
    }

}
