import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.List;
public class File_structure{
    public List read(String name){
        String filePath = System.getProperty("user.home")+File.separator+"Desktop"+File.separator+name+File.separator+"index.html";
        File readFile = new File(filePath);
        String fileStructure;
        List<String> list = new ArrayList<>();
        try{
            Scanner scanner = new Scanner(readFile);
            while(scanner.hasNext()){
                String nextLine = scanner.nextLine();
                char[] nextLine_TOchar = nextLine.toCharArray();
                if(!(nextLine_TOchar.length==0)){
                    String charTostring = String.valueOf(nextLine_TOchar[0]);
                    if(charTostring.equals("#")){
                        String[] line =nextLine.split("#");
                        String stringLine = line[1];
                        list.add(stringLine);
                    }
                }
            }
            scanner.close();
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("ERROR 201");
        }
        return list;
    }
}
