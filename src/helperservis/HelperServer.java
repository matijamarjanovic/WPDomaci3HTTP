package helperservis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HelperServer {

    public static final int HELPER_PORT = 8081;

    public static void main(String[] args) throws IOException {

        ServerSocket ss = new ServerSocket(HELPER_PORT);

        while (true) {
            Socket s = ss.accept();
            new Thread(new HelperThread(s)).start();
        }

    }
}
