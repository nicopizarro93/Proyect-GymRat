package com.example.ms_marcas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_marcas.model.Marca;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    List<Marca> findByRutAtleta(String rutAtleta);
    
    List<Marca> findByEstado(String estado);
    
    List<Marca> findByNombreEjercicioAndEstadoOrderByPesoLevantadoDesc(String nombreEjercicio, String estado);
}
