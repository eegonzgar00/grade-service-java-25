package com.learn.biblioteca.model;

import java.util.UUID;

public record Libro(UUID id, String titulo, String autor, int anio, EstadoLibro estado) {

    public Libro(String titulo, String autor, int anio) {
        this(UUID.randomUUID(), titulo, autor, anio, new Disponible());
    }

    public Libro prestar() {
        return new Libro(id, titulo, autor, anio, new Prestado());
    }

    public Libro devolver() {
        return new Libro(id, titulo, autor, anio, new Disponible());
    }

    public Libro reservar() {
        return new Libro(id, titulo, autor, anio, new Reservado());
    }

    public String descripcionCorta() {
        return "%s (%d) - %s".formatted(titulo, anio, estado.nombre());
    }
}
