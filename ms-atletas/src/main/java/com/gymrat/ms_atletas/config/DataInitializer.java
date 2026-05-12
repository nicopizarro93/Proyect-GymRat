package com.gymrat.ms_atletas.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.gymrat.ms_atletas.model.Atleta;
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
                        "19.235.003-5",
                        "Alexis",
                        "alexis@gymrat.com",
                        "MIEMBRO"
                    ),
                    new Atleta(
                        null,
                        "18.456.789-1",
                        "Matias",
                        "matias@gymrat.com",
                        "MIEMBRO"
                    ),
                    new Atleta(
                        null,
                        "17.555.444-2",
                        "Camila",
                        "camila@gymrat.com",
                        "STAFF"
                    )
                );
                atletaRepository.saveAll(atletas);
                System.out.println("atletas precargados con exito");
            };
        }
}
