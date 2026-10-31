
package com.example.incidencias.repository;

import com.example.incidencias.model.Estado;
import com.example.incidencias.model.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    List<Incidencia> findByEstado(Estado estado);
}
