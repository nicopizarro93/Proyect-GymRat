package com.example.ms_membresia.repository;

import com.example.ms_membresia.model.MembresiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio encargado del acceso a datos de las membresías.
 * Extiende JpaRepository para usar operaciones CRUD y consultas personalizadas.
 */
@Repository
public interface MembresiaRepository extends JpaRepository<MembresiaModel, Long> {

    /**
     * Busca todas las membresías asociadas a un atleta mediante su RUT.
     *
     * @param rutAtleta RUT del atleta.
     * @return lista de membresías encontradas.
     */
    List<MembresiaModel> findByRutAtleta(String rutAtleta);

    /**
     * Obtiene la membresía más reciente de un atleta según la fecha de término.
     *
     * @param rutAtleta RUT del atleta.
     * @return membresía más reciente, si existe.
     */
    Optional<MembresiaModel> findTopByRutAtletaOrderByFechaFinDesc(String rutAtleta);
}