package com.gymrat.ms_atletas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gymrat.ms_atletas.model.Atleta;
import com.gymrat.ms_atletas.model.RolEnum;
import com.gymrat.ms_atletas.repository.AtletaRepository;
import com.gymrat.ms_atletas.services.AtletaServiceImpl;



// 1. Le decimos a JUnit que vamos a usar Mockito en esta clase
@ExtendWith(MockitoExtension.class)
class AtletaServiceImplTest {

    // 2. Creamos un "Doble de Acción" (Mock) de la base de datos
    @Mock
    private AtletaRepository atletaRepository;

    // 3. Inyectamos ese Mock falso dentro de nuestro Servicio REAL
    @InjectMocks
    private AtletaServiceImpl atletaService;

    // Variables globales para usar en las pruebas
    private Atleta atletaMock;

    @BeforeEach
    void setUp() {
        // Preparamos un Atleta de mentira antes de cada prueba
        atletaMock = new Atleta();
        atletaMock.setId(1L);
        atletaMock.setRut("18357914-2");
        atletaMock.setNombre("Nicolás Pizarro");
        atletaMock.setRol(RolEnum.MIEMBRO);
    }

    @Test
    @DisplayName("Debe encontrar y retornar un atleta cuando el RUT existe")
    void buscarPorRut_AtletaExiste_RetornaAtleta() {
        // Arrange (Preparar): Le enseñamos al mock cómo debe comportarse
        // Le decimos: "Cuando alguien llame a findByRut con este RUT, devuelve nuestro atletaMock"
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.of(atletaMock));

        // Act (Actuar): Llamamos al método real de nuestro servicio
        Atleta resultado = atletaService.buscarPorRut("18357914-2");

        // Assert (Afirmar): Verificamos que el resultado es el correcto
        assertNotNull(resultado, "El atleta no debería ser nulo");
        assertEquals("Nicolás Pizarro", resultado.getNombre(), "El nombre no coincide");
        assertEquals(RolEnum.MIEMBRO, resultado.getRol(), "El rol no coincide");

        // (Opcional) Verificamos que el servicio realmente consultó a la base de datos 1 vez
        verify(atletaRepository, times(1)).findByRut("18357914-2");
    }

    @Test
    @DisplayName("Debe lanzar RuntimeException cuando el RUT no existe")
    void buscarPorRut_AtletaNoExiste_LanzaExcepcion() {
        // Arrange: Enseñamos al mock a devolver "Vacío" simulando que no encontró nada
        when(atletaRepository.findByRut("99999999-9")).thenReturn(Optional.empty());

        // Act & Assert: Verificamos que el servicio lance el error correcto
        RuntimeException excepcion = assertThrows(
            RuntimeException.class, 
            () -> atletaService.buscarPorRut("99999999-9")
        );

        // Verificamos que el mensaje sea exactamente el que programaste
        assertEquals("Atleta no encontrado", excepcion.getMessage());
    }

    @Test
    @DisplayName("Debe guardar un atleta exitosamente")
    void guardarAtleta_DatosValidos_RetornaAtletaGuardado() {
        // Arrange: Cuando el repository intente guardar CUALQUIER objeto de tipo Atleta, devuelve el nuestro
        when(atletaRepository.save(any(Atleta.class))).thenReturn(atletaMock);

        // Act
        Atleta resultado = atletaService.guardarAtleta(atletaMock);

        // Assert
        assertNotNull(resultado.getId(), "El ID no debería ser nulo tras guardar");
        assertEquals("18357914-2", resultado.getRut());
        
        // Verificamos que el método save() del repositorio fue llamado exactamente 1 vez
        verify(atletaRepository, times(1)).save(atletaMock);
    }
}