public record Libro(String titulo, String autor, int anio, boolean disponible) {
    public Libro prestar() { return new Libro(titulo, autor, anio, false); }
    public Libro devolver() { return new Libro(titulo, autor, anio, true); }
}