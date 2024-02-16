import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Server {
    String server = "localhost";
//    String server = "192.168.1.4";
    int port = 55555;

    public static Socket connection(String ip, int destinationPort) throws IOException {
        try {
//            String host = "jakubdomain.ddns.net";
            String host = ip;
            int port = destinationPort;
            Socket socket = new Socket(host, port);
            return socket;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List get(String what) {
        String comand = what;
        String read = "";
        List<String> readList = new ArrayList<>();
        try {
            Socket socket = connection(server, port);
//            System.out.println(socket);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(comand.getBytes());
            outputStream.flush();
            readList = read(socket);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return readList;
    }

    public boolean bio(String description) {
        boolean isFree1 = false;
        try {
            Socket socket = connection(server, port);
            OutputStream outputStream = socket.getOutputStream();
            String message = "BIO#" + description;
            outputStream.write(message.getBytes());
            List<String> isFree = read(socket);
            if(!isFree.isEmpty()){
                if(isFree.get(0).equals("true")){
                    isFree1 = true;
                }
            }
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isFree1;
    }

    public List read(Socket connection) {
        String message = "";
        Socket socket = null;
        List<String> messageList = new ArrayList<>();
        try {
//            System.out.println(connection);
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer))!=-1){
                message = new String(buffer, 0, bytesRead);
                messageList.add(message);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return messageList;
    }
    public void listener(boolean isPrint){
        AtomicReference<String> content = new AtomicReference<>("");
        Thread thread = new Thread(()->{
            try{
                ServerSocket serverSocket = new ServerSocket(44444);
                while (true){
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    byte[] buffer = new byte[2048];
                    int bytesRead = inputStream.read(buffer);
                    String read = new String(buffer, 0, bytesRead);
                    String clientIP = socket.getInetAddress().getHostAddress();
                    content.set(content + ("<" + read + " FROM " + clientIP + ">\n"));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(()->{
            if(isPrint){
                System.out.println(content);
            }
        });
        thread.start();
        thread2.start();
    }
}
