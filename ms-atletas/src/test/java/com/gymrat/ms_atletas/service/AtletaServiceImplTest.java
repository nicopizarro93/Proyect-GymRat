package com.gymrat.ms_atletas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
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
    @DisplayName("buscarPorRut debe devolver atleta existente")
    void buscarPorRut_AtletaExiste_RetornaAtleta() {
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.of(atletaMock));

        Atleta resultado = atletaService.buscarPorRut("18357914-2");

        assertNotNull(resultado);
        assertEquals("18357914-2", resultado.getRut());
        assertEquals("Nicolás Pizarro", resultado.getNombre());
        assertEquals(RolEnum.MIEMBRO, resultado.getRol());
        verify(atletaRepository, times(1)).findByRut("18357914-2");
    }

    @Test
    @DisplayName("buscarPorRut debe lanzar IllegalArgumentException cuando no existe atleta")
    void buscarPorRut_AtletaNoExiste_LanzaIllegalArgumentException() {
        when(atletaRepository.findByRut("99999999-9")).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> atletaService.buscarPorRut("99999999-9")
        );

        assertTrue(exception.getMessage().contains("No se encontró un atleta con ese RUT"));
        verify(atletaRepository, times(1)).findByRut("99999999-9");
    }

    @Test
    @DisplayName("guardarAtleta debe persistir cuando no existe el RUT")
    void guardarAtleta_DatosValidos_RetornaAtletaGuardado() {
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.empty());
        when(atletaRepository.save(any(Atleta.class))).thenReturn(atletaMock);

        Atleta resultado = atletaService.guardarAtleta(atletaMock);

        assertNotNull(resultado);
        assertEquals("18357914-2", resultado.getRut());
        verify(atletaRepository, times(1)).findByRut("18357914-2");
        verify(atletaRepository, times(1)).save(atletaMock);
    }

    @Test
    @DisplayName("guardarAtleta debe fallar cuando el RUT ya está registrado")
    void guardarAtleta_RutDuplicado_LanzaIllegalArgumentException() {
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.of(atletaMock));

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> atletaService.guardarAtleta(atletaMock)
        );

        assertEquals("ya existe un atleta con ese RUT", exception.getMessage());
        verify(atletaRepository, times(1)).findByRut("18357914-2");
        verify(atletaRepository, never()).save(any(Atleta.class));
    }

    @Test
    @DisplayName("listarTodos debe devolver todos los atletas")
    void listarTodos_RetornaListaDeAtletas() {
        when(atletaRepository.findAll()).thenReturn(List.of(atletaMock));

        List<Atleta> resultado = atletaService.listarTodos();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("18357914-2", resultado.get(0).getRut());
        verify(atletaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("eliminarPorRut debe borrar el atleta existente")
    void eliminarPorRut_AtletaExiste_EliminaAtleta() {
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.of(atletaMock));

        atletaService.eliminarPorRut("18357914-2");

        verify(atletaRepository, times(1)).findByRut("18357914-2");
        verify(atletaRepository, times(1)).delete(atletaMock);
    }
}
