package tcpZadaca1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Worker extends Thread{
    private Socket socket;
    public Worker(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String line = in.readLine();
            if (line.contains("login")){
                out.write("Login successful!\n");
                out.flush();
                while (true){
                    line = in.readLine();
                    out.write(String.format("echo - %s\n", line));
                    out.flush();
                    if (line.contains("bye")){
                        out.write("Logout successful!");
                        out.flush();
                        break;
                    }
                }
            }else{
                out.write("Login failed!\n");
                out.flush();
            }
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
