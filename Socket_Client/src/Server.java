import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    String server = "192.168.1.4";
    int port = 8080;

    public static Socket connection(String ip, int destinationPort) throws IOException {
        try {
            String host = ip;
            int port = destinationPort;
            Socket socket = new Socket(host, port);
            return socket;
        } catch (UnknownHostException e) {
            System.out.println("ERROR 201");
            e.printStackTrace();
            throw e;
        }
    }
    public String myIp(){
        String ip = " ";
        try{
            Socket socket1 = connection(server,port);
            ip = socket1.getLocalAddress().getHostAddress();
            socket1.close();
        }catch (IOException e){
            System.out.println("ERROR 207");
        }
        return ip;
    }

    public List get(String what) {
        List<String> readList = new ArrayList<>();
        try {
            Socket socket = connection(server, port);
            send(socket,what,"n");
            readList = read(socket);
        } catch (IOException e) {
            System.out.println("ERROR 202");
            e.printStackTrace();
        }
        return readList;
    }
    public void send(Socket socket,String what,String close){
        try{
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(what.getBytes());
            outputStream.flush();
            if(close.equals("y")){
                outputStream.close();
            }
        }catch (IOException e){
            System.out.println("ERROR 203");
            e.printStackTrace();
        }
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
            System.out.println("ERROR 204");
            e.printStackTrace();
        }
        return isFree1;
    }

    public List read(Socket connection) {
        String message = "";
        Socket socket = null;
        List<String> messageList = new ArrayList<>();
        try {
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while((bytesRead = inputStream.read(buffer))!=-1){
                message = new String(buffer, 0, bytesRead);
                messageList.add(message);
            }
            inputStream.close();
        } catch (IOException e) {
            System.out.println("ERROR 205");
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    System.out.println("ERROR 206");
                }
            }
        }
        return messageList;
    }
}
