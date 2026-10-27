package hello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SocketClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 50001;

    public static void main(String[] args) {
        try (Socket clientSocket = new Socket(SERVER_HOST, SERVER_PORT)) {
            InputStream is = clientSocket.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String receivedData = br.readLine();
            System.out.println("Data from server: [" + receivedData + "]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
