import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;
import java.time.*;
import java.io.IOException;
public class Console {
    public static void log(String command){
        String Path = System.getProperty("user.home")+File.separator+"AppData"+File.separator+"Roaming"+File.separator+".Library";
        File file = new File(Path+File.separator+"Logs.txt");
        try{
            Scanner scanner = new Scanner(file);
            String content = "";
            while(scanner.hasNextLine()){
                content = content + scanner.nextLine()+"\n";
            }
            scanner.close();
            FileWriter fileWriter = new FileWriter(file);
            content = content+"["+LocalTime.now()+"/"+LocalDate.now()+"] "+command+"\n";
            fileWriter.write(content);
            fileWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("error 201");
        }
    }
}