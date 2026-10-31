
package com.example.incidencias.service;

import com.example.incidencias.model.Estado;
import com.example.incidencias.model.Incidencia;
import com.example.incidencias.model.Prioridad;
import com.example.incidencias.repository.IncidenciaRepository;
import com.example.incidencias.repository.UsuarioRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IncidenciaServiceTest {

    private IncidenciaRepository incidenciaRepository;
    private UsuarioRepository usuarioRepository;
    private IncidenciaService incidenciaService;

    @BeforeEach
    void prepararTest() {
        incidenciaRepository = mock(IncidenciaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);

        incidenciaService = new IncidenciaService(
                incidenciaRepository, usuarioRepository);
    }

    @Test
    void crearIncidenciaDebeTenerEstadoAbierta() {

        // Crear una incidencia sin usuario asignado
        incidenciaService.crearIncidencia(
                "Error WiFi",
                "No funciona la conexion",
                Prioridad.ALTA,
                null
        );

        // Capturar la incidencia enviada al repositorio
        ArgumentCaptor<Incidencia> captor = ArgumentCaptor.forClass(Incidencia.class);

        verify(incidenciaRepository).save(captor.capture());

        // Comprobar que se guarda con estado ABIERTA
        assertEquals(Estado.ABIERTA, captor.getValue().getEstado());
    }

    @Test
    void noPermitirReabrirIncidenciaCerrada() {

        // Preparar una incidencia que ya esta cerrada
        Incidencia incidencia = new Incidencia();
        incidencia.setId(1L);
        incidencia.setEstado(Estado.CERRADA);

        // Simular la busqueda de la incidencia
        when(incidenciaRepository.findById(1L))
                .thenReturn(Optional.of(incidencia));

        // Comprobar que se rechaza la reapertura
        assertThrows(IllegalStateException.class, () ->
                incidenciaService.cambiarEstado(1L, Estado.ABIERTA)
        );

        // Comprobar que no se guardan cambios
        verify(incidenciaRepository, never()).save(any(Incidencia.class));
    }
}

