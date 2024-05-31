package tcpAud;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
    private int port;
    public Server(int port){
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Starting the server...");
        ServerSocket serverSocket = null;
        try {
           serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server started");
        while(true){
            try {
                Socket client = serverSocket.accept();
                System.out.println("client connected");
                System.out.println(client.getInetAddress() + " " + client.getPort());
                new Worker(client).start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        Server server = new Server(5000);
        server.start();
    }
}
