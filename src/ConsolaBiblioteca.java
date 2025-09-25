import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BibliotecaFrame extends JFrame {
    private final IBibliotecaService biblioteca;
    private final DefaultListModel<String> modeloLibros;

    public BibliotecaFrame(IBibliotecaService biblioteca) {
        this.biblioteca = biblioteca;
        this.modeloLibros = new DefaultListModel<>();

        setTitle("Biblioteca Digital");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel de botones
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

        // Lista de libros
        JList<String> listaLibros = new JList<>(modeloLibros);
        JScrollPane scroll = new JScrollPane(listaLibros);

        // Añadir componentes
        setLayout(new BorderLayout());
        add(panelBotones, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // Eventos
        btnRegistrar.addActionListener(e -> registrarLibro());
        btnListar.addActionListener(e -> listarLibros());
    }

    private void registrarLibro() {
        String titulo = JOptionPane.showInputDialog(this, "Título:");
        String autor = JOptionPane.showInputDialog(this, "Autor:");
        int anio = Integer.parseInt(JOptionPane.showInputDialog(this, "Año:"));
        biblioteca.registrarLibro(titulo, autor, anio);
        listarLibros();
    }

    private void listarLibros() {
        modeloLibros.clear();
        List<Libro> libros = biblioteca.getLibros();
        for (Libro l : libros) {
            modeloLibros.addElement(l.titulo() + " - " + l.autor() + " (" + l.anio() + ") [" + l.estado() + "]");
        }
    }
}
