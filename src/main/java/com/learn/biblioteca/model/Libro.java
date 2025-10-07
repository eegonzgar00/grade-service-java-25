import java.time.Year;
import java.util.Objects;
import java.util.UUID;

public record Libro(UUID id, String titulo, String autor, int anio, EstadoLibro estado) {
    public Libro {
        Objects.requireNonNull(titulo, "El título no puede ser nulo");
        Objects.requireNonNull(autor, "El autor no puede ser nulo");

        if (titulo.isBlank()) throw new IllegalArgumentException("El título no puede estar vacío");
        if (autor.isBlank()) throw new IllegalArgumentException("El autor no puede estar vacío");
        if (anio < 0 || anio > Year.now().getValue())
            throw new IllegalArgumentException("El año no es válido");
    }

    public Libro(String titulo, String autor, int anio) {
        this(UUID.randomUUID(), titulo, autor, anio, EstadoLibro.DISPONIBLE);
    }

    public Libro prestar() {
        return withEstado(EstadoLibro.PRESTADO);
    }

    public Libro devolver() {
        return withEstado(EstadoLibro.DISPONIBLE);
    }

    public Libro reservar() {
        return withEstado(EstadoLibro.RESERVADO);
    }

    private Libro withEstado(EstadoLibro nuevoEstado) {
        return new Libro(id, titulo, autor, anio, nuevoEstado);
    }

    public String descripcionCorta() {
        return String.format("%s (%d) - %s",
                titulo, anio,
                switch (estado) {
                    case DISPONIBLE -> "✅ Disponible";
                    case PRESTADO   -> "📕 Prestado";
                    case RESERVADO  -> "📌 Reservado";
                });
    }
}

enum EstadoLibro { DISPONIBLE, PRESTADO, RESERVADO }
