import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
public class Server {
        public static String server = "jakubdomain.ddns.net";
//    public static String server = "localhost";
//    public static String server = "192.168.1.3";
    public static int port = 55555;
    private static InputStream inputStream;
    private static OutputStream outputStream;
    private static Socket socket;
    public synchronized OutputStream getOutputStream() throws IOException {
        if (outputStream == null || getSocket().isOutputShutdown()) {
            outputStream = getSocket().getOutputStream();
        }
        return outputStream;
    }
    public synchronized InputStream getInputStream() throws IOException {
        if (inputStream == null || getSocket().isInputShutdown()) {
            inputStream = getSocket().getInputStream();
        }
        return inputStream;
    }
    public synchronized Socket getSocket() throws IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(server, port);
            waitThread(200);
        }
        return socket;
    }
    public String myIp() {
        send("clientIp");
        String ip = simpleRead();
        return ip;
    }

    public String getList() {
        String content=null;
        send("getList");
        while(content==null){
            content = simpleRead();
        }
        return content;
    }
    public void send(String what) {
        try {
            getOutputStream().write(what.getBytes());
        } catch (IOException e) {
            System.out.println("ERROR 201");
        }
    }
    public boolean bio(String description) {
        boolean isFree1 = false;
        String message = "BIO#" + description;
        String isFree = null;
        send(message);
        isFree = simpleRead();
        if (isFree.equals("true")) {
            isFree1 = true;
        }
        return isFree1;
    }
    public synchronized String simpleRead(){
        String message="";
        try{
            byte[] buffer = new byte[1024];
            int bytesRead = getInputStream().read(buffer);
            if(bytesRead > 0){
                message = new String(buffer, 0, bytesRead);
            }
        }
        catch (IOException e){
            System.out.println("ERROR 211");
        }
        return message;
    }
    public static void waitThread(int timeToWait){
        try{
            Thread.sleep(timeToWait);
        }catch (InterruptedException e){
            System.out.println("ERROR 221");
        }
    }
}