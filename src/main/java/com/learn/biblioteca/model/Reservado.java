package com.learn.biblioteca.model;

public record Reservado() implements EstadoLibro {
    @Override public String nombre() { return "📌 Reservado"; }
}