package capitalize;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class CapitalizeClient {

    private static final int SERVER_PORT = 59898;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please pass the server ipv4 as the program's only argument!");
            return;
        }

        try (var socket = new Socket(args[0], SERVER_PORT)) {
            System.out.println("Enter text that will be capitalized:");
            var scanner = new Scanner(System.in);
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream(), true);

            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                System.out.println(in.nextLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
