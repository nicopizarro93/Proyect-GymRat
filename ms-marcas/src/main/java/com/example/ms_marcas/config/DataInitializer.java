package com.example.ms_marcas.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.ms_marcas.model.EstadoEnum;
import com.example.ms_marcas.model.Marca;
import com.example.ms_marcas.repository.MarcaRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final MarcaRepository marcaRepository;

    @Bean
    CommandLineRunner initData() {
        return args -> {
            if (marcaRepository.count() > 0) {
                return;
            }

            List<Marca> marcas = List.of(
                new Marca(
                    null,
                    "18357914-2",
                    "press banca",
                    120.0,
                    EstadoEnum.APROBADA
                ),
                new Marca(
                    null,
                    "26587494-3",
                    "press banca",
                    110.0,
                    EstadoEnum.APROBADA
                ),
                new Marca(
                    null,
                    "87654321-0",
                    "press banca",
                    100.0,
                    EstadoEnum.APROBADA
                )
            );

            marcaRepository.saveAll(marcas);

            System.out.println("✅ Marcas iniciales de press banca cargadas correctamente.");
        };
    }
}
