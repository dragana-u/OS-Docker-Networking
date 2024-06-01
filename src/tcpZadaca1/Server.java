package tcpZadaca1;

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
        System.out.println("Server is starting ...");
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Server has started and is waiting for connections!");
        while (true){
            Socket socket = null;
            try {
                socket = serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("New client connected");
            new Worker(socket).start();
        }
    }

    public static void main(String[] args) {
        Server server = new Server(9753);
        server.start();
    }
}
