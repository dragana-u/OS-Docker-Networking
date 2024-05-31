//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//public class TCPServer extends Thread{
//    private int port;
//    private static int messageCount = 0;
//    TCPServer(int port){
//        this.port = port;
//    }
//
//    @Override
//    public void run() {
//        System.out.println("Server starting...");
//        ServerSocket serverSocket = null;
//        try {
//            serverSocket = new ServerSocket(this.port);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("Server started and waiting for connection...");
//        while(true){
//            Socket clientSocket = null;
//            try {
//                clientSocket = serverSocket.accept();
//                System.out.println("New client connected...");
//                new ClientHandle(clientSocket).start();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//    public static synchronized void incrementClientCount() {
//        messageCount++;
//    }
//
//
//
//    public static void main(String[] args) throws InterruptedException {
//        int serverPort = Integer.parseInt(System.getenv("SERVER_PORT"));
//        prob server = new prob(serverPort);
//        server.start();
//        Thread.sleep(50000);
//        System.out.println("Total messages: " + messageCount);
//    }
//}
//
//class ClientHandle extends Thread{
//    private Socket socket;
//    ClientHandle(Socket socket){
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            String line = reader.readLine();
//            if (!line.contains("login")){
//                writer.write("Login not successful!\n");
//                writer.flush();
//                return;
//            }else{
//                writer.write("logged in\n");
//                writer.flush();
//            }
//            while ((line = reader.readLine()) != null){
//                prob.incrementClientCount();
//                if (line.contains("logout")){
//                    writer.write("logged out\n");
//                    writer.flush();
//                    break;
//                }else{
//                    writer.write("echo: " + line + "\n");
//                    writer.flush();
//                }
//            }
//            socket.close();
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//
//}