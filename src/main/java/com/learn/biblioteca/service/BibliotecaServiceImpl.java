package com.learn.biblioteca.service;

import com.learn.biblioteca.model.EstadoLibro;
import com.learn.biblioteca.model.Libro;

import java.util.*;
import java.util.stream.Collectors;

public class BibliotecaServiceImpl implements IBibliotecaService {

    private final List<Libro> libros = new ArrayList<>();

    @Override
    public void registrarLibro(String titulo, String autor, int anio) {
        libros.add(new Libro(titulo, autor, anio));
    }

    @Override
    public List<Libro> buscar(String texto) {
        var query = texto.toLowerCase();
        return libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(query)
                        || l.autor().toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    @Override
    public boolean prestar(UUID id) {
        return actualizarEstado(id, EstadoLibro.PRESTADO);
    }

    @Override
    public boolean devolver(UUID id) {
        return actualizarEstado(id, EstadoLibro.DISPONIBLE);
    }

    @Override
    public boolean reservar(UUID id) {
        return actualizarEstado(id, EstadoLibro.RESERVADO);
    }

    private boolean actualizarEstado(UUID id, EstadoLibro nuevoEstado) {
        Optional<Libro> encontrado = libros.stream()
                .filter(l -> l.id().equals(id))
                .findFirst();

        if (encontrado.isEmpty()) return false;

        Libro l = encontrado.get();
        libros.remove(l);
        libros.add(new Libro(l.id(), l.titulo(), l.autor(), l.anio(), nuevoEstado));
        return true;
    }

    @Override
    public List<Libro> getLibros() {
        return List.copyOf(libros);
    }
}
