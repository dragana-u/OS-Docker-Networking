package udpaud;

import java.io.IOException;
import java.net.*;

public class UDPClientAud extends Thread {
    private String serverName;
    private int serverPort;

    private DatagramSocket socket;
    private InetAddress address;
    private String message;
    private byte[] buffer;

    public UDPClientAud(String serverName, int serverPort, String message) {
        this.serverName = serverName;
        this.serverPort = serverPort;
        this.message = message;
        try {
            this.socket = new DatagramSocket();
            this.address = InetAddress.getByName(serverName);
        } catch (SocketException | UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, serverPort);
        try {
            socket.send(packet);
            packet = new DatagramPacket(buffer, buffer.length, address, serverPort);
            System.out.println(new String(packet.getData(), 0, packet.getLength()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        UDPClientAud client = new UDPClientAud("localhost", 5554, "Hi :)");
        client.start();
    }
}
