package com.learn.biblioteca.model;

public sealed interface EstadoLibro permits Disponible, Prestado, Reservado {
    String nombre();
}

