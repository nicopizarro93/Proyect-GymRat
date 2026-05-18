package com.example.ms_rutinas.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ms_rutinas.model.DificultadEnum;
import com.example.ms_rutinas.model.Rutina;
import com.example.ms_rutinas.repository.RutinaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Configuración encargada de cargar rutinas iniciales en la base de datos.
 * Solo inserta datos de ejemplo cuando el repositorio está vacío.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    /**
     * Repositorio utilizado para consultar y guardar rutinas.
     */
    private final RutinaRepository rutinaRepository;

    /**
     * Inicializa rutinas predeterminadas al arrancar la aplicación.
     *
     * @return tarea que se ejecuta al iniciar el microservicio.
     */
    @Bean
    CommandLineRunner initRutinas(){
        return args ->{
            if(rutinaRepository.count()>0){
                return;
            }
            List<Rutina>rutinas=List.of(

                new Rutina(
                    null,
                    "Powerlifter",
                    DificultadEnum.INTERMEDIO,
                    6,
                    List.of(1L,11L,3L)
                ),

                new Rutina(
                    null,
                    "BodyBuilding",
                    DificultadEnum.INTERMEDIO,
                    5,
                    List.of(2L,3L,6L,9L,12L,13L,16L)
                ),
                new Rutina(
                    null,
                    "FullBody",
                    DificultadEnum.AVANZADO,
                    4,
                    List.of(1L,6L,10L,12L,14L,15L,18L)
                )
            );
            rutinaRepository.saveAll(rutinas);

            System.out.println("Rutinas iniciales cargadas con exito");
        };
    }
}
