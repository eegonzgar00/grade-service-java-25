package com.learn.biblioteca.model;

public record Prestado() implements EstadoLibro {
    @Override public String nombre() { return "📕 Prestado"; }
}
