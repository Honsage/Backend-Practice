package hello;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

    private static final int SERVER_PORT = 50001;

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(SERVER_PORT);

            while (true) {
                Socket clientConn = server.accept();

                DataOutputStream serverOutput = new DataOutputStream(clientConn.getOutputStream());
                serverOutput.writeBytes("Hello from Java Server!");
                clientConn.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
