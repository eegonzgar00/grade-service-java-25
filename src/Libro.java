import java.util.UUID;

public record Libro(UUID id, String titulo, String autor, int anio, EstadoLibro estado) {
    public Libro {
        if (titulo == null || titulo.isBlank())
            throw new IllegalArgumentException("El título no puede estar vacío");
        if (autor == null || autor.isBlank())
            throw new IllegalArgumentException("El autor no puede estar vacío");
        if (anio < 0 || anio > java.time.Year.now().getValue())
            throw new IllegalArgumentException("El año no es válido");
    }

    // Constructor más corto para cuando no pasamos ID ni estado
    public Libro(String titulo, String autor, int anio) {
        this(UUID.randomUUID(), titulo, autor, anio, EstadoLibro.DISPONIBLE);
    }

    // Métodos de dominio (inmutables → devuelven nuevo objeto)
    public Libro prestar() {
        if (estado == EstadoLibro.PRESTADO) {
            throw new IllegalStateException("El libro ya está prestado");
        }
        return new Libro(id, titulo, autor, anio, EstadoLibro.PRESTADO);
    }

    public Libro devolver() {
        if (estado == EstadoLibro.DISPONIBLE) {
            throw new IllegalStateException("El libro ya está disponible");
        }
        return new Libro(id, titulo, autor, anio, EstadoLibro.DISPONIBLE);
    }

    public Libro reservar() {
        if (estado == EstadoLibro.RESERVADO) {
            throw new IllegalStateException("El libro ya está reservado");
        }
        return new Libro(id, titulo, autor, anio, EstadoLibro.RESERVADO);
    }
}

enum EstadoLibro { DISPONIBLE, PRESTADO, RESERVADO }
