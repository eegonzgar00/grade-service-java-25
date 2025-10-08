package com.learn.biblioteca.service;

import com.learn.biblioteca.model.Libro;

import java.util.List;
import java.util.UUID;

public interface IBibliotecaService {
    void registrarLibro(String titulo, String autor, int anio);
    List<Libro> getLibros();
    List<Libro> buscar(String texto);
    boolean prestar(UUID id);
    boolean devolver(UUID id);
    boolean reservar(UUID id);
}
