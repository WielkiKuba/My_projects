import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Server server = new Server();
//    szkielet działa!
//    trzeba teraz dodać system logowania sie bazujący na SQL
//    trzeba dodać historie czatów które bedą przechowywane na komputerze lokalnym
//    trzeba też dodać funkcje która bedzie wysyłać kopie historii czatów na inne komputery użytkownika
//    a te komputery będą zapisane w... tak! mySQL
//    można też poprosić kogoś o gui (jeszcze nie wiem jak to zaimplementować, bede o tym myślał)
//    można też przeportować na telefon można
//    można też dodać opcje zmianny serwera na użytek własny/firmowy
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int times = 50;
        clear(times);
        String myIp = server.myIp();
        System.out.println("=========================");
        System.out.println("Enter description");
        System.out.println("=========================");
        final String description = (scanner.nextLine()).toUpperCase();
        clear(times);
        Listener listener = new Listener();
        Thread listenerThread = new Thread(listener);
        while(true){
            server.waitThread(200);
            clear(times);
            Boolean shutdown = false;
            if(!server.bio(description)){
                System.out.println("=========================");
                System.out.println("This nickname(description) is already occupied");
                System.out.println("=========================");
                System.out.println("App will shutdown in 5s");
                server.waitThread(5000);
                shutdown = true;
            }else {
                while(true){
                    boolean refreshing = false;
                    server.waitThread(200);
                    String anotherClients = server.getList();
                    listenerThread.start();
                    while(true){
                        clear(times);
                        System.out.println("=========================");
                        if(anotherClients.equals("")||anotherClients.equals(" ")){
                            System.out.println("There's no online users");
                        }
                        else {
                            System.out.println(anotherClients);
                        }
                        System.out.println("=========================\n");
                        System.out.println("<Enter an option");
                        System.out.println("=========================");
                        System.out.println("1-Send message");
                        System.out.println("2-Read messageses");
                        System.out.println("3-Reload clients list");
                        System.out.println("4-exit");
                        System.out.println("=========================");
                        String option = scanner.nextLine();
                        int optionInt=0;
                        try{
                            optionInt = Integer.parseInt(option);
                            switch (optionInt){
                                case (1)->{
                                    System.out.println("=========================");
                                    System.out.println("Enter client's ip");
                                    System.out.println("=========================");
                                    String ip = scanner.nextLine();
                                    clear(times);
                                    System.out.println("=========================");
                                    System.out.println("Enter a message or enter \"<quit>\" to exit");
                                    System.out.println("=========================");
                                    while(true){
                                        String message = scanner.nextLine();
                                        if(message.equals("<quit>")){
                                            break;
                                        }else{
                                            String convertedMessage = "MESSAGE#"+ip+"#"+message+"#"+myIp;
                                            listener.pause();
                                            server.send(convertedMessage);
                                            listener.resume();
                                        }
                                    }
                                }
                                case (2)->{
                                    System.out.println("=========================");
                                    if(chatMemory.equals("")){
                                        System.out.println("There's no new messages");
                                    }else {
                                        System.out.println(chatMemory);
                                    }
                                    System.out.println("=========================");
                                }
                                case (3)->{
                                    listener.pause();
                                    anotherClients = server.getList();
                                    refreshing = true;
                                    listener.resume();
                                }
                                case (4)->{
                                    shutdown = true;
                                }
                            }
                            if(shutdown){
                                break;
                            }
                            if(optionInt>4||optionInt<1){
                                System.out.println("Enter a number between 1-4");
                            }
                            if(!refreshing){
                                System.out.println("Press any key to restart");
                                String x = scanner.nextLine();
                            }
                        }catch (Exception e){
                            System.out.println("Enter a number between 1-4");
                        }
                    }
                    if(shutdown){
                        break;
                    }
                }
            }
            if(shutdown){
                listenerThread.stop();
                break;
            }
        }
    }
    public static void clear(int times){
        for(int i=0;i<times;i++){
            System.out.println(" ");
        }
    }
    public static String chatMemory = "";
    public static class Listener implements Runnable{
        private final Object lock = new Object();
        private volatile boolean paused = false;

        public void pause() {
            paused = true;
        }

        public void resume() {
            synchronized (lock) {
                paused = false;
                lock.notify();
            }
        }
        @Override
        public void run(){
            while(true){
                try{
                    if(server.getInputStream().available()!=0){
                        String read = server.simpleRead();
                        if(read!=null){
                            String[] readSplited = read.split("#");
                            if(readSplited[0].equals("RMESSAGE")){
                                if(chatMemory.equals("")){
                                    chatMemory = "Message: "+readSplited[1]+" FROM "+readSplited[2];
                                }
                                else {
                                    chatMemory = chatMemory + "\nMessage: "+readSplited[1]+" FROM "+readSplited[2];
                                }
                            }else {
//                                System.out.println("wysyłam ping");
                                server.waitThread(100);
                                server.send("PING");
                            }
                        }
                    }
                }catch (IOException e){
                    System.out.println("ERROR 101");
                }
            }
        }
    }
}