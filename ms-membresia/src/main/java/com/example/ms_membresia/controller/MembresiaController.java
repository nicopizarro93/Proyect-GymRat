package com.example.ms_membresia.controller;

import java.util.List;
import java.util.Map; // <-- Importante para leer el JSON

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_membresia.model.MembresiaModel;
import com.example.ms_membresia.service.MembresiaService;

import lombok.RequiredArgsConstructor;

@RestController // esta clase responderá a peticiones web
@RequestMapping("/api/v1/membresias") // La URL principal
@RequiredArgsConstructor
public class MembresiaController {

    private final MembresiaService membresiaService;

    // 1. URL para contratar un nuevo plan y darle permisos para ingresar al gym
    // Se usará así en Thunder Client: POST localhost:8082/api/v1/membresias/contratar
    @PostMapping("/contratar")
    public ResponseEntity<MembresiaModel> contratar(@RequestBody Map<String, String> request) {
        
        // Sacamos los datos del JSON (del Map) uno por uno
        String rutAtleta = request.get("rutAtleta");
        String tipoPlan = request.get("tipoPlan");
        int mesesDuracion = Integer.parseInt(request.get("mesesDuracion")); 
        
        MembresiaModel nuevaMembresia = membresiaService.contratarPlan(rutAtleta, tipoPlan, mesesDuracion);
        return new ResponseEntity<>(nuevaMembresia, HttpStatus.CREATED);
    }

    // 2. URL para ver la membresía actual de un atleta
    // Se usará así: GET localhost:8082/api/v1/membresias/111-1/actual
    @GetMapping("/{rutAtleta}/actual")
    public ResponseEntity<MembresiaModel> obtenerActual(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.obtenerMembresiaActual(rutAtleta));
    }

    // 3. URL para ver todo el historial de pagos de un atleta
    // Se usará así: GET localhost:8082/api/v1/membresias/111-1/historial
    @GetMapping("/{rutAtleta}/historial")
    public ResponseEntity<List<MembresiaModel>> obtenerHistorial(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.obtenerHistorialPorRut(rutAtleta));
    }

    // Desde aquí, Membresía usará el "teléfono" para pedirle datos a Asistencia
    // Se usará así: GET localhost:8082/api/v1/membresias/111-1/asistencias
    @GetMapping("/{rutAtleta}/asistencias")
    public ResponseEntity<List<Object>> verAsistencias(@PathVariable String rutAtleta) {
        return ResponseEntity.ok(membresiaService.verAsistenciasDesdeMembresia(rutAtleta));
    }
}