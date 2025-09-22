import java.util.*;
import java.util.stream.Collectors;

public class Biblioteca {
    private final List<Libro> libros = new ArrayList<>();

    public void registrarLibro(String titulo, String autor, int anio) {
        libros.add(new Libro(titulo, autor, anio, true));
    }

    public List<Libro> buscar(String texto) {
        return libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(texto.toLowerCase())
                        || l.autor().toLowerCase().contains(texto.toLowerCase()))
                .toList();
    }

    public boolean prestar(int indice) {
        if (indice >= 0 && indice < libros.size() && libros.get(indice).disponible()) {
            libros.set(indice, libros.get(indice).prestar());
            return true;
        }
        return false;
    }

    public boolean devolver(int indice) {
        if (indice >= 0 && indice < libros.size() && !libros.get(indice).disponible()) {
            libros.set(indice, libros.get(indice).devolver());
            return true;
        }
        return false;
    }

    public List<Libro> getLibros() { return libros; }
}
