package com.example.ms_asistencia.controller;

import java.util.List;
import java.util.Map; // <-- Agregamos Map para leer el JSON correctamente

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.service.AsistenciaService;

import lombok.RequiredArgsConstructor;

@RestController // Creamos la clase controller
@RequestMapping("/api/v1/asistencias") // Configura la URL base
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService; 

    //Para simular que un atleta pasa su tarjeta por el torniquete
    @PostMapping("/registrar") 
    public ResponseEntity<Asistencia> registrar(@RequestBody Map<String, String> request) { 
        
        // Magia aquí: Extraemos SOLO el valor limpio del RUT, dejando fuera las llaves del JSON
        String rutAtleta = request.get("rutAtleta");
        
        // 1. Le pasa el RUT al Service para que haga su magia
        Asistencia asistencia = asistenciaService.registrarAsistencia(rutAtleta);
        
        if (asistencia == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        // 2. Devuelve la asistencia
        return new ResponseEntity<>(asistencia, HttpStatus.CREATED);
    }

    // Para ver el historial de un atleta
    @GetMapping("/{rutAtleta}") 
    public ResponseEntity<List<Asistencia>> obtenerHistorial(@PathVariable String rutAtleta) { 
        return ResponseEntity.ok(asistenciaService.obtenerAsistenciasPorRut(rutAtleta));
    }

    @GetMapping
    public ResponseEntity<List<Asistencia>> obtenerTodas() {
        return ResponseEntity.ok(asistenciaService.obtenerTodasAsistencias());
    }
}