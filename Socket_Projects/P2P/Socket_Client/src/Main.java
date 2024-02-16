import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int times = 50;
        Server server = new Server();
        Thread thread = new Thread(() ->{
            while(true){
                Scanner scanner = new Scanner(System.in);
                clear(times);
                System.out.println("=========================");
                System.out.println("Enter description");
                System.out.println("=========================");
                String description = (scanner.nextLine()).toUpperCase();
                List<String> anotherClients = server.get("getList");
                clear(times);
                if(!server.bio(description)){
                    System.out.println("=========================");
                    System.out.println("This nickname(description) is already occupied");
                    System.out.println("=========================");
                }else {
                    if(anotherClients.isEmpty()){
                        System.out.println("=========================");
                        System.out.println("There's no online users");
                        System.out.println("=========================");
                    }else {
                        for(String client:anotherClients){
                            String[] clientParts = client.split("#");
                            if(clientParts[0].equals(description)){
                                continue;
                            }
                            else {System.out.println(client);}
                        }
                        System.out.println("Enter an option");
                        System.out.println("=========================");
                        System.out.println("1-Send message");
                        System.out.println("2-Show ma messages");
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
                                }
                                case (2)->{
                                    server.listener(true);
                                }
                            }
                            if(optionInt>2||optionInt<1){
                                System.out.println("Enter a number between 1-2");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("Enter a number between 1-2");
                        }
                    }
                }
                System.out.println("Enter any key to restart");
                String x = scanner.nextLine();
            }
        });
        Thread thread2 = new Thread(() ->{
            System.out.println("Thread2");
//            mam to problem z linux/ jak wpisuje $JAVA_HOME/bin/java --version to jest java 11 a powinno byc 14
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
                command2 = "bash -c '$JAVA_HOME/bin/java -jar Socket_Listener.jar; exec bash'";
            }
            ProcessBuilder processBuilder = new ProcessBuilder(command0,command1,command2);
            try{
                Process console = processBuilder.start();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
        thread.start();
        thread2.start();
    }
    public static void clear(int times){
        for(int i=0;i<times;i++){
            System.out.println(" ");
        }
    }
}
