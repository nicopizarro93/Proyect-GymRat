package com.gymrat.ms_atletas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gymrat.ms_atletas.model.Atleta;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {
    // Aquí puedes agregar métodos personalizados de consulta si es necesario
    Optional<Atleta> findByRut(String rut);

}
