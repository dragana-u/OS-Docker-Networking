

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class UDPClient extends Thread{
    private String serverName;
    private int serverPort;
    private String message;

    private DatagramSocket socket;
    private byte[] buffer;
    private List<String> predefinedMessages;

    public UDPClient(String serverName, int serverPort, String message) throws SocketException {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.message = message;
        buffer = message.getBytes();
        this.socket = new DatagramSocket();
        predefinedMessages = new ArrayList<String>();
        this.predefinedMessages.add("message1");
        this.predefinedMessages.add("message2");
        this.predefinedMessages.add("message3");
        this.predefinedMessages.add("logged out");
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(serverName), serverPort);
            socket.send(packet);
            DatagramPacket receivePacket = new DatagramPacket(new byte[256], 256);
            socket.receive(receivePacket);
            String server_message = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Client received: " + server_message);
            for (String messageToServer : predefinedMessages) {
                packet = new DatagramPacket(messageToServer.getBytes(), messageToServer.getBytes().length, InetAddress.getByName(serverName), serverPort);
                socket.send(packet);
                socket.receive(receivePacket);
                server_message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("Server message: " + server_message);
                if (server_message.equals("logged out")) {
                    break;
                }
                System.out.println("Client logged out..");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SocketException {
        String serverName = System.getenv("SERVER_NAME");
        int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));
        UDPClient client = new UDPClient(serverName, serverPort, "login");
        client.start();
    }
}

