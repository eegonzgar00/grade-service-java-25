package com.learn.biblioteca.service;

import com.learn.biblioteca.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class BibliotecaServiceImpl implements IBibliotecaService {

    private static final Logger log = LoggerFactory.getLogger(BibliotecaServiceImpl.class);
    private final List<Libro> libros = new ArrayList<>();

    @Override
    public void registrarLibro(String titulo, String autor, int anio) {
        try {
            Libro nuevo = new Libro(titulo, autor, anio);
            libros.add(nuevo);
            log.info("📚 Libro registrado: {}", nuevo.descripcionCorta());
        } catch (IllegalArgumentException e) {
            log.warn("❌ Error al registrar libro: {}", e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Libro> getLibros() {
        log.debug("Obteniendo lista de libros ({} libros)", libros.size());
        return List.copyOf(libros);
    }

    @Override
    public boolean prestar(UUID id) {
        Optional<Libro> libro = findLibro(id);
        if (libro.isEmpty()) {
            log.warn("Intento de prestar un libro inexistente ({})", id);
            return false;
        }

        try {
            Libro actualizado = libro.get().prestar();
            reemplazarLibro(actualizado);
            log.info("📕 Libro prestado: {}", actualizado.descripcionCorta());
            return true;
        } catch (IllegalStateException e) {
            log.warn("❌ No se pudo prestar libro {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean devolver(UUID id) {
        Optional<Libro> libro = findLibro(id);
        if (libro.isEmpty()) return false;

        try {
            Libro actualizado = libro.get().devolver();
            reemplazarLibro(actualizado);
            log.info("📖 Libro devuelto: {}", actualizado.descripcionCorta());
            return true;
        } catch (IllegalStateException e) {
            log.warn("⚠️ Error al devolver libro {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    public boolean reservar(UUID id) {
        Optional<Libro> libro = findLibro(id);
        if (libro.isEmpty()) return false;

        try {
            Libro actualizado = libro.get().reservar();
            reemplazarLibro(actualizado);
            log.info("📌 Libro reservado: {}", actualizado.descripcionCorta());
            return true;
        } catch (IllegalStateException e) {
            log.warn("⚠️ No se pudo reservar libro {}: {}", id, e.getMessage());
            return false;
        }
    }

    @Override
    public List<Libro> buscar(String texto) {
        var resultados = libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(texto.toLowerCase())
                        || l.autor().toLowerCase().contains(texto.toLowerCase()))
                .toList();

        log.debug("Búsqueda '{}' devolvió {} resultados", texto, resultados.size());
        return resultados;
    }

    private Optional<Libro> findLibro(UUID id) {
        return libros.stream().filter(l -> l.id().equals(id)).findFirst();
    }

    private void reemplazarLibro(Libro nuevo) {
        for (int i = 0; i < libros.size(); i++) {
            if (libros.get(i).id().equals(nuevo.id())) {
                libros.set(i, nuevo);
                return;
            }
        }
    }
}
