package com.example.ms_marcas.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.ms_marcas.model.Marca;

@Repository
public interface MarcaRepository extends CrudRepository<Marca, Long> {

    List<Marca> findByRutAtleta(String rutAtleta);
    
    List<Marca> findByEstado(String estado);
}
