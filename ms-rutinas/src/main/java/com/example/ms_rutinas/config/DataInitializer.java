package com.example.ms_rutinas.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ms_rutinas.model.DificultadEnum;
import com.example.ms_rutinas.model.Rutina;
import com.example.ms_rutinas.repository.RutinaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final RutinaRepository rutinaRepository;

    @Bean
    CommandLineRunner initRutinas(){
        return args ->{
            if(rutinaRepository.count()>0){
                return;
            }
            List<Rutina>rutinas=List.of(

                new Rutina(
                    null,
                    "Powerbuilding",
                    DificultadEnum.INTERMEDIO,
                    6,
                    List.of(1L,2L,3L,4L)
                ),

                new Rutina(
                    null,
                    "BodyBuilding",
                    DificultadEnum.INTERMEDIO,
                    5,
                    List.of(1L,2L,3L,4L)
                ),
                new Rutina(
                    null,
                    "FullBody",
                    DificultadEnum.AVANZADO,
                    5,
                    List.of(1L,2L,3L,4L)
                )
            );
            rutinaRepository.saveAll(rutinas);

            System.out.println("Rutinas iniciales cargadas con exito");
        };
    }
}
