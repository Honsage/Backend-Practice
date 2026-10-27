package capitalize;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class CapitalizeServer {

    private static final int SERVER_PORT = 59898;

    public static void main(String[] args) {
        try (var listener = new ServerSocket(SERVER_PORT)) {
            System.out.println("Server is running");
            var pool = Executors.newFixedThreadPool(20);

            while (true) {
                pool.execute(new Capitalizer(listener.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class Capitalizer implements Runnable {

        private final Socket socket;

        Capitalizer(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("Connection: " + socket);
            try {
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);
                while(in.hasNextLine()) {
                    out.println(in.nextLine().toUpperCase());
                }
            } catch (IOException e) {
                System.out.println("Error: " + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("Closed: " + socket);
            }
        }
    }
}
