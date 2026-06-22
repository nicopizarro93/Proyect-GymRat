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

// Habilitamos la integración de Mockito para las pruebas
@ExtendWith(MockitoExtension.class)
class AtletaControllerTest {

    // Simulamos la capa de servicio (al controlador no le importa cómo procesa el servicio, solo qué responde)
    @Mock
    private AtletaService atletaService;

    // Inyectamos el servicio falso directamente en el controlador que vamos a probar
    @InjectMocks
    private AtletaController atletaController;

    // Herramienta clave de Spring para simular peticiones HTTP (GET, POST, etc.) sin servidor real
    private MockMvc mockMvc;
    
    // Objeto de la librería Jackson que convierte clases de Java a texto JSON y viceversa
    private ObjectMapper objectMapper;
    
    private Atleta atletaMock;

    @BeforeEach
    void setUp() {
        // Configuramos MockMvc de manera aislada (standalone) exclusivamente para este controlador
        mockMvc = MockMvcBuilders.standaloneSetup(atletaController).build();
        objectMapper = new ObjectMapper();

        // Preparamos nuestro objeto simulado para usarlo en las respuestas de la API
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
        // PREPARAR: Creamos el DTO (los datos de entrada) que simula lo que envía un cliente en el Body
        AtletaRequestDTO request = new AtletaRequestDTO();
        request.setRut("18357914-2");
        request.setNombre("Nicolás Pizarro");
        request.setEmail("nico.pizarro@gymrat.cl");
        request.setRol(RolEnum.MIEMBRO);

        // Simulamos la respuesta exitosa del servicio
        when(atletaService.guardarAtleta(any(Atleta.class))).thenReturn(atletaMock);

        // ACTUAR Y AFIRMAR: Simulamos la petición POST a la ruta indicada
        mockMvc.perform(post("/api/v1/atletas")
                .contentType(MediaType.APPLICATION_JSON) // Indicamos que el contenido enviado es JSON
                .content(objectMapper.writeValueAsString(request))) // Convertimos nuestro DTO a String JSON
            .andExpect(status().isCreated()) // Verificamos que el servidor devuelva el código 201 (Created)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON)) // Verificamos que la respuesta sea de tipo JSON
            // jsonPath examina el JSON devuelto para confirmar que los campos son los correctos
            .andExpect(jsonPath("$.rut").value("18357914-2"))
            .andExpect(jsonPath("$.nombre").value("Nicolás Pizarro"))
            .andExpect(jsonPath("$.email").value("nico.pizarro@gymrat.cl"))
            .andExpect(jsonPath("$.rol").value("MIEMBRO"));

        // Verificamos que el controlador efectivamente le pasó los datos al servicio
        verify(atletaService, times(1)).guardarAtleta(any(Atleta.class));
    }

    @Test
    @DisplayName("GET /api/v1/atletas/{rut} debe devolver atleta cuando existe")
    void obtenerPorRut_AtletaExiste_RetornaOk() throws Exception {
        // PREPARAR: Configuramos el servicio para que retorne nuestro atletaMock
        when(atletaService.buscarPorRut("18357914-2")).thenReturn(atletaMock);

        // ACTUAR Y AFIRMAR: Simulamos una petición GET a la URL con el RUT
        mockMvc.perform(get("/api/v1/atletas/18357914-2"))
            .andExpect(status().isOk()) // Esperamos código HTTP 200 (OK)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.rut").value("18357914-2"))
            .andExpect(jsonPath("$.nombre").value("Nicolás Pizarro"));

        verify(atletaService, times(1)).buscarPorRut("18357914-2");
    }

    @Test
    @DisplayName("GET /api/v1/atletas debe listar todos los atletas")
    void listarAtletas_RetornaOk() throws Exception {
        // PREPARAR: El servicio devuelve una lista con nuestro atleta
        when(atletaService.listarTodos()).thenReturn(List.of(atletaMock));

        // ACTUAR Y AFIRMAR: Simulamos el GET general
        mockMvc.perform(get("/api/v1/atletas"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.length()").value(1)) // Verificamos que el array JSON devuelva 1 elemento
            .andExpect(jsonPath("$[0].rut").value("18357914-2")); // Verificamos el primer elemento del array

        verify(atletaService, times(1)).listarTodos();
    }

    @Test
    @DisplayName("DELETE /api/v1/atletas/{rut} debe eliminar el atleta y devolver NO_CONTENT")
    void eliminarAtleta_AtletaExiste_RetornaNoContent() throws Exception {
        // PREPARAR: Como el método eliminar devuelve 'void', usamos doNothing() para simular su ejecución
        doNothing().when(atletaService).eliminarPorRut("18357914-2");

        // ACTUAR Y AFIRMAR: Simulamos petición DELETE
        mockMvc.perform(delete("/api/v1/atletas/18357914-2"))
            .andExpect(status().isNoContent()); // Verificamos código 204 (No Content), respuesta estándar para borrados

        verify(atletaService, times(1)).eliminarPorRut("18357914-2");
    }
}