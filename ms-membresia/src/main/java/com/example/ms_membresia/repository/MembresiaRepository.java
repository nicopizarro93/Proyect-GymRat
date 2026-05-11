package com.example.ms_membresia.repository;

import com.example.ms_membresia.model.MembresiaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// @Repository esta interfaz se encarga de Crear, Leer, Actualizar, Borrar
@Repository
public interface MembresiaRepository extends JpaRepository<MembresiaModel, Long> {
    
    // 1. Método para buscar membresías por el RUT del atleta.
    List<MembresiaModel> findByRutAtleta(String rutAtleta);

    // Este metodo nos servirá para saber si su último pago está vigente o no.
    Optional<MembresiaModel> findTopByRutAtletaOrderByFechaFinDesc(String rutAtleta);
}