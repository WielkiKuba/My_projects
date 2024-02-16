import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        int port = 55555;
        ArrayList<String> clients = new ArrayList<>();
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept();

                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                byte[] buffer = new byte[6144];
                int bytesRead = inputStream.read(buffer);

                String read = new String(buffer, 0, bytesRead);
                String[] message = read.split("#");
                String clientIP = socket.getInetAddress().getHostAddress();
                boolean isFree = true;
                if(message[0].equals("BIO")){
                    for(String nickname:clients){
                        String[] nickname2 = nickname.split("#");
                        if(nickname2[0].equals(message[1])){
                            isFree = false;
                        }
                    }
                    String word;
                    if(isFree){
                        clients.add(message[1]+"#"+clientIP);
                         word = "true";
                    }else {
                        word = "false";
                    }
                    outputStream.write(word.getBytes());
                }
                if(message[0].equals("getList")){
                    for(String word:clients){
                        outputStream.write(word.getBytes());
                        outputStream.flush();
                    }
                }
                outputStream.close();
                socket.close();
            }
        } catch (IOException e) {
        e.printStackTrace();
        }
    }
}
