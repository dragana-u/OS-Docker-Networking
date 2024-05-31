

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer extends Thread{
    private DatagramSocket socket;
    private byte[] buffer;

    public UDPServer(int port) throws SocketException {
        this.socket = new DatagramSocket(port);
        buffer = new byte[256];
    }

    @Override
    public void run() {
        DatagramPacket recieved_packet = new DatagramPacket(buffer, buffer.length);
        while (true){
            try {
                socket.receive(recieved_packet);
                String client_message = new String(recieved_packet.getData(), 0, recieved_packet.getLength());
                DatagramPacket send_packet = getDatagramPacket(client_message, recieved_packet);
                socket.send(send_packet);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static DatagramPacket getDatagramPacket(String client_message, DatagramPacket recieved_packet) {
        String response;
        if (client_message.equals("login")){
            response = "logged in";
        }else if(client_message.equals("logout")){
            response = "logged out";
        }else{
            response = String.format("echo - %s", client_message);
        }
        return new DatagramPacket(response.getBytes(), response.getBytes().length, recieved_packet.getAddress(), recieved_packet.getPort());
    }

    public static void main(String[] args) throws SocketException {
        int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));
        UDPServer server = new UDPServer(4445);
        server.start();
    }
}
