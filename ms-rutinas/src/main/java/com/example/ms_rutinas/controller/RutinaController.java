package com.example.ms_rutinas.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ms_rutinas.client.EjerciciosClient;
import com.example.ms_rutinas.dto.EjercicioResponseDTO;
import com.example.ms_rutinas.dto.RutinaResponseDTO;
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
import org.springframework.web.bind.annotation.PutMapping;

/**
 * Controlador REST encargado de exponer los endpoints para gestionar rutinas.
 * Recibe las solicitudes HTTP y delega la lógica de negocio al servicio de rutinas.
 */
@RestController
@RequestMapping("/api/v1/rutinas")
@RequiredArgsConstructor
public class RutinaController {

    /**
     * Servicio encargado de la lógica de negocio relacionada con rutinas.
     */
    private final RutinaService service;

    /**
     * Cliente usado para consultar ejercicios desde el microservicio de ejercicios.
     */
    private final EjerciciosClient ejerciciosClient;

    /**
     * Crea una nueva rutina validando los datos recibidos.
     *
     * @param rutina datos de la rutina a crear.
     * @return rutina creada con estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Rutina> crear(@Valid@RequestBody Rutina rutina) {
    
    
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.guardarRutina(rutina));
    }

    /**
     * Obtiene una rutina mediante su identificador.
     *
     * @param id identificador de la rutina.
     * @return rutina encontrada.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Rutina> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    /**
     * Lista todas las rutinas registradas.
     *
     * @return lista de rutinas existentes.
     */
    @GetMapping
    public ResponseEntity<List<Rutina>> listar(){
        return ResponseEntity.ok(service.listarRutinas());
    }

    /**
     * Obtiene una rutina completa, incluyendo la información detallada de sus ejercicios.
     *
     * @param id identificador de la rutina.
     * @return rutina con sus ejercicios detallados.
     */
    @GetMapping("/{id}/completa")
    public ResponseEntity<RutinaResponseDTO> obtenerRutinaCompleta(@PathVariable Long id){

        return ResponseEntity.ok(
            service.obtenerRutinaCompleta(id)
        );
    }

    /**
     * Lista todas las rutinas con la información completa de sus ejercicios.
     *
     * @return lista de rutinas completas.
     */
    @GetMapping("/completas")
    public ResponseEntity<List<RutinaResponseDTO>> listarRutinasCompletas(){
        return ResponseEntity.ok(
            service.listarRutinasCompletas()
        );
    }



    /**
     * Elimina una rutina existente mediante su identificador.
     *
     * @param id identificador de la rutina a eliminar.
     * @return respuesta sin contenido cuando la eliminación es exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        service.eliminarRutina(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene todos los ejercicios disponibles desde el microservicio de ejercicios.
     *
     * @return lista de ejercicios disponibles.
     */
    @GetMapping("/ejercicios")
    public ResponseEntity<List<EjercicioResponseDTO>> obtenerEjercicios(){
        return ResponseEntity.ok(ejerciciosClient.listarEjercicios());
    }

    /**
     * Actualiza una rutina existente con nuevos datos.
     *
     * @param id identificador de la rutina a actualizar.
     * @param rutina nuevos datos de la rutina.
     * @return rutina actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Rutina>actualizarRutina(@PathVariable Long id,@Valid @RequestBody Rutina rutina) {
    
    
        return ResponseEntity.ok(service.actualizarRutina(id, rutina));
    }

}
