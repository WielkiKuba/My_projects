import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int times = 50;
        int localPort = 8000;
        Server server = new Server();
        String descriptionOld = "";
        String myIp = server.myIp();
        System.out.println(myIp);
        while(true){
            try{
                Scanner scanner = new Scanner(System.in);
                clear(times);
                System.out.println("=========================");
                System.out.println("Enter description");
                System.out.println("=========================");
                String description = (scanner.nextLine()).toUpperCase();
                clear(times);
                if(!server.bio(description)){
                    System.out.println("=========================");
                    System.out.println("This nickname(description) is already occupied");
                    System.out.println("=========================");
                }else {
                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException e){
                        System.out.println("ERROR 102");
                        e.printStackTrace();
                    }
                    try{
                        Socket localSocket = server.connection("localhost",localPort);
                        if(localSocket.isConnected()){
                            server.send(localSocket,"NAME#"+description,"y");
                        }
                    }catch (IOException e){
                        Process console = process().start();
                        try{
                            Thread.sleep(1000);
                            Socket localSocket = server.connection("localhost",localPort);
                            server.send(localSocket,"NAME#"+description,"y");
                        }catch (InterruptedException i){
                            System.out.println("ERROR 103");
                            i.printStackTrace();
                        }
                    }
                    Boolean shutdown = false;
                    while(true){
                        Boolean printed = false;
                        List<String> anotherClients = server.get("getList");
                        clear(times);
                        System.out.println("=========================");
                        for(String client:anotherClients){
                            String[] clientParts = client.split("#");
                            if((clientParts[0].equals(description))||((clientParts[0].equals(descriptionOld))&&(clientParts[1].equals(myIp)))){
                                continue;
                            }
                            else {
                                System.out.println(client);
                                printed = true;
                            }
                        }
                        if(!printed){
                            System.out.println("There's no online users");
                        }
                        System.out.println("=========================\n");
                        System.out.println("<Enter an option");
                        System.out.println("=========================");
                        System.out.println("1-Send message");
                        System.out.println("2-Rename a profile");
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
                                            Socket socket = server.connection(ip,localPort);
                                            server.send(socket,message,"y");
                                        }
                                    }
                                }
                                case (2)->{
                                    clear(times);
                                    System.out.println("=========================");
                                    System.out.println("Your actual nickname: "+description);
                                    System.out.println("=========================\n");
                                    System.out.println("=========================");
                                    System.out.println("Are you sure, you want to change nickname?");
                                    System.out.println("[Yes=y,No=n]");
                                    System.out.println("=========================");
                                    String change = scanner.nextLine();
                                    if(change.equals("y")){
                                        clear(times);
                                        descriptionOld = description;
                                        System.out.println("=========================");
                                        System.out.println("Enter description");
                                        System.out.println("=========================");
                                        description = (scanner.nextLine()).toUpperCase();
                                        if(!server.bio(description)){
                                            clear(times);
                                            System.out.println("=========================");
                                            System.out.println("This nickname(description) is already occupied");
                                            System.out.println("=========================");
                                            description = descriptionOld;
                                        }else {
                                            Socket localSocket = server.connection("localhost",localPort);
                                            server.send(localSocket,"NAME#"+description,"y");
                                        }
                                    }
                                }
                                case (4)->{
                                    Socket localSocket = server.connection("localhost",localPort);
                                    server.send(localSocket,"SHUTDOWN#"+description,"y");
                                    shutdown = true;
                                }
                            }
                            if(shutdown){
                                break;
                            }
                            if(optionInt>3||optionInt<1){
                                System.out.println("Enter a number between 1-4");
                            }
                        }catch (Exception e){
                            System.out.println("Enter a number between 1-4");
                        }
                    }
                    if(shutdown){
                        break;
                    }
                }
                String x = scanner.nextLine();
            }catch (IOException e){
                System.out.println("ERROR 101");
                e.printStackTrace();
            }
        }
    }
    public static ProcessBuilder process(){
        String os = System.getProperty("os.name");
        String command0 = "";
        String command1 = "";
        String command2 = "";
        if(os.equals("Linux")){
            command0 = "/usr/bin/gnome-terminal";
            command1 = "-e";
            command2 = "bash -c '$JAVA_HOME/bin/java -jar Socket_Listener.jar; exec bash'";
        }else {
            command0 = "CMD";
            command1 = "/C";
            command2 = "$JAVA_HOME/bin/java -jar Socket_Listener.jar";
        }
        ProcessBuilder processBuilder = new ProcessBuilder(command0,command1,command2);
        return processBuilder;
    }
    public static void clear(int times){
        for(int i=0;i<times;i++){
            System.out.println(" ");
        }
    }
}
