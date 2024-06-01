package tcpZadaca1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends Thread {
    int serverPort;
    String serverName;

    public Client(String serverName, int serverPort) {
        this.serverPort = serverPort;
        this.serverName = serverName;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getByName(this.serverName),this.serverPort);
            BufferedReader read_socket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader read_input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter write_socket = new PrintWriter(socket.getOutputStream(), true);
            write_socket.write(read_input.readLine() + "\n");
            write_socket.flush();
            String validate_login = read_socket.readLine();
            System.out.println(validate_login);
            if (validate_login.contains("successful")){
                while (true){
                    String client_mess = read_input.readLine();
                    write_socket.write(client_mess + "\n");
                    write_socket.flush();
                    String server_response = read_socket.readLine();
                    System.out.println(server_response);
                    if (server_response.contains("bye")){
                        System.out.println("Logged out");
                        break;
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Client client = new Client("localhost",9753);
        client.start();
    }
}
