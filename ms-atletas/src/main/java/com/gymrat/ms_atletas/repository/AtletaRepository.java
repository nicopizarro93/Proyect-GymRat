package com.gymrat.ms_atletas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymrat.ms_atletas.model.Atleta;

/**
 * Repositorio encargado de realizar operaciones de acceso a datos
 * sobre la entidad Atleta.
 *
 * Extiende JpaRepository, por lo que hereda métodos como:
 * save, findAll, findById, delete, count, entre otros.
 */
@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {

    /**
     * Busca un atleta según su RUT.
     *
     * Retorna Optional porque puede existir o no un atleta con ese RUT.
     */
    Optional<Atleta> findByRut(String rut);

}
