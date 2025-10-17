package com.learn.biblioteca.controller;

import com.learn.biblioteca.dto.LibroRequest;
import com.learn.biblioteca.model.Libro;
import com.learn.biblioteca.service.IBibliotecaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<Libro>> listar() {
        return ResponseEntity.ok(service.getLibros());
    }

    @PostMapping
    public ResponseEntity<String> registrar(@Valid @RequestBody LibroRequest request) {
        service.registrarLibro(request.titulo(), request.autor(), request.anio());
        return ResponseEntity.ok("📚 Libro registrado con éxito");
    }

    @PostMapping("/{id}/prestar")
    public ResponseEntity<String> prestar(@PathVariable UUID id) {
        return service.prestar(id)
                ? ResponseEntity.ok("📕 Libro prestado")
                : ResponseEntity.badRequest().body("No se pudo prestar el libro");
    }

    @PostMapping("/{id}/devolver")
    public ResponseEntity<String> devolver(@PathVariable UUID id) {
        return service.devolver(id)
                ? ResponseEntity.ok("📖 Libro devuelto")
                : ResponseEntity.badRequest().body("No se pudo devolver el libro");
    }
}
