package com.gymrat.ms_atletas.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gymrat.ms_atletas.model.Atleta;
import com.gymrat.ms_atletas.model.RolEnum;
import com.gymrat.ms_atletas.repository.AtletaRepository;

import lombok.RequiredArgsConstructor;

/**
 * Clase de configuración encargada de cargar datos iniciales
 * en la base de datos cuando la aplicación se inicia.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

        /**
         * Repositorio utilizado para acceder a la tabla de atletas.
         * Se inyecta automáticamente gracias a Lombok con @RequiredArgsConstructor.
         */
        private final AtletaRepository atletaRepository;

        /**
         * Bean que se ejecuta automáticamente al iniciar la aplicación.
         * Sirve para insertar atletas base solo si la base de datos está vacía.
         */
        @Bean
        CommandLineRunner initData(){
            return args->{

                // Si ya existen atletas en la base de datos, no se insertan datos nuevamente.
                if(atletaRepository.count()>0){
                    return;
                }

                // Lista de atletas iniciales que se guardarán en la base de datos.
                List<Atleta> atletas=List.of(
                    new Atleta(
                        null,
                        "12345678-9",
                        "Astroberto Perez",
                        "Astroberto@gymrat.com",
                        RolEnum.STAFF
                    ),
                    new Atleta(
                        null,
                        "18357914-2",
                        "Nicolás Pizarro",
                        "nicolas@gymrat.com",
                        RolEnum.MIEMBRO
                    ),
                    new Atleta(
                        null,
                        "26587494-3",
                        "Alexis Acuña",
                        "alexis@gymrat.com",
                        RolEnum.MIEMBRO
                    ),
                    new Atleta(
                        null,
                        "87654321-0",
                        "Humberto Ramirez",
                        "Humberto@gymrat.com",
                        RolEnum.MIEMBRO
                    )
                );

                // Guarda todos los atletas iniciales en la base de datos.
                atletaRepository.saveAll(atletas);

                // Mensaje informativo en consola para confirmar la precarga.
                System.out.println("✅ Atletas base (Staff y Miembros) precargados con éxito.");
            };
        }
}
