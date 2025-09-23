import java.util.*;
import java.util.stream.Collectors;

public class Biblioteca  implements IBibliotecaService{
    private final List<Libro> libros = new ArrayList<>();

    @Override
    public void registrarLibro(String titulo, String autor, int anio) {
        libros.add(new Libro(titulo, autor, anio, EstadoLibro.DISPONIBLE));
    }

    @Override
    public List<Libro> buscar(String texto) {
        return libros.stream()
                .filter(l -> l.titulo().toLowerCase().contains(texto.toLowerCase())
                        || l.autor().toLowerCase().contains(texto.toLowerCase()))
                .toList();
    }

    @Override
    public boolean prestar(int indice) {
        if (indice >= 0 && indice < libros.size() && libros.get(indice).estado() == EstadoLibro.DISPONIBLE) {
            libros.set(indice, libros.get(indice).prestar());
            return true;
        }
        return false;
    }

    @Override
    public boolean devolver(int indice) {
        if (indice >= 0 && indice < libros.size() && libros.get(indice).estado() == EstadoLibro.PRESTADO) {
            libros.set(indice, libros.get(indice).devolver());
            return true;
        }
        return false;
    }

    @Override
    public List<Libro> getLibros() {
        return libros;
    }
}
