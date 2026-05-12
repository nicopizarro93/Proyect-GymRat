package com.example.ms_ejercicios.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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






@RestController
@RequestMapping("/api/v1/ejercicios")
@RequiredArgsConstructor
public class EjercicioController {

    private final EjercicioService ejercicioService;

    @PostMapping
    public ResponseEntity<Ejercicio>crearEjercicio(@Valid @RequestBody Ejercicio ejercicio) {
        Ejercicio nuevEjercicio=ejercicioService.guardarEjercicio(ejercicio);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevEjercicio);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ejercicio>buscarPorId(@PathVariable Long id) {
        Ejercicio ejercicio=ejercicioService.buscarPorId(id);
        return ResponseEntity.ok(ejercicio);
    }

    @GetMapping
    public ResponseEntity<List<Ejercicio>>listarEjercicios() {
        List<Ejercicio> ejercicios=ejercicioService.listarEjercicios();
        return ResponseEntity.ok(ejercicios);
    }

    @GetMapping("/grupo/{grupoMuscular}")
    public ResponseEntity<List<Ejercicio>>listarPorGrupo(@PathVariable GrupoMuscularEnum grupoMuscular) {
        return ResponseEntity.ok(ejercicioService.listarPorGrupoMuscular(grupoMuscular));
    }
    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminarEjercicio(@PathVariable Long id){
        ejercicioService.eliminarPorId(id);
        return ResponseEntity.noContent().build();
    }
    
}
