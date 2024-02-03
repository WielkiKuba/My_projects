import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
public class Main {
    public static BlockingQueue<String> connectedClients = new LinkedBlockingQueue<>();
    public static BlockingQueue<Socket> sockets = new LinkedBlockingQueue<>();
//    trzeba dodać system logów

    public static void main(String[] args){
        int port = 55555;
        Thread mainThread = new Thread(()->{
            try{
                ServerSocket serverSocket = new ServerSocket(port);
                System.out.println("=========================");
                System.out.println("TURNING ON SERVER");
                System.out.println("=========================");
                while(true){
                    Socket socket = serverSocket.accept();
                    System.out.println("["+formattedTime()+"] Connection established with client "+socket.getInetAddress().getHostAddress());
                    Thread process = new Thread(new ClientThread(socket));
                    waitThread(200);
                    process.start();
                }
            } catch (IOException e) {
                System.out.println("["+formattedTime()+"] Server start failed");
            }
        });
        mainThread.start();
    }
    public static String formattedTime(){
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateTime = currentTime.format(formatter);
        return dateTime;
    }
    public static void waitThread(int timeToWait){
        try{
            Thread.sleep(timeToWait);
        }catch (InterruptedException e){
            System.out.println("["+formattedTime()+"] \"waitThread\" failure");
        }
    }
    public static class ClientThread implements Runnable{
        private OutputStream outputStream;
        private final Socket socket2;
        private InputStream inputStream;
        private boolean isConnected = true;
        private synchronized DataInputStream getInputStream() throws IOException {
            if (inputStream == null||socket2.isInputShutdown()) {
                inputStream = socket2.getInputStream();
            }
            return new DataInputStream(inputStream);
        }
        private synchronized DataOutputStream getOutputStream() throws IOException {
            if (outputStream == null||socket2.isOutputShutdown()) {
                outputStream = socket2.getOutputStream();
            }
            return new DataOutputStream(outputStream);
        }
        public void pinger(){
            while(isConnected){
                try{
                    waitThread(60000);
                    int PING = 0;
                    for(int i = 0;i<5;i++){
                        waitThread(1000);
                        send(socket2,"PING",false);
                        System.out.println("["+formattedTime()+"] Ping "+clientIP);
                        if(simpleRead(false).equals("PING")){
                            System.out.println("["+formattedTime()+"] Ping from "+clientIP);
                            PING++;
                        }
                    }
                    System.out.println("["+formattedTime()+"] Received "+PING+"/5 packages");
                    if(PING==0){
                        socket2.close();
                        isConnected = false;
                        break;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        public ClientThread(Socket socket){
            this.socket2 = socket;
        }
        public String clientIP;
        @Override
        public void run(){
            clientIP = socket2.getInetAddress().getHostAddress();
            System.out.println("["+formattedTime()+"] Connection request with "+clientIP+" processed successfully");
            Thread pingerThread = new Thread(()->{
                pinger();
            });
            pingerThread.start();
            String clientName = "";
            while(isConnected){
                try{
                    if(getInputStream().available()!=0){
                        String read = simpleRead(true);
                        if(read!=null){
                            String[] message = read.split("#");
                            boolean isFree = true;
                            if(message[0].equals("MESSAGE")){
                                String receiverIp = message[1];
                                String content = message[2];
                                String senderIp = message[3];
                                Socket localSocket = null;
                                for(String line:connectedClients){
                                    String[] lineSplited = line.split("#");
                                    if(lineSplited[1].equals(receiverIp)){
                                        try{
                                            localSocket = existingSocket(lineSplited[2]);
                                            if(localSocket!=null){
                                                send(localSocket,"RMESSAGE#"+content+"#"+senderIp,true);
                                            }
                                            else {
                                                send(socket2,"RMESSAGE#Connection with client("+receiverIp+") has failed#SERVER",true);
                                            }
                                            break;
                                        }catch (IOException e){}
                                    }
                                    else {
                                        System.out.println("["+formattedTime()+"] Receiver {"+receiverIp+"} not found");
                                        send(socket2,"RMESSAGE#Connection with client("+receiverIp+") has failed#SERVER",true);
                                    }
                                }
                            } else if(message[0].equals("BIO")){
                                for(String nickname:connectedClients){
                                    String[] nickname2 = nickname.split("#");
                                    if(nickname2[0].equals(message[1])){
                                        isFree = false;
                                    }
                                }
                                String word=" ";
                                if(isFree){
                                    try{
                                        clientName = message[1];
                                        connectedClients.put(message[1]+"#"+clientIP+"#"+socket2);
                                        sockets.put(socket2);
                                        word = "true";
                                        send(socket2,word,true);
                                    }catch (InterruptedException e){
                                        e.printStackTrace();
                                    }
                                }else {
                                    word = "false";
                                    send(socket2,word,true);
                                }
                            }else if(message[0].equals("getList")){
                                String fullMessage = " ";
                                for(String word:connectedClients){
                                    String[] wordSplited = word.split("#");
                                    String name = wordSplited[0];
                                    String ip = wordSplited[1];
                                    if((ip.equals(clientIP))&&(name.equals(clientName))){
                                        continue;
                                    }
                                    String finalWord = name+"#"+ip;
                                    if(fullMessage.equals(" ")){
                                        fullMessage = finalWord;
                                    }
                                    else{
                                        fullMessage = fullMessage +"\n"+ finalWord;
                                    }
                                }
                                send(socket2,fullMessage,true);
                            } else if (message[0].equals("clientIp")) {
                                String ip = clientIP;
                                send(socket2,ip,true);
                            }
                            if(!isFree){
                                try{
                                    socket2.close();
                                }catch (IOException e){
                                    System.out.println("["+formattedTime()+"] Closing socket {"+socket2+"} failed");
                                }
                                break;
                            }
                        }
                    }
                }catch (IOException e){
                    System.out.println("["+formattedTime()+"] Conection with "+clientIP+" lost");
                }
            }
            System.out.println("["+formattedTime()+"] Connection with "+clientIP+" closed");
            for(String client:connectedClients){
                String[] clientSplited = client.split("#");
                String socketToString = socket2.toString();
                if(clientSplited[2].equals(socketToString)){
                    connectedClients.remove(client);
                }
            }
            sockets.remove(socket2);
        }
        private Socket existingSocket(String socketString) throws IOException {
            Socket oldSocket = null;
            for(Socket thisSocket:sockets){
                String thisSocketString = thisSocket.toString();
                if(socketString.equals(thisSocketString)){
                    oldSocket = thisSocket;
                    break;
                }
            }
            return oldSocket;
        }
        public synchronized String simpleRead(boolean printLog){
            String message="";
            try{
                byte[] buffer = new byte[1024];
                int bytesRead = getInputStream().read(buffer);
                if(bytesRead > 0){
                    message = new String(buffer, 0, bytesRead);
                }
                if(printLog){
                    System.out.println("["+formattedTime()+"] Data received from "+clientIP);
                }
            }
            catch (IOException e){
                if(printLog){
                    System.out.println("["+formattedTime()+"] Data receiving from "+clientIP+" failed");
                }
            }
            return message;
        }
        public synchronized void send(Socket localSocket,String what,boolean printLog){
            try{
                if(localSocket == socket2){
                    getOutputStream().write(what.getBytes());
                }else{
                    OutputStream localOutputStream = localSocket.getOutputStream();
                    localOutputStream.write(what.getBytes());
                }
                if(printLog){
                    System.out.println("["+formattedTime()+"] Data sent to "+localSocket.getInetAddress().getHostAddress());
                }
            }catch (IOException e){
                if(printLog){
                    System.out.println("["+formattedTime()+"] Data sending to "+localSocket.getInetAddress().getHostAddress()+" failed");
                }
            }
        }
    }
}