
# Gestión de Incidencias - Prueba Técnica BDO

Aplicación web para registrar y consultar incidencias técnicas de un equipo de soporte.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Spring Data JPA / Hibernate
- H2 en memoria
- Maven
- JUnit 5 y Mockito
- HTML, CSS y JavaScript

## Cómo ejecutar el proyecto

Requisitos:
- Java 17 instalado
- Puerto 8080 disponible

Desde la raíz del repositorio:

Windows:
.\mvnw.cmd spring-boot:run

Linux / macOS:
./mvnw spring-boot:run

Abrir en el navegador:
http://localhost:8080/

La aplicación utiliza H2 en memoria. Al reiniciar, se vuelven
a cargar los datos de ejemplo definidos en schema.sql.

## Ejecutar los tests

Windows:
.\mvnw.cmd clean test

Linux / macOS:
./mvnw clean test

Se incluyen tests unitarios de la capa Service con JUnit 5
y Mockito, incluyendo la regla que impide reabrir
incidencias cerradas.

## API REST

GET /api/incidencias
Lista todas las incidencias.

GET /api/incidencias?estado=ABIERTA
Filtra las incidencias por estado.

POST /api/incidencias
Crea una incidencia con estado inicial ABIERTA.
Devuelve HTTP 201 si se crea correctamente.

PATCH /api/incidencias/{id}/estado
Actualiza el estado de una incidencia.

Errores principales:
- 400: datos inválidos.
- 404: incidencia inexistente.
- 409: intento de reabrir una incidencia cerrada.

## Funcionalidades del frontend

- Visualización de incidencias en una tabla.
- Filtrado por estado sin recargar la página.
- Mensajes de error cuando falla la API.
- Formulario de creación de incidencias.

## Decisiones técnicas

Se ha utilizado una arquitectura por capas para separar
las responsabilidades:

- Controller: recibe y responde peticiones HTTP.
- Service: aplica validaciones y reglas de negocio.
- Repository: gestiona el acceso a datos mediante JPA.
- Model: representa las entidades de la aplicación.
- DTO: define los datos recibidos en las operaciones REST.

Se ha elegido H2 en memoria para facilitar la ejecución
sin necesidad de configurar una base de datos externa.

El frontend utiliza HTML, CSS y JavaScript sin frameworks,
de acuerdo con el enunciado.

## Posibles mejoras

- Persistencia de datos mediante una base de datos externa.
- Tests de integración para los endpoints REST.
- Paginación y ordenación de incidencias.
- Interfaz para cambiar el estado de las incidencias.
- Mejoras de validación y presentación de errores.

## Uso de inteligencia artificial

Se ha utilizado ChatGPT como apoyo durante el desarrollo
para comprender conceptos de Spring Boot, JPA, DTO,
JUnit 5, Mockito y gestión de excepciones.

También se ha utilizado como asistencia para plantear
ejemplos de implementación, revisar código y preparar
pruebas manuales.

El código se ha trabajado y probado durante el desarrollo,
revisando su funcionamiento y las decisiones técnicas.
