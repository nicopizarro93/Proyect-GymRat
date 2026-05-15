package com.gymrat.ms_atletas.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gymrat.ms_atletas.model.Atleta;
import com.gymrat.ms_atletas.model.RolEnum;
import com.gymrat.ms_atletas.repository.AtletaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

        private final AtletaRepository atletaRepository;

        @Bean
        CommandLineRunner initData(){
            return args->{
                if(atletaRepository.count()>0){
                    return;
                }
                List<Atleta> atletas=List.of(
                    new Atleta(
                        null,
                        "12345678-9",
                        "Astroberto Perez", // Mayúsculas y tilde
                        "Astroberto@gymrat.com",
                        RolEnum.STAFF
                    ),
                    new Atleta(
                        null,
                        "18357914-2",
                        "Nicolás Pizarro", // Mayúsculas y tilde
                        "nicolas@gymrat.com",
                        RolEnum.MIEMBRO
                    ),
                    new Atleta(
                        null,
                        "26587494-3",
                        "Alexis Acuña", // Mayúsculas
                        "alexis@gymrat.com",
                        RolEnum.MIEMBRO
                    ),
                    new Atleta(
                        null,
                        "87654321-0",
                        "Humberto Ramirez", // Mayúsculas
                        "Humberto@gymrat.com",
                        RolEnum.MIEMBRO
                    )
                );
                atletaRepository.saveAll(atletas);
                System.out.println("✅ Atletas base (Staff y Miembros) precargados con éxito.");
            };
        }
}
