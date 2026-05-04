package com.example.ms_ejercicios.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ms_ejercicios.model.DificultadEnum;
import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;
import com.example.ms_ejercicios.repository.EjercicioRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final EjercicioRepository ejerciciorepository;

    @Bean
    CommandLineRunner initData(){
        return args->{
            if(ejerciciorepository.count()>0){
                return;
            }

            List<Ejercicio>ejercicios=List.of(
                new Ejercicio(null, "press banca",GrupoMuscularEnum.PECHO,DificultadEnum.INTERMEDIO)
            );

            ejerciciorepository.saveAll(ejercicios);
            System.out.println("datos inicialez cargados correctamente");
        };
    }
}
