import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args){
        int port = 8080;
        BlockingQueue<String> clients = new LinkedBlockingQueue<>();
        Thread thread = new Thread(() ->{
            try{
                ServerSocket serverSocket = new ServerSocket(port);
                while(true){
                    Socket socket = serverSocket.accept();
                    String read = simpleRead(socket);
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
                        String word=" ";
                        if(isFree){
                            try{
                                System.out.println("nick jest wolny to dodałem: "+message[1]);
                                clients.put(message[1]+"#"+clientIP);
                                word = "true";
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }else {
                            word = "false";
                        }
                        send(socket,word,"y");
                    }
                    if(message[0].equals("getList")){
                        for(String word:clients){
                            send(socket,word,"n");
                            System.out.println("tu wysyłam 1 element z listy klientów: "+word);
                            System.out.println(word);
                        }
                    }
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread thread2 = new Thread(() ->{
            while(true){
                try{
                    Thread.sleep(15000);
                    for(String client:clients){
                        System.out.println("debug(Lista klientów):");
                        System.out.println("+++++++++++++++++++++++");
                        for(String i:clients){
                            System.out.println(i);
                        }
                        System.out.println("+++++++++++++++++++++++\n");
                        String[] clientParts = client.split("#");
                        String name = clientParts[0];
                        String ip = clientParts[1];
                        try{
                            Socket ayaSocket = connection(ip,8000);
                            String command = "AYA#"+name;
                            send(ayaSocket,command,"n");
                            String message = simpleRead(ayaSocket);
                            String[] dividedMessage = message.split("#");
                            if(dividedMessage.length==3){
                                System.out.println(client+" dostarczył stary i nowy nick to stary deletłem");
                                clients.remove(client);
                            }
                        }catch (IOException e){
                            System.out.println(client+" nie odpowiedział to go deletłem");
                            clients.remove(client);
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread2.start();
    }
    public static Socket connection(String ip, int destinationPort) throws IOException {
        try {
            String host = ip;
            int port = destinationPort;
            Socket socket = new Socket(host, port);
            return socket;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            throw e;
        }
    }
    public static void send(Socket socket,String what,String close){
        try{
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(what.getBytes());
            System.out.println("wysyłam: "+what);
            outputStream.flush();
            if(close.equals("y")){
                outputStream.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public static String simpleRead(Socket connection){
        String message = "";
        try{
            InputStream inputStream = connection.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);
            if(bytesRead > 0){
                message = new String(buffer, 0, bytesRead);
            }
            System.out.println("odbieram: "+message);
        }catch (IOException e){
            e.printStackTrace();
        }
        return message;
    }
}
