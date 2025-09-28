import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.UUID;

public class BibliotecaFrame extends JFrame {
    private final IBibliotecaService biblioteca;
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private final JList<String> listaLibros = new JList<>(modeloLista);

    public BibliotecaFrame(IBibliotecaService biblioteca) {
        this.biblioteca = biblioteca;
        setTitle("📚 Biblioteca Digital");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Botones
        JButton btnRegistrar = new JButton("Registrar libro");
        JButton btnBuscar = new JButton("Buscar libro");
        JButton btnPrestar = new JButton("Prestar libro");
        JButton btnDevolver = new JButton("Devolver libro");

        // Panel de botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);

        add(new JScrollPane(listaLibros), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnRegistrar.addActionListener(e -> registrarLibro());
        btnBuscar.addActionListener(e -> buscarLibros());
        btnPrestar.addActionListener(e -> prestarLibro());
        btnDevolver.addActionListener(e -> devolverLibro());

        listarLibros(); // cargar al inicio
    }

    private void registrarLibro() {
        String titulo = JOptionPane.showInputDialog(this, "Título del libro:");
        if (titulo == null || titulo.isBlank()) return;

        String autor = JOptionPane.showInputDialog(this, "Autor:");
        if (autor == null || autor.isBlank()) return;

        String anioStr = JOptionPane.showInputDialog(this, "Año de publicación:");
        if (anioStr == null || anioStr.isBlank()) return;

        try {
            int anio = Integer.parseInt(anioStr);
            biblioteca.registrarLibro(titulo, autor, anio);
            listarLibros();
            JOptionPane.showMessageDialog(this, "📚 Libro registrado.");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Año inválido.");
        }
    }

    private void buscarLibros() {
        String texto = JOptionPane.showInputDialog(this, "Texto a buscar:");
        if (texto == null || texto.isBlank()) return;

        List<Libro> resultados = biblioteca.buscar(texto);
        modeloLista.clear();

        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No se encontraron libros.");
        } else {
            for (Libro l : resultados) {
                modeloLista.addElement(formatoLibro(l));
            }
        }
    }

    private void prestarLibro() {
        int indice = listaLibros.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un libro de la lista.");
            return;
        }

        Libro seleccionado = biblioteca.getLibros().get(indice);
        UUID id = seleccionado.id();

        if (biblioteca.prestar(id)) {
            JOptionPane.showMessageDialog(this, "📕 Libro prestado.");
            listarLibros();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo prestar.");
        }
    }

    private void devolverLibro() {
        int indice = listaLibros.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Selecciona un libro de la lista.");
            return;
        }

        Libro seleccionado = biblioteca.getLibros().get(indice);
        UUID id = seleccionado.id();

        if (biblioteca.devolver(id)) {
            JOptionPane.showMessageDialog(this, "📖 Libro devuelto.");
            listarLibros();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo devolver.");
        }
    }

    private void listarLibros() {
        modeloLista.clear();
        for (Libro l : biblioteca.getLibros()) {
            modeloLista.addElement(formatoLibro(l));
        }
    }

    private String formatoLibro(Libro l) {
        return "%s (%s, %d) [%s]".formatted(l.titulo(), l.autor(), l.anio(), l.estado());
    }

    public static void main(String[] args) {
        IBibliotecaService biblioteca = new BibliotecaServiceImpl();
        SwingUtilities.invokeLater(() -> new BibliotecaFrame(biblioteca).setVisible(true));
    }
}
