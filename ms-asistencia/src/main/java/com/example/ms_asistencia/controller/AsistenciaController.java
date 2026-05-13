package com.example.ms_asistencia.controller;

import java.util.List;
import java.util.Map; // Agregado para poder recibir el JSON como un mapa

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
@RequestMapping("/api/v1/asistencias") // Configura la URL base. empezará con localhost:8084/api/v1/asistencias
@RequiredArgsConstructor
public class AsistenciaController {

    private final AsistenciaService asistenciaService; // Conectamos(Controller)con el(Service).

    //Para simular que un atleta pasa su tarjeta por el torniquete
    @PostMapping("/registrar") // Escucha peticiones POST (crear datos) en la ruta /api/v1/asistencias/registrar
    public ResponseEntity<Asistencia> registrar(@RequestBody Map<String, String> request) { 
        
        // Extraemos el valor "rutAtleta" del JSON que nos envían en el Body
        String rutAtleta = request.get("rutAtleta");
        
        // 1. Le pasa el RUT al Service para que haga su magia, ponga la hora y lo guarde. El resultado se guarda en 'asistencia'.
        Asistencia asistencia = asistenciaService.registrarAsistencia(rutAtleta);
        
        // Si el service devuelve null (por RUT vacío o inexistente), devolvemos un error 400 Bad Request
        if (asistencia == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        // 2. Devuelve la asistencia recién creada a la pantalla, junto con un código HTTP 201 "Recurso Creado con exito"
        return new ResponseEntity<>(asistencia, HttpStatus.CREATED);
    }

    // Para ver el historial de un atleta (cuántas veces ha venido)
    @GetMapping("/{rutAtleta}") // Escucha peticiones GET (leer datos). {} significan que esa parte de la URL es dinámica.
    public ResponseEntity<List<Asistencia>> obtenerHistorial(@PathVariable String rutAtleta) { // @PathVariable atrapa el valor de las llaves {}
        // Va al Service, busca la lista completa de ese RUT, la mete en un sobre con código 200 (OK) y la devuelve.
        return ResponseEntity.ok(asistenciaService.obtenerAsistenciasPorRut(rutAtleta));
    }

    @GetMapping
    public ResponseEntity<List<Asistencia>> obtenerTodas() {
        // Devuelve la lista completa de asistencias, con código 200 (OK)
        return ResponseEntity.ok(asistenciaService.obtenerTodasAsistencias());
    }
}