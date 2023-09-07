import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Config {
     String Path = System.getProperty("user.home")+File.separator+"AppData"+File.separator+"Roaming"+File.separator+".Library";
    File file = new File(Path+File.separator+"Password.txt");
    Console console = new Console();
    public String PIN(Integer mode){
        String PIN = "";
//        1 zwraca PIN
            try{
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    String nextLine = scanner.nextLine();
                    if(mode==1){
                        if(nextLine.equals("true")||nextLine.equals("false")){
                            break;
                        }
                    }
                    PIN = nextLine;
                }
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("error 301");
            }
        return PIN;
    }
    public void change_PIN(String PIN){
        try{
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(PIN+"\n");
            fileWriter.write("true");
            fileWriter.close();
            String name = file.getName();
            System.out.println("PIN has been changed");
            console.log("changed file {"+name+"} | PIN changed for \""+PIN+"\"");
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("error 302");
        }
    }
}