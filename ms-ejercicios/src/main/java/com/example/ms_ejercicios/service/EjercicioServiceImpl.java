package com.example.ms_ejercicios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;
import com.example.ms_ejercicios.repository.EjercicioRepository;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de ejercicios.
 * Contiene la lógica de negocio relacionada con la creación,
 * búsqueda, listado y eliminación de ejercicios.
 */
@Service
@RequiredArgsConstructor
public class EjercicioServiceImpl implements EjercicioService {

    /**
     * Repositorio utilizado para acceder a los datos de ejercicios.
     */
    private final EjercicioRepository ejercicioRepository;

    /**
     * Guarda un ejercicio verificando previamente que no exista
     * otro ejercicio con el mismo nombre.
     *
     * @param ejercicio ejercicio a guardar.
     * @return ejercicio guardado.
     * @throws IllegalArgumentException si ya existe un ejercicio con el mismo nombre.
     */
    @Override
    public Ejercicio guardarEjercicio(Ejercicio ejercicio) {
       if(ejercicioRepository.findByNombreEjercicio(ejercicio.getNombreEjercicio()).isPresent()){
        throw new IllegalArgumentException("ya existe un ejercicio con ese nombre");
       }
       return ejercicioRepository.save(ejercicio);
    }

    /**
     * Busca un ejercicio por su identificador.
     *
     * @param id identificador del ejercicio.
     * @return ejercicio encontrado.
     * @throws RuntimeException si el ejercicio no existe.
     */
    @Override
    public Ejercicio buscarPorId(Long id) {
       return ejercicioRepository.findById(id)
       .orElseThrow(()-> new RuntimeException("ejercicio no encontrado"));
    }

    /**
     * Lista todos los ejercicios almacenados.
     *
     * @return lista de ejercicios.
     */
    @Override
    public List<Ejercicio> listarEjercicios() {
       return ejercicioRepository.findAll();
    }

    /**
     * Elimina un ejercicio por su identificador.
     *
     * @param id identificador del ejercicio a eliminar.
     */
    @Override
    public void eliminarPorId(Long id) {
       ejercicioRepository.deleteById(id);
    }

    /**
     * Lista ejercicios según el grupo muscular indicado.
     *
     * @param grupoMuscular grupo muscular usado como filtro.
     * @return lista de ejercicios encontrados.
     */
    @Override
    public List<Ejercicio> listarPorGrupoMuscular(GrupoMuscularEnum grupoMuscular) {
      return ejercicioRepository.findByGrupoMuscular(grupoMuscular);
    }

    /**
     * Busca un ejercicio por su nombre exacto.
     *
     * @param nombre nombre del ejercicio.
     * @return un Optional con el ejercicio si existe.
     */
    @Override
    public Optional<Ejercicio> buscarPorNombre(String nombre) {
         return ejercicioRepository.findByNombreEjercicio(nombre);
    }
}
