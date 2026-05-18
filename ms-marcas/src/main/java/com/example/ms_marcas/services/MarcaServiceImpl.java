package com.example.ms_marcas.services;

import java.util.List; // Permite trabajar con listas de marcas

import org.springframework.stereotype.Service; // Indica que esta clase es un servicio de Spring

import com.example.ms_marcas.client.AtletaClient; // Cliente Feign para comunicarse con el microservicio de atletas
import com.example.ms_marcas.model.Marca; // Entidad que representa una marca registrada
import com.example.ms_marcas.repository.MarcaRepository; // Repositorio para acceder a la base de datos
import com.example.ms_marcas.dto.MarcaRequestDTO; // DTO usado para recibir los datos desde una petición

import feign.FeignException; // Maneja errores al comunicarse con otros microservicios
import lombok.RequiredArgsConstructor; // Genera el constructor con los atributos final

@Service // Marca la clase como servicio para que Spring la gestione
@RequiredArgsConstructor // Inyecta automáticamente las dependencias final mediante constructor
public class MarcaServiceImpl implements MarcaService {

    private final MarcaRepository marcaRepository; // Se usa para guardar y consultar marcas en la BD
    private final AtletaClient atletaClient; // Se usa para validar si el atleta existe en otro microservicio

    @Override // Indica que este método viene de la interfaz MarcaService
    public Marca registrarIntento(MarcaRequestDTO dto) {
        try {
            // Valida remotamente que el atleta exista antes de registrar la marca
            atletaClient.obtenerAtletaPorRut(dto.getRutAtleta());
        } catch (FeignException.NotFound e) {
            // Si el atleta no existe, se informa con un error claro
            throw new IllegalArgumentException("Error: El atleta con RUT " + dto.getRutAtleta() + " no existe.");
        } catch (FeignException e) {
            // Si falla la comunicación entre microservicios, se lanza error general
            throw new RuntimeException("Error de comunicación al validar el atleta.");
        }

        // Crea una nueva entidad Marca para guardarla en la base de datos
        Marca nuevaMarca = new Marca();

        // Copia los datos recibidos desde el DTO hacia la entidad
        nuevaMarca.setRutAtleta(dto.getRutAtleta());
        nuevaMarca.setNombreEjercicio(dto.getNombreEjercicio());
        nuevaMarca.setPesoLevantado(dto.getPesoLevantado());

        // Toda marca nueva queda pendiente hasta que sea aprobada o rechazada
        nuevaMarca.setEstado("PENDIENTE");

        // Guarda la marca en la base de datos y devuelve el resultado
        return marcaRepository.save(nuevaMarca);
    }

    @Override // Implementa la búsqueda de marcas por RUT
    public List<Marca> obtenerPorRut(String rut) {
        // Busca todas las marcas asociadas a un atleta según su RUT
        return marcaRepository.findByRutAtleta(rut);
    }

    @Override // Implementa el listado general de marcas
    public List<Marca> listarTodos() {
        // Devuelve todas las marcas registradas en la base de datos
        return marcaRepository.findAll();
    }

    @Override // Implementa la búsqueda de una marca por ID
    public Marca buscarPorId(Long id) {
        // Busca la marca y lanza error si no existe
        return marcaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Marca no encontrada"));
    }

    @Override // Implementa la búsqueda de marcas aprobadas por ejercicio
    public List<Marca> obtenerMarcasAprobadasPorEjercicio(String nombreEjercicio) {
        // Devuelve marcas aprobadas de un ejercicio, ordenadas por mayor peso levantado
        return marcaRepository.findByNombreEjercicioAndEstadoOrderByPesoLevantadoDesc(nombreEjercicio, "APROBADA");
    }

    @Override // Implementa la actualización del estado de una marca
    public Marca actualizarEstado(Long id, String nuevoEstado) {
        // Busca la marca existente antes de modificarla
        Marca marca = buscarPorId(id);

        // Cambia el estado, por ejemplo: APROBADA o RECHAZADA
        marca.setEstado(nuevoEstado);

        // Guarda el cambio en la base de datos
        return marcaRepository.save(marca);
    }
}