import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;

public class FuncionServicios {
    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/grade", exchange -> {
            String num = exchange.getRequestURI().getQuery(); // ?score=85
            int score = Integer.parseInt(num.split("=")[1]);

            // Pattern Matching con primitivos (Java 25)
            String grade = switch (score) {
                case int s when s >= 90 -> "A";
                case int s when s >= 75 -> "B";
                case int s when s >= 60 -> "C";
                default -> "D/F";
            };

            String response = "Score: " + score + " => Grade: " + grade;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (var os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });

        server.start();
        System.out.println("✅ Service running on http://localhost:8080/grade?score=85");
    }
}
