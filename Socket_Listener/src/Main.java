import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        String name =" ";
        String server = "jakubdomain.ddns.net";
        try{
            ServerSocket serverSocket = new ServerSocket(8000);
            System.out.println("=========================");
            System.out.println("You can here read messages sent to you");
            System.out.println("=========================\n");
            while(true){
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                byte[] buffer = new byte[2048];
                int bytesRead = inputStream.read(buffer);
                String read = new String(buffer, 0, bytesRead);
                String clientIP = socket.getInetAddress().getHostAddress();
                if(clientIP.equals(server)||clientIP.equals("127.0.0.1")){
                    String[] readParts = read.split("#");
                    if(readParts[0].equals("NAME")){
                        name = readParts[1];
                        System.out.println("<You are logged on "+name);
                    }
                    else if(readParts[0].equals("AYA")){
                        if(readParts[1].equals(name)){
                            send(socket,"AYA#"+name,"y");
                        }else {
                            send(socket,"AYA#"+readParts[1]+"#"+name,"y");
                        }
                    }
                    else if(readParts[0].equals("SHUTDOWN")){
                        break;
                    }
                }else {
                    if(!name.equals(" ")){
                        System.out.println("["+read+" FROM "+clientIP+"]");
                    }
                }
            }
        }catch (IOException e){
            System.out.println("ERROR 301");
        }
    }
    public static void send(Socket socket,String what,String close){
        try{
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(what.getBytes());
            outputStream.flush();
            if(close.equals("y")){
                outputStream.close();
            }
        }catch (IOException e){
            System.out.println("ERROR 311");
        }
    }
}