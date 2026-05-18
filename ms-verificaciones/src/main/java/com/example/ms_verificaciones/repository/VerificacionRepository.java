package com.example.ms_verificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.model.enums.EstadoValidacion;
import java.util.List;

/**
 * Repositorio encargado de gestionar las operaciones de acceso a datos
 * para la entidad Verificacion.
 */
public interface VerificacionRepository extends JpaRepository<Verificacion, Long> {

    /**
     * Busca todas las verificaciones solicitadas por un atleta específico.
     *
     * @param rutAtleta RUT del atleta.
     * @return lista de verificaciones asociadas al atleta.
     */
    List<Verificacion> findByRutAtleta(String rutAtleta);

    /**
     * Busca todas las verificaciones asociadas a una marca específica.
     *
     * @param idMarca identificador de la marca.
     * @return lista de verificaciones relacionadas con la marca.
     */
    List<Verificacion> findByIdMarca(Long idMarca);

    /**
     * Busca verificaciones según su estado actual.
     * Es útil para listar solicitudes pendientes de revisión.
     *
     * @param estado estado de validación que se desea consultar.
     * @return lista de verificaciones que coinciden con el estado indicado.
     */
    List<Verificacion> findByEstado(EstadoValidacion estado);
}
