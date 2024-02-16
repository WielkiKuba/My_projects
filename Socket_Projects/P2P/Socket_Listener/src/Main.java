import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket = new ServerSocket(55555);
            while (true){
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                byte[] buffer = new byte[2048];
                int bytesRead = inputStream.read(buffer);
                String read = new String(buffer, 0, bytesRead);
                String clientIP = socket.getInetAddress().getHostAddress();
                System.out.println("<"+read+" FROM "+clientIP+">");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}