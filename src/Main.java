public class Main {
    public static void main(String[] args) {
        IBibliotecaService biblioteca = new Biblioteca();
        ConsolaBiblioteca app = new ConsolaBiblioteca(biblioteca);
        app.iniciar();
    }
}