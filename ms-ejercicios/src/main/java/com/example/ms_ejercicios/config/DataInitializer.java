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

/**
 * Clase de configuración encargada de cargar datos iniciales
 * en la base de datos al iniciar la aplicación.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    /**
     * Repositorio utilizado para consultar y guardar ejercicios.
     */
    private final EjercicioRepository ejerciciorepository;

    /**
     * Bean que se ejecuta automáticamente al iniciar la aplicación.
     * Inserta una lista de ejercicios predeterminados solo si la tabla está vacía.
     *
     * @return un CommandLineRunner que carga los datos iniciales.
     */
    @Bean
    CommandLineRunner initData(){
        return args->{
            if(ejerciciorepository.count()>0){
                return;
            }

            List<Ejercicio>ejercicios=List.of(
                new Ejercicio(null, "press banca",GrupoMuscularEnum.PECHO,DificultadEnum.AVANZADO),
                new Ejercicio(null, "peck deck",GrupoMuscularEnum.PECHO,DificultadEnum.PRINCIPIANTE),
                new Ejercicio(null, "press inclinado",GrupoMuscularEnum.PECHO,DificultadEnum.INTERMEDIO),
                new Ejercicio(null, "apertura en polea",GrupoMuscularEnum.PECHO,DificultadEnum.INTERMEDIO),
                new Ejercicio(null, "apertura con mancuernas",GrupoMuscularEnum.PECHO,DificultadEnum.AVANZADO),

                new Ejercicio(null, "jalon al pecho en polea",GrupoMuscularEnum.ESPALDA,DificultadEnum.INTERMEDIO),
                new Ejercicio(null, "remo con mancuernas",GrupoMuscularEnum.ESPALDA,DificultadEnum.INTERMEDIO),
                new Ejercicio(null, "remo con barra",GrupoMuscularEnum.ESPALDA,DificultadEnum.AVANZADO),

                new Ejercicio(null, "vuelos laterales",GrupoMuscularEnum.HOMBRO,DificultadEnum.AVANZADO),
                new Ejercicio(null, "press militar",GrupoMuscularEnum.HOMBRO,DificultadEnum.AVANZADO),

                new Ejercicio(null, "sentadilla",GrupoMuscularEnum.PIERNA,DificultadEnum.AVANZADO),
                new Ejercicio(null, "prensa",GrupoMuscularEnum.PIERNA,DificultadEnum.PRINCIPIANTE),

                new Ejercicio(null, "predicador con barra",GrupoMuscularEnum.BICEP,DificultadEnum.INTERMEDIO),
                new Ejercicio(null, "curl con mancuernas",GrupoMuscularEnum.BICEP,DificultadEnum.PRINCIPIANTE),

                new Ejercicio(null, "press frances",GrupoMuscularEnum.TRICEP,DificultadEnum.AVANZADO),
                new Ejercicio(null, "extension en polea alta",GrupoMuscularEnum.TRICEP,DificultadEnum.PRINCIPIANTE),

                new Ejercicio(null, "elevacion de piernas",GrupoMuscularEnum.ABDOMEN,DificultadEnum.AVANZADO),
                new Ejercicio(null, "crunch abdominal",GrupoMuscularEnum.ABDOMEN,DificultadEnum.PRINCIPIANTE)
            );

            ejerciciorepository.saveAll(ejercicios);
            System.out.println("datos iniciales cargados correctamente");
        };
    }
}
