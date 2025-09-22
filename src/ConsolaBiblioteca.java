import java.util.List;
import java.util.Scanner;

public class ConsolaBiblioteca {
    private final Biblioteca biblioteca = new Biblioteca();
    private final Scanner sc = new Scanner(System.in);

    public void iniciar() {
        while (true) {
            System.out.println("\n=== BIBLIOTECA DIGITAL ===");
            System.out.println("1. Registrar libro");
            System.out.println("2. Buscar libros");
            System.out.println("3. Prestar libro");
            System.out.println("4. Devolver libro");
            System.out.println("5. Listar libros");
            System.out.println("6. Salir");
            System.out.print("Elige una opción: ");

            int opcion = leerEntero();

            switch (opcion) {
                case 1 -> registrarLibro();
                case 2 -> buscarLibros();
                case 3 -> prestarLibro();
                case 4 -> devolverLibro();
                case 5 -> listarLibros();
                case 6 -> { System.out.println("👋 Saliendo..."); return; }
                default -> System.out.println("❌ Opción inválida.");
            }
        }
    }

    private void registrarLibro() {
        System.out.print("Título: ");
        String titulo = sc.nextLine();
        System.out.print("Autor: ");
        String autor = sc.nextLine();
        System.out.print("Año: ");
        int anio = leerEntero();

        biblioteca.registrarLibro(titulo, autor, anio);
        System.out.println("📚 Libro registrado.");
    }

    private void buscarLibros() {
        System.out.print("Buscar por texto: ");
        String texto = sc.nextLine();

        List<Libro> resultados = biblioteca.buscar(texto);

        if (resultados.isEmpty()) {
            System.out.println("⚠️ No se encontraron libros.");
        } else {
            mostrarLista(resultados);
        }
    }

    private void prestarLibro() {
        listarLibros();
        System.out.print("Número del libro a prestar: ");
        int indice = leerEntero() - 1;

        if (biblioteca.prestar(indice)) {
            System.out.println("📕 Libro prestado.");
        } else {
            System.out.println("❌ No se pudo prestar (índice inválido o ya prestado).");
        }
    }

    private void devolverLibro() {
        listarLibros();
        System.out.print("Número del libro a devolver: ");
        int indice = leerEntero() - 1;

        if (biblioteca.devolver(indice)) {
            System.out.println("📖 Libro devuelto.");
        } else {
            System.out.println("❌ No se pudo devolver (índice inválido o ya disponible).");
        }
    }

    private void listarLibros() {
        List<Libro> libros = biblioteca.getLibros();

        if (libros.isEmpty()) {
            System.out.println("⚠️ No hay libros en la biblioteca.");
        } else {
            mostrarLista(libros);
        }
    }

    private void mostrarLista(List<Libro> libros) {
        for (int i = 0; i < libros.size(); i++) {
            var l = libros.get(i);
            System.out.printf("%d. %s (%s, %d) [%s]\n",
                    i + 1, l.titulo(), l.autor(), l.anio(),
                    l.disponible() ? "Disponible ✅" : "Prestado ❌");
        }
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Ingresa un número válido: ");
            }
        }
    }
}
