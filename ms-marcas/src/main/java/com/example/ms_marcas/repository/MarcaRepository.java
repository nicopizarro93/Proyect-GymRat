package com.example.ms_marcas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_marcas.model.Marca;

/**
 * Repositorio encargado de acceder a los datos de las marcas.
 * Extiende JpaRepository para usar operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    /**
     * Busca todas las marcas asociadas a un atleta por su RUT.
     *
     * @param rutAtleta RUT del atleta.
     * @return lista de marcas encontradas.
     */
    List<Marca> findByRutAtleta(String rutAtleta);

    /**
     * Busca marcas según su estado.
     *
     * @param estado estado de la marca.
     * @return lista de marcas con el estado indicado.
     */
    List<Marca> findByEstado(String estado);

    /**
     * Busca marcas por ejercicio y estado, ordenadas desde el mayor peso levantado.
     *
     * @param nombreEjercicio nombre del ejercicio.
     * @param estado estado de la marca.
     * @return lista de marcas ordenadas descendentemente por peso levantado.
     */
    List<Marca> findByNombreEjercicioAndEstadoOrderByPesoLevantadoDesc(String nombreEjercicio, String estado);
}
