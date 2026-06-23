package com.example.ms_ejercicios.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_ejercicios.dto.EjercicioRequestDTO;
import com.example.ms_ejercicios.model.Ejercicio;
import com.example.ms_ejercicios.model.GrupoMuscularEnum;
import com.example.ms_ejercicios.service.EjercicioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/api/v1/ejercicios")
@RequiredArgsConstructor
@Tag(name="Ejercicios", description = "Operaciones realcionadas con los ejercicios")
public class EjercicioController {

    private final EjercicioService ejercicioService;

    @Operation(summary = "Crear un nuevo ejercicio")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Ejercicio creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en el DTO")
    })
    @PostMapping
    public ResponseEntity<Ejercicio> crearEjercicio(@Valid @RequestBody EjercicioRequestDTO dto) {
        Ejercicio nuevoEjercicio = new Ejercicio();
        // Convertimos todo a mayúsculas o capitalizado si prefieres unificar el formato
        nuevoEjercicio.setNombreEjercicio(dto.getNombreEjercicio());
        nuevoEjercicio.setGrupoMuscular(dto.getGrupoMuscular());
        nuevoEjercicio.setDificultad(dto.getDificultad());

        Ejercicio ejercicioGuardado = ejercicioService.guardarEjercicio(nuevoEjercicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(ejercicioGuardado);
    }

    @Operation(summary = "Buscar ejercicio por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio>buscarPorId(@PathVariable Long id) {
        Ejercicio ejercicio=ejercicioService.buscarPorId(id);
        return ResponseEntity.ok(ejercicio);
    }

    @Operation(summary = "Listar todos los ejercicios")
    @GetMapping
    public ResponseEntity<List<Ejercicio>>listarEjercicios() {
        List<Ejercicio> ejercicios=ejercicioService.listarEjercicios();
        return ResponseEntity.ok(ejercicios);
    }

    @Operation(summary = "Listar ejercicios segun grupo muscular")
    @GetMapping("/grupo/{grupoMuscular}")
    public ResponseEntity<List<Ejercicio>>listarPorGrupo(@PathVariable GrupoMuscularEnum grupoMuscular) {
        return ResponseEntity.ok(ejercicioService.listarPorGrupoMuscular(grupoMuscular));
    }
    
    @Operation(summary = "eliminar un ejercicio por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminarEjercicio(@PathVariable Long id){
        ejercicioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar ejercicio por nombre exacto")
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Ejercicio> buscarPorNombre(@PathVariable String nombre){
        Ejercicio ejercicio = ejercicioService.buscarPorNombre(nombre);
        return ResponseEntity.ok(ejercicio);

    }
    
}
