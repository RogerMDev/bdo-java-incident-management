package com.example.incidencias.service;

import com.example.incidencias.model.Estado;
import com.example.incidencias.model.Incidencia;
import com.example.incidencias.repository.IncidenciaRepository;
import com.example.incidencias.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import com.example.incidencias.model.Prioridad;
import com.example.incidencias.model.Usuario;
import java.time.LocalDateTime;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final UsuarioRepository usuarioRepository;

    public IncidenciaService(
            IncidenciaRepository incidenciaRepository,
            UsuarioRepository usuarioRepository) {

        this.incidenciaRepository = incidenciaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Incidencia> listarIncidencias(Estado estado){
        if (estado == null){
            return incidenciaRepository.findAll();
        }
        return incidenciaRepository.findByEstado(estado);
    }

    public Incidencia crearIncidencia(
            String titulo,
            String descripcion,
            Prioridad prioridad,
            Long usuarioId) {

        // Validación titulo
        if (titulo == null || titulo.isBlank() || titulo.length() > 100) {
            throw new IllegalArgumentException("El titulo debe tener entre 1 y 100 caracteres");
        }

        // Validación prioridad
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad es obligatoria");
        }

        // Creación la incidencia
        Incidencia incidencia = new Incidencia();
        incidencia.setTitulo(titulo);
        incidencia.setDescripcion(descripcion);
        incidencia.setPrioridad(prioridad);
        incidencia.setEstado(Estado.ABIERTA);
        incidencia.setFechaCreacion(LocalDateTime.now());

        // Asignar un usuario si se proporciona su identificador
        if (usuarioId != null) {
            Usuario usuario = usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new IllegalArgumentException(
                            "El usuario asignado no existe"));

            incidencia.setUsuarioAsignado(usuario);
        }

        // Guardar la incidencia en la base de datos
        return incidenciaRepository.save(incidencia);
    }



    public Incidencia cambiarEstado(Long id, Estado nuevoEstado) {

        // Comprobar que el nuevo estado es valido, error 400
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El nuevo estado es obligatorio");
        }

        // Buscar la incidencia en la base de datos, error 404
        Incidencia incidencia = incidenciaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(
                        "La incidencia no existe"));

        // Una incidencia cerrada no puede reabrirse, error 409
        if (incidencia.getEstado() == Estado.CERRADA
                && nuevoEstado != Estado.CERRADA) {
            throw new IllegalStateException(
                    "Una incidencia cerrada no puede reabrirse");
        }

        // Actualizar el estado y guardar
        incidencia.setEstado(nuevoEstado);

        return incidenciaRepository.save(incidencia);
    }

}
