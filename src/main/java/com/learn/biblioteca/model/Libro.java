package com.learn.biblioteca.model;

import java.time.Year;
import java.util.Objects;
import java.util.UUID;

public record Libro(UUID id, String titulo, String autor, int anio, EstadoLibro estado) {

    public Libro {
        // Validaciones de nulidad
        Objects.requireNonNull(titulo, "El título no puede ser nulo");
        Objects.requireNonNull(autor, "El autor no puede ser nulo");
        Objects.requireNonNull(estado, "El estado no puede ser nulo");

        // Validaciones de contenido
        if (titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (autor.isBlank()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        if (anio < 1400 || anio > Year.now().getValue()) {
            throw new IllegalArgumentException("El año no es válido");
        }
    }

    // Constructor simplificado (sin ID ni estado)
    public Libro(String titulo, String autor, int anio) {
        this(UUID.randomUUID(), titulo, autor, anio, EstadoLibro.DISPONIBLE);
    }

    // Métodos de transición de estado (inmutables)
    public Libro prestar() {
        if (estado == EstadoLibro.PRESTADO) {
            throw new IllegalStateException("El libro ya está prestado");
        }
        return withEstado(EstadoLibro.PRESTADO);
    }

    public Libro devolver() {
        if (estado == EstadoLibro.DISPONIBLE) {
            throw new IllegalStateException("El libro ya está disponible");
        }
        return withEstado(EstadoLibro.DISPONIBLE);
    }

    public Libro reservar() {
        if (estado != EstadoLibro.DISPONIBLE) {
            throw new IllegalStateException("Solo se pueden reservar libros disponibles");
        }
        return withEstado(EstadoLibro.RESERVADO);
    }

    private Libro withEstado(EstadoLibro nuevoEstado) {
        return new Libro(id, titulo, autor, anio, nuevoEstado);
    }

    public String descripcionCorta() {
        return String.format("%s (%d) - %s", titulo, anio, switch (estado) {
            case DISPONIBLE -> "✅ Disponible";
            case PRESTADO   -> "📕 Prestado";
            case RESERVADO  -> "📌 Reservado";
        });
    }

    @Override
    public String toString() {
        return descripcionCorta();
    }
}

enum EstadoLibro {
    DISPONIBLE,
    PRESTADO,
    RESERVADO
}
