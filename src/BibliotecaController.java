package com.learn.biblioteca.controller;

import com.learn.biblioteca.model.Libro;
import com.learn.biblioteca.service.IBibliotecaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/libros")
public class BibliotecaController {

    private final IBibliotecaService service;

    public BibliotecaController(IBibliotecaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Libro> listar() {
        return service.getLibros();
    }

    @PostMapping
    public void registrar(@RequestBody Libro libro) {
        service.registrarLibro(libro.titulo(), libro.autor(), libro.anio());
    }

    @PostMapping("/{id}/prestar")
    public void prestar(@PathVariable UUID id) {
        service.prestar(id);
    }

    @PostMapping("/{id}/devolver")
    public void devolver(@PathVariable UUID id) {
        service.devolver(id);
    }
}
