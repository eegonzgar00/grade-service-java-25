import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BibliotecaFrame extends JFrame {
    private final IBibliotecaService biblioteca;
    private final DefaultListModel<String> modeloLibros;
    private final JList<String> listaLibros;

    public BibliotecaFrame(IBibliotecaService biblioteca) {
        this.biblioteca = biblioteca;
        this.modeloLibros = new DefaultListModel<>();
        this.listaLibros = new JList<>(modeloLibros);

        setTitle("Biblioteca Digital");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel botones
        JPanel panelBotones = new JPanel();
        JButton btnRegistrar = new JButton("Registrar libro");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnPrestar = new JButton("Prestar");
        JButton btnDevolver = new JButton("Devolver");
        JButton btnListar = new JButton("Listar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnPrestar);
        panelBotones.add(btnDevolver);
        panelBotones.add(btnListar);

        // Lista
        JScrollPane scroll = new JScrollPane(listaLibros);

        setLayout(new BorderLayout());
        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Eventos
        btnRegistrar.addActionListener(e -> registrarLibro());
        btnBuscar.addActionListener(e -> buscarLibro());
        btnPrestar.addActionListener(e -> prestarLibro());
        btnDevolver.addActionListener(e -> devolverLibro());
        btnListar.addActionListener(e -> listarLibros());
    }

    private void registrarLibro() {
        String titulo = JOptionPane.showInputDialog(this, "Título:");
        if (titulo == null || titulo.isBlank()) {
            JOptionPane.showMessageDialog(this, "⚠️ El título no puede estar vacío.");
            return;
        }

        String autor = JOptionPane.showInputDialog(this, "Autor:");
        if (autor == null || autor.isBlank()) {
            JOptionPane.showMessageDialog(this, "⚠️ El autor no puede estar vacío.");
            return;
        }

        String anioStr = JOptionPane.showInputDialog(this, "Año:");
        int anio;
        try {
            anio = Integer.parseInt(anioStr);
            if (anio < 0 || anio > java.time.Year.now().getValue()) {
                JOptionPane.showMessageDialog(this, "⚠️ Año inválido.");
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "⚠️ Debes ingresar un número para el año.");
            return;
        }

        biblioteca.registrarLibro(titulo, autor, anio);
        JOptionPane.showMessageDialog(this, "📚 Libro registrado con éxito.");
        listarLibros();
    }

    private void buscarLibro() {
        String texto = JOptionPane.showInputDialog(this, "Buscar:");
        List<Libro> resultados = biblioteca.buscar(texto);
        modeloLibros.clear();
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ No se encontraron libros.");
        } else {
            for (Libro l : resultados) {
                modeloLibros.addElement(l.titulo() + " - " + l.autor() + " (" + l.anio() + ") [" + l.estado() + "]");
            }
        }
    }

    private void prestarLibro() {
        int indice = listaLibros.getSelectedIndex();
        if (indice != -1 && biblioteca.prestar(indice)) {
            JOptionPane.showMessageDialog(this, "📕 Libro prestado.");
            listarLibros();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo prestar.");
        }
    }

    private void devolverLibro() {
        int indice = listaLibros.getSelectedIndex();
        if (indice != -1 && biblioteca.devolver(indice)) {
            JOptionPane.showMessageDialog(this, "📖 Libro devuelto.");
            listarLibros();
        } else {
            JOptionPane.showMessageDialog(this, "❌ No se pudo devolver.");
        }
    }

    private void listarLibros() {
        modeloLibros.clear();
        List<Libro> libros = biblioteca.getLibros();
        for (Libro l : libros) {
            modeloLibros.addElement(l.titulo() + " - " + l.autor() + " (" + l.anio() + ") [" + l.estado() + "]");
        }
    }
}
