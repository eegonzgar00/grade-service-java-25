import java.util.List;

public interface IBibliotecaService {

    void registrarLibro(String titulo, String autor, int anio);
    List<Libro> buscar(String texto);
    boolean prestar(int indice);
    boolean devolver(int indice);
    List<Libro> getLibros();
}
