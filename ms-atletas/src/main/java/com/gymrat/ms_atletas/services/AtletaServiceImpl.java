package com.gymrat.ms_atletas.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.gymrat.ms_atletas.model.Atleta;
import com.gymrat.ms_atletas.repository.AtletaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AtletaServiceImpl implements AtletaService {

    // Inyección limpia y segura mediante constructor generado por Lombok
    private final AtletaRepository atletaRepository;

    @Override
    public Atleta guardarAtleta(Atleta atleta) {
        if(atletaRepository.findByRut(atleta.getRut()).isPresent()){
            throw new IllegalArgumentException("ya existe un atleta con ese RUT");
        }
        return atletaRepository.save(atleta);
    }

    @Override
    public Atleta buscarPorRut(String rut) {
       return atletaRepository.findByRut(rut)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró un atleta con ese RUT" + rut));
    }

    @Override
    public List<Atleta> listarTodos() {
        return atletaRepository.findAll(); // Sin el casteo
    }


    @Override
    public void eliminarPorRut(String rut) {
        Atleta atleta = buscarPorRut(rut);
        atletaRepository.delete(atleta);
    }

}
