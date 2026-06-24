package com.example.ms_ejercicios.Controller;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ms_ejercicios.controller.EjercicioController;
import com.example.ms_ejercicios.dto.EjercicioRequestDTO;
import com.example.ms_ejercicios.model.DificultadEnum;
import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;
import com.example.ms_ejercicios.service.EjercicioService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(EjercicioController.class)
public class EjercicioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EjercicioService ejercicioService;

    @Autowired
    private ObjectMapper objectMapper;

    private Ejercicio ejercicio;
    private EjercicioRequestDTO dto;

    @BeforeEach
    void setUp() {

        ejercicio = new Ejercicio(
                1L,
                "Press banca",
                GrupoMuscularEnum.PECHO,
                DificultadEnum.INTERMEDIO);

        dto = new EjercicioRequestDTO();
        dto.setNombreEjercicio("Press banca");
        dto.setGrupoMuscular(GrupoMuscularEnum.PECHO);
        dto.setDificultad(DificultadEnum.INTERMEDIO);
    }

    @Test
    void buscarPorId_DeberiaRetornarEjercicio() throws Exception {

        when(ejercicioService.buscarPorId(1L))
                .thenReturn(ejercicio);

        mockMvc.perform(get("/api/v1/ejercicios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEjercicio").value(1))
                .andExpect(jsonPath("$.nombreEjercicio").value("Press banca"))
                .andExpect(jsonPath("$.grupoMuscular").value("PECHO"))
                .andExpect(jsonPath("$.dificultad").value("INTERMEDIO"));
    }

    @Test
    void listarEjercicios_DeberiaRetornarLista() throws Exception {

        when(ejercicioService.listarEjercicios())
                .thenReturn(List.of(ejercicio));

        mockMvc.perform(get("/api/v1/ejercicios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombreEjercicio").value("Press banca"));
    }

    @Test
    void listarPorGrupo_DeberiaRetornarSoloPecho() throws Exception {

        when(ejercicioService.listarPorGrupoMuscular(GrupoMuscularEnum.PECHO))
                .thenReturn(List.of(ejercicio));

        mockMvc.perform(get("/api/v1/ejercicios/grupo/PECHO"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].grupoMuscular").value("PECHO"));
    }

    @Test
    void buscarPorNombre_DeberiaRetornarEjercicio() throws Exception {

        when(ejercicioService.buscarPorNombre("Press banca"))
                .thenReturn(ejercicio);

        mockMvc.perform(get("/api/v1/ejercicios/nombre/Press banca"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombreEjercicio").value("Press banca"));
    }

    @Test
    void crearEjercicio_DeberiaRetornarCreated() throws Exception {

        when(ejercicioService.guardarEjercicio(org.mockito.ArgumentMatchers.any(Ejercicio.class)))
                .thenReturn(ejercicio);

        mockMvc.perform(post("/api/v1/ejercicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombreEjercicio").value("Press banca"));
    }

    @Test
    void eliminarEjercicio_DeberiaRetornar204() throws Exception {

        doNothing().when(ejercicioService).eliminarPorId(1L);

        mockMvc.perform(delete("/api/v1/ejercicios/1"))
                .andExpect(status().isNoContent());
    }

}