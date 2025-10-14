package com.learn.biblioteca.model;

public record Disponible() implements EstadoLibro {
    @Override public String nombre() { return "✅ Disponible"; }
}