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

// Le indicamos a JUnit que habilite las funcionalidades de Mockito en esta clase
@ExtendWith(MockitoExtension.class)
class AtletaServiceImplTest {

    // Creamos un "Doble" (Mock) de la base de datos para no afectarla realmente
    @Mock
    private AtletaRepository atletaRepository;

    // Inyectamos el Mock falso creado arriba dentro de la instancia real de nuestro Servicio
    @InjectMocks
    private AtletaServiceImpl atletaService;

    // Variable global para almacenar datos de prueba reutilizables
    private Atleta atletaMock;

    // Este método se ejecutará SIEMPRE antes de cada @Test para preparar el entorno limpio
    @BeforeEach
    void setUp() {
        // Preparamos un Atleta ficticio para usarlo en nuestras pruebas
        atletaMock = new Atleta();
        atletaMock.setId(1L);
        atletaMock.setRut("18357914-2");
        atletaMock.setNombre("Nicolás Pizarro");
        atletaMock.setRol(RolEnum.MIEMBRO);
    }

    // Le indica a JUnit que este método es un caso de prueba
    @Test
    // Nombre descriptivo que aparecerá en la consola para facilitar la lectura de resultados
    @DisplayName("buscarPorRut debe devolver atleta existente")
    void buscarPorRut_AtletaExiste_RetornaAtleta() {
        // PREPARAR: Instruimos al Mock sobre qué responder cuando se llame al método findByRut
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.of(atletaMock));

        // ACTUAR: Ejecutamos el método real del servicio que queremos probar
        Atleta resultado = atletaService.buscarPorRut("18357914-2");

        // AFIRMAR: Comprobamos que el resultado no sea nulo y que los datos coincidan
        assertNotNull(resultado);
        assertEquals("18357914-2", resultado.getRut());
        assertEquals("Nicolás Pizarro", resultado.getNombre());
        assertEquals(RolEnum.MIEMBRO, resultado.getRol());
        
        // VERIFICAR: Aseguramos que el repositorio fue consultado exactamente 1 vez
        verify(atletaRepository, times(1)).findByRut("18357914-2");
    }

    @Test
    @DisplayName("buscarPorRut debe lanzar IllegalArgumentException cuando no existe atleta")
    void buscarPorRut_AtletaNoExiste_LanzaIllegalArgumentException() {
        // PREPARAR: Simulamos que la base de datos devuelve un resultado vacío
        when(atletaRepository.findByRut("99999999-9")).thenReturn(Optional.empty());

        // ACTUAR Y AFIRMAR: Verificamos que se lance la excepción correcta cuando el atleta no se encuentra
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> atletaService.buscarPorRut("99999999-9")
        );

        // Validamos que el mensaje de la excepción sea exactamente el esperado
        assertTrue(exception.getMessage().contains("No se encontró un atleta con ese RUT"));
        verify(atletaRepository, times(1)).findByRut("99999999-9");
    }

    @Test
    @DisplayName("guardarAtleta debe persistir cuando no existe el RUT")
    void guardarAtleta_DatosValidos_RetornaAtletaGuardado() {
        // PREPARAR: Aseguramos que el RUT no exista previamente y definimos qué devolverá el 'save'
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.empty());
        when(atletaRepository.save(any(Atleta.class))).thenReturn(atletaMock);

        // ACTUAR: Ejecutamos el método de guardar
        Atleta resultado = atletaService.guardarAtleta(atletaMock);

        // AFIRMAR: Validamos la respuesta y verificamos que el repositorio ejecutó la búsqueda y el guardado
        assertNotNull(resultado);
        assertEquals("18357914-2", resultado.getRut());
        verify(atletaRepository, times(1)).findByRut("18357914-2");
        verify(atletaRepository, times(1)).save(atletaMock);
    }

    @Test
    @DisplayName("guardarAtleta debe fallar cuando el RUT ya está registrado")
    void guardarAtleta_RutDuplicado_LanzaIllegalArgumentException() {
        // PREPARAR: Simulamos que el RUT ya existe en la base de datos
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.of(atletaMock));

        // ACTUAR Y AFIRMAR: Esperamos que lance error al intentar guardar un duplicado
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> atletaService.guardarAtleta(atletaMock)
        );

        assertEquals("ya existe un atleta con ese RUT", exception.getMessage());
        verify(atletaRepository, times(1)).findByRut("18357914-2");
        
        // IMPORTANTE: Aseguramos que el método 'save' NUNCA fue llamado debido al error
        verify(atletaRepository, never()).save(any(Atleta.class));
    }

    @Test
    @DisplayName("listarTodos debe devolver todos los atletas")
    void listarTodos_RetornaListaDeAtletas() {
        // PREPARAR: Simulamos que la BD devuelve una lista con nuestro atleta de prueba
        when(atletaRepository.findAll()).thenReturn(List.of(atletaMock));

        // ACTUAR: Llamamos al método listarTodos del servicio
        List<Atleta> resultado = atletaService.listarTodos();

        // AFIRMAR: Comprobamos que la lista contenga 1 elemento y sea nuestro atleta
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("18357914-2", resultado.get(0).getRut());
        verify(atletaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("eliminarPorRut debe borrar el atleta existente")
    void eliminarPorRut_AtletaExiste_EliminaAtleta() {
        // PREPARAR: Simulamos que el atleta existe en la base de datos para poder borrarlo
        when(atletaRepository.findByRut("18357914-2")).thenReturn(Optional.of(atletaMock));

        // ACTUAR: Llamamos al método eliminar
        atletaService.eliminarPorRut("18357914-2");

        // AFIRMAR: Verificamos que se haya buscado el atleta y posteriormente se haya llamado al método delete
        verify(atletaRepository, times(1)).findByRut("18357914-2");
        verify(atletaRepository, times(1)).delete(atletaMock);
    }
}