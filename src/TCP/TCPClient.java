package TCP;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Random;

public class TCPClient extends Thread{
    private String serverName;
    private int port;

    public TCPClient(String serverName, int port) {
        this.serverName = serverName;
        this.port = port;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            Random random = new Random();
            socket = new Socket(InetAddress.getByName(serverName), port);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedWriter.write("login\n");
            bufferedWriter.write("First message: " + random.nextInt(4) + "\n");
            bufferedWriter.write("Second message: " + random.nextInt(4) + "\n");
            bufferedWriter.write("logout\n");
            bufferedWriter.flush();
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                System.out.println("From server " + line);
            }
            bufferedWriter.flush();
            System.out.println("Done!");
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String serverName = System.getenv("SERVER_NAME");
        int port = Integer.parseInt(System.getenv("SERVER_PORT"));
        for (int i = 0; i < 5; i++) {
            TCPClient client = new TCPClient(serverName, port);
            client.start();
        }
    }
}
