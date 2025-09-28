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
        String query = texto.toLowerCase(Locale.ROOT);
        return libros.stream()
                .filter(l -> l.titulo().toLowerCase(Locale.ROOT).contains(query)
                        || l.autor().toLowerCase(Locale.ROOT).contains(query))
                .collect(Collectors.toList());
    }

    @Override
    public boolean prestar(UUID id) {
        return cambiarEstado(id, EstadoLibro.DISPONIBLE, EstadoLibro.PRESTADO);
    }

    @Override
    public boolean devolver(UUID id) {
        return cambiarEstado(id, EstadoLibro.PRESTADO, EstadoLibro.DISPONIBLE);
    }

    @Override
    public boolean reservar(UUID id) {
        return cambiarEstado(id, EstadoLibro.DISPONIBLE, EstadoLibro.RESERVADO);
    }

    @Override
    public List<Libro> getLibros() {
        return List.copyOf(libros); // inmutable
    }
    
    private boolean cambiarEstado(UUID id, EstadoLibro estadoActual, EstadoLibro nuevoEstado) {
        Optional<Libro> libroOpt = libros.stream()
                .filter(l -> l.id().equals(id) && l.estado() == estadoActual)
                .findFirst();

        if (libroOpt.isPresent()) {
            Libro libro = libroOpt.get();
            libros.set(libros.indexOf(libro),
                    new Libro(libro.id(), libro.titulo(), libro.autor(), libro.anio(), nuevoEstado));
            return true;
        }
        return false;
    }
}
