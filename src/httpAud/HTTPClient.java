package httpAud;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTTPClient {
    public static void main(String[] args) {
        String serverAddress = "localhost";
        int serverPort = 8080;
        String name = "D";
        String surname = "U";
        try {
            String url = "http://" + serverAddress + ":" + serverPort + "/?name=" + name + "&surname=" + surname;
            URL serverUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) serverUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}