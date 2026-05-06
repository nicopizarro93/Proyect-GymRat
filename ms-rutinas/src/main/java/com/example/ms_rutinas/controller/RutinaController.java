package com.example.ms_rutinas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ms_rutinas.model.Rutina;
import com.example.ms_rutinas.service.RutinaService;

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
@RequestMapping("/api/v1/rutinas")
@RequiredArgsConstructor
public class RutinaController {

    private final RutinaService service;

    @PostMapping
    public ResponseEntity<Rutina> crear(@Valid@RequestBody Rutina rutina) {
       
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardarRutina(rutina));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rutina> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<Rutina>> listar(){
        return ResponseEntity.ok(service.listarRutinas());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        service.eliminarRutina(id);
        return ResponseEntity.noContent().build();
    }
    
}
