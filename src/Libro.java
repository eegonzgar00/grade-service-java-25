public record Libro(String titulo, String autor, int anio, EstadoLibro estado) {
    public Libro(String titulo, String autor, int anio) {
        this(titulo, autor, anio, EstadoLibro.DISPONIBLE);
    }

    public Libro prestar() {
        return new Libro(titulo, autor, anio, EstadoLibro.PRESTADO);
    }

    public Libro devolver() {
        return new Libro(titulo, autor, anio, EstadoLibro.DISPONIBLE);
    }
}

enum EstadoLibro { DISPONIBLE, PRESTADO, RESERVADO }
