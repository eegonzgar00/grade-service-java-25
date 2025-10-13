package com.learn.biblioteca.model;

sealed interface EstadoLibro permits Disponible, Prestado, Reservado {
    String nombre();
}

record Disponible() implements EstadoLibro {
    @Override public String nombre() { return "✅ Disponible"; }
}

record Prestado() implements EstadoLibro {
    @Override public String nombre() { return "📕 Prestado"; }
}

record Reservado() implements EstadoLibro {
    @Override public String nombre() { return "📌 Reservado"; }
}