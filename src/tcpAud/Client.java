package tcpAud;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class Client extends Thread{
    private InetAddress serverAddress;
    private int serverPort;

    public Client(InetAddress address, int port) {
        this.serverAddress = address;
        this.serverPort = port;
    }

    @Override
    public void run() {
        Socket socket = null;
        Random random = new Random();
        try {
            socket = new Socket(serverAddress, serverPort);
            BufferedReader  bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            String method = random.nextInt(10) % 2 == 0 ? "GET" : "POST";
            String l = method + " /movies/ " + random.nextInt(100) +  " HTTP/1.1\n";
            bufferedWriter.write(l);
            bufferedWriter.write("User: FINKI\n");
            bufferedWriter.flush();
            String line = bufferedReader.readLine();
            while (line != null) {
                System.out.println("The server sent:" + line);
                line = bufferedReader.readLine();
            }
            System.out.println("Done");
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        Client clinet = new Client(InetAddress.getLocalHost(), 5000);
        clinet.start();
    }
}
