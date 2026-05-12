package com.example.ms_asistencia.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; // Herramienta de Lombok para "enchufar" el Service automáticamente
import org.springframework.web.bind.annotation.GetMapping; // Nos permite devolver códigos web (ej: 200 OK, 201 Creado)
import org.springframework.web.bind.annotation.PathVariable; // Es el "sobre" donde metemos la respuesta para enviarla por internet
import org.springframework.web.bind.annotation.PostMapping; // Trae las herramientas para recibir peticiones (GET, POST, etc.)
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_asistencia.model.Asistencia;
import com.example.ms_asistencia.service.AsistenciaService;

import lombok.RequiredArgsConstructor;



@RestController // Creamos la clase controller

@RequestMapping("/api/v1") // Configura la URL base. empezará con localhost:8084/api/asistencias********

@RequiredArgsConstructor

public class AsistenciaController {



    private final AsistenciaService asistenciaService; // Conectamos(Controller)con el(Service).



    //Para simular que un atleta pasa su tarjeta por el torniquete

    @PostMapping("/registrar") // Escucha peticiones POST (crear datos) en la ruta /api/v1/registrar

    public ResponseEntity<Asistencia> registrar(@RequestParam String rutAtleta) {

       

        // 1. Le pasa el RUT al Service para que haga su magia, ponga la hora y lo guarde. El resultado se guarda en 'asistencia'.

        Asistencia asistencia = asistenciaService.registrarAsistencia(rutAtleta);

       

        // 2. Devuelve la asistencia recién creada a la pantalla, junto con un código HTTP 201 "Recurso Creado con exito"

        return new ResponseEntity<>(asistencia, HttpStatus.CREATED);

    }



    // Para ver el historial de un atleta (cuántas veces ha venido)

    @GetMapping("/{rutAtleta}") // Escucha peticiones GET (leer datos). {} significan que esa parte de la URL es dinámica.

    public ResponseEntity<List<Asistencia>> obtenerHistorial(@PathVariable String rutAtleta) { // @PathVariable atrapa el valor de las llaves {}

       

        // Va al Service, busca la lista completa de ese RUT, la mete en un sobre con código 200 (OK) y la devuelve.

        return ResponseEntity.ok(asistenciaService.obtenerAsistenciasPorRut(rutAtleta));

    }

}