package udpaud;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServerAud extends Thread {
    private DatagramSocket socket;
    private byte[] buffer;

    public UDPServerAud(int port) {
        this.buffer = new byte[256];
        try {
            this.socket = new DatagramSocket(port);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        while (true) {
            try {
                socket.receive(packet);
                String recieved = new String(packet.getData(),0, packet.getLength());
                System.out.println("Recieved: " + recieved);
                packet = new DatagramPacket(buffer, buffer.length, packet.getAddress(),packet.getPort());
                socket.send(packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public static void main(String[] args) {
        UDPServerAud server = new UDPServerAud(5554);
        server.start();
    }
}
