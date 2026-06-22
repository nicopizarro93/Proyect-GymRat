package com.example.ms_ejercicios.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.ms_ejercicios.model.DificultadEnum;
import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;
import com.example.ms_ejercicios.repository.EjercicioRepository;
import com.example.ms_ejercicios.service.EjercicioServiceImpl;

@ExtendWith(MockitoExtension.class)
public class EjercicioServiceTest {

    @Mock
    private EjercicioRepository ejercicioRepository;

    @InjectMocks
    private EjercicioServiceImpl ejercicioService;

    private Ejercicio ejercicio;

    @BeforeEach
    void setUp(){
        ejercicio= new Ejercicio(
            1L,
            "press banca",
            GrupoMuscularEnum.PECHO,
            DificultadEnum.INTERMEDIO
        );
    }

    @Test
    void guardarEjercicio_DeberiaGuardarCorrectamente(){
        when(ejercicioRepository.findByNombreEjercicio("press banca"))
        .thenReturn(Optional.empty());

        when(ejercicioRepository.save(ejercicio))
        .thenReturn(ejercicio);

        Ejercicio resultado=ejercicioService.guardarEjercicio(ejercicio);

        assertNotNull(resultado);
        assertEquals("press banca", resultado.getNombreEjercicio());

        verify(ejercicioRepository).save(ejercicio);
    }

    @Test
    void guardarEjercicio_DeberiaLanzarExcepcionSiExiste(){

        when(ejercicioRepository.findByNombreEjercicio("press banca"))
        .thenReturn(Optional.of(ejercicio));

        IllegalArgumentException exception=
        assertThrows(IllegalArgumentException.class, ()->{
            ejercicioService.guardarEjercicio(ejercicio);
        });

        assertEquals("ya existe un ejercicio con ese nombre", exception.getMessage());
    }

    //haremos test de buscar ejercicio por id en caso de que exista
    @Test
    void buscarPorId_DeberiaRetornarEjercicio(){
        when(ejercicioRepository.findById(1L))
                .thenReturn(Optional.of(ejercicio));

        Ejercicio resultado = ejercicioService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdEjercicio());
        assertEquals("press banca", resultado.getNombreEjercicio());

        verify(ejercicioRepository).findById(1L);
    }

    //haremos test para buscar ejercicio por id pero en esta ocasion inexistente
    @Test
    void buscarPorId_DeberiaLanzarExcepcionSiNoExiste(){
        when(ejercicioRepository.findById(1L))
                .thenReturn(Optional.empty());

        RuntimeException exception=
            assertThrows(RuntimeException.class, () ->{
                ejercicioService.buscarPorId(1L);
            });

        assertEquals("ejercicio no encontrado", exception.getMessage());
    }

    //test de listar ejercicios
    @Test
    void listarEjercicios_DeberiaRetornarLista(){
        List<Ejercicio> lista= List.of(ejercicio);

        when(ejercicioRepository.findAll())
                .thenReturn(lista);

        List<Ejercicio> resultado = ejercicioService.listarEjercicios();

        assertEquals(1, resultado.size());
        assertEquals("press banca", resultado.get(0).getNombreEjercicio());

        verify(ejercicioRepository).findAll();
    }

    //test eliminar ejercicios
    //como no devuelve nada solo verificamos que el repositorio fue llamado
    @Test
    void eliminarPorId_DeberiaEliminarCorrectamente(){

        when(ejercicioRepository.existsById(1L))
                .thenReturn(true);

        ejercicioService.eliminarPorId(1L);

        verify(ejercicioRepository).deleteById(1L);
    }
}
