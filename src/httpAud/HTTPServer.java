package httpAud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class WorkingThread extends Thread {
    private final Socket clientSocket;

    public WorkingThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String request = in.readLine();
            Map<String, String> result = parseUrl(request);
            String name = result.getOrDefault("name", "default");
            String surname = result.getOrDefault("surname","default");

            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println();
            out.println("<html><head><title>Hello</title></head><body>");
            out.println("<h1>Hello, " + name + " " + surname + "!</h1>");
            out.println("</body></html>");
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Method that parses the request url into a map of all its query parameters
     *
     * @param url is the url of the request
     */
    private Map<String, String> parseUrl(String url) {
        Map<String, String> map = new HashMap<>();
        if (!url.contains("?")) return map;

        String[] parts = url.split("\\s+");
        String query = parts[1].split("\\?")[1];
        String[] params = query.split("&");

        for (String param : params) {
            String[] keyValue = param.split("=");
            map.put(keyValue[0], keyValue[1]);
        }
        return map;
    }
}


public class HTTPServer {
    public static void main(String[] args) {
        int port = 8080;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server on port: " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                WorkingThread workingThread = new WorkingThread(clientSocket);
                workingThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
