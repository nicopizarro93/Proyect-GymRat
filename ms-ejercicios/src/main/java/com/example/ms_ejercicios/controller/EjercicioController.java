package com.example.ms_ejercicios.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_ejercicios.dto.EjercicioRequestDTO;
import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;
import com.example.ms_ejercicios.service.EjercicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controlador REST encargado de exponer los endpoints
 * relacionados con la gestión de ejercicios.
 */
@RestController
@RequestMapping("/api/v1/ejercicios")
@RequiredArgsConstructor
public class EjercicioController {

    /**
     * Servicio que contiene la lógica de negocio de los ejercicios.
     */
    private final EjercicioService ejercicioService;

    /**
     * Crea un nuevo ejercicio a partir de los datos recibidos en el DTO.
     *
     * @param dto datos necesarios para crear el ejercicio.
     * @return ejercicio creado con estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Ejercicio> crearEjercicio(@Valid @RequestBody EjercicioRequestDTO dto) {
        Ejercicio nuevoEjercicio = new Ejercicio();
        nuevoEjercicio.setNombreEjercicio(dto.getNombreEjercicio());
        nuevoEjercicio.setGrupoMuscular(dto.getGrupoMuscular());
        nuevoEjercicio.setDificultad(dto.getDificultad());

        Ejercicio ejercicioGuardado = ejercicioService.guardarEjercicio(nuevoEjercicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioGuardado);
    }

    /**
     * Busca un ejercicio por su identificador.
     *
     * @param id identificador del ejercicio.
     * @return ejercicio encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio>buscarPorId(@PathVariable Long id) {
        Ejercicio ejercicio=ejercicioService.buscarPorId(id);
        return ResponseEntity.ok(ejercicio);
    }

    /**
     * Lista todos los ejercicios registrados.
     *
     * @return lista completa de ejercicios.
     */
    @GetMapping
    public ResponseEntity<List<Ejercicio>>listarEjercicios() {
        List<Ejercicio> ejercicios=ejercicioService.listarEjercicios();
        return ResponseEntity.ok(ejercicios);
    }

    /**
     * Lista los ejercicios filtrados por grupo muscular.
     *
     * @param grupoMuscular grupo muscular por el cual se desea filtrar.
     * @return lista de ejercicios pertenecientes al grupo muscular indicado.
     */
    @GetMapping("/grupo/{grupoMuscular}")
    public ResponseEntity<List<Ejercicio>>listarPorGrupo(@PathVariable GrupoMuscularEnum grupoMuscular) {
        return ResponseEntity.ok(ejercicioService.listarPorGrupoMuscular(grupoMuscular));
    }

    /**
     * Elimina un ejercicio según su identificador.
     *
     * @param id identificador del ejercicio a eliminar.
     * @return respuesta sin contenido si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminarEjercicio(@PathVariable Long id){
        ejercicioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Busca un ejercicio por su nombre exacto.
     *
     * @param nombre nombre del ejercicio a buscar.
     * @return ejercicio encontrado o estado HTTP 404 si no existe.
     */
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<?> buscarPorNombre(@PathVariable String nombre){
        return ejercicioService.buscarPorNombre(nombre)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
