import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        IBibliotecaService biblioteca = new Biblioteca();
        SwingUtilities.invokeLater(() -> {
            new BibliotecaFrame(biblioteca).setVisible(true);
        });
    }
}
