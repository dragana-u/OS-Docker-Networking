package tcpAud;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Worker extends Thread{
    private Socket socket;
    public Worker(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String[] line = bufferedReader.readLine().split(" ");
            Request request = new Request(line);
            System.out.println(request.method + " " + request.resource);
            String readLine = bufferedReader.readLine();
            while(readLine!=null) {
                String[] parts = readLine.split(": ", 2);
                request.headers.put(parts[0],parts[1]);
                readLine = bufferedReader.readLine();

            }
            // POST
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("HTTP 200 OK\n");
            writer.write("You request to make " + request.method + " " + request.resource);
            writer.write("Hello user: " + request.headers.get("User") + "\n");

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static class Request{
        String method; // GET Post
        String resource;
        String httpVersion;
        Map<String, String> headers;
        public Request(String[] parts){
            method = parts[0];
            resource = parts[1];
            httpVersion = parts[2];

            headers = new HashMap<>();

        }
    }
}
