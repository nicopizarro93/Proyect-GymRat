package com.example.ms_verificaciones.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ms_verificaciones.model.Verificacion;
import com.example.ms_verificaciones.model.enums.EstadoValidacion;
import java.util.List;


public interface VerificacionRepository extends JpaRepository<Verificacion, Long> {

    // Sirve para que un usuario vea el historial de todas sus solicitudes
    List<Verificacion> findByRutAtleta(String rutAtleta);

    // Sirve para saber cuántas verificaciones existen de una marca específica
    List<Verificacion> findByIdMarca(Long idMarca);

    // ¡CRUCIAL para el Staff! Sirve para listar todas las solicitudes que están PENDIENTES de revisión
    List<Verificacion> findByEstado(EstadoValidacion estado);

    
}
