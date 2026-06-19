package com.gymrat.ms_atletas.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gymrat.ms_atletas.dto.AtletaRequestDTO;
import com.gymrat.ms_atletas.model.Atleta;
import com.gymrat.ms_atletas.model.RolEnum;
import com.gymrat.ms_atletas.services.AtletaService;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class AtletaControllerTest {

    @Mock
    private AtletaService atletaService;

    @InjectMocks
    private AtletaController atletaController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private Atleta atletaMock;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(atletaController).build();
        objectMapper = new ObjectMapper();

        atletaMock = new Atleta();
        atletaMock.setId(1L);
        atletaMock.setRut("18357914-2");
        atletaMock.setNombre("Nicolás Pizarro");
        atletaMock.setEmail("nico.pizarro@gymrat.cl");
        atletaMock.setRol(RolEnum.MIEMBRO);
    }

    @Test
    @DisplayName("POST /api/v1/atletas debe crear un atleta y devolver CREATED")
    void crearAtleta_DatosValidos_RetornaCreated() throws Exception {
        AtletaRequestDTO request = new AtletaRequestDTO();
        request.setRut("18357914-2");
        request.setNombre("Nicolás Pizarro");
        request.setEmail("nico.pizarro@gymrat.cl");
        request.setRol(RolEnum.MIEMBRO);

        when(atletaService.guardarAtleta(any(Atleta.class))).thenReturn(atletaMock);

        mockMvc.perform(post("/api/v1/atletas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.rut").value("18357914-2"))
            .andExpect(jsonPath("$.nombre").value("Nicolás Pizarro"))
            .andExpect(jsonPath("$.email").value("nico.pizarro@gymrat.cl"))
            .andExpect(jsonPath("$.rol").value("MIEMBRO"));

        verify(atletaService, times(1)).guardarAtleta(any(Atleta.class));
    }

    @Test
    @DisplayName("GET /api/v1/atletas/{rut} debe devolver atleta cuando existe")
    void obtenerPorRut_AtletaExiste_RetornaOk() throws Exception {
        when(atletaService.buscarPorRut("18357914-2")).thenReturn(atletaMock);

        mockMvc.perform(get("/api/v1/atletas/18357914-2"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.rut").value("18357914-2"))
            .andExpect(jsonPath("$.nombre").value("Nicolás Pizarro"));

        verify(atletaService, times(1)).buscarPorRut("18357914-2");
    }

    @Test
    @DisplayName("GET /api/v1/atletas debe listar todos los atletas")
    void listarAtletas_RetornaOk() throws Exception {
        when(atletaService.listarTodos()).thenReturn(List.of(atletaMock));

        mockMvc.perform(get("/api/v1/atletas"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1))
            .andExpect(jsonPath("$[0].rut").value("18357914-2"));

        verify(atletaService, times(1)).listarTodos();
    }

    @Test
    @DisplayName("DELETE /api/v1/atletas/{rut} debe eliminar el atleta y devolver NO_CONTENT")
    void eliminarAtleta_AtletaExiste_RetornaNoContent() throws Exception {
        doNothing().when(atletaService).eliminarPorRut("18357914-2");

        mockMvc.perform(delete("/api/v1/atletas/18357914-2"))
            .andExpect(status().isNoContent());

        verify(atletaService, times(1)).eliminarPorRut("18357914-2");
    }
}
