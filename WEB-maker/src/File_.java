import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class File_ {
    int headNumber=0;
    int bodyNumber=0;
    String desktopPath = System.getProperty("user.home")+ File.separator+"Desktop";
    String projectPath;
    public void path(String name){
        projectPath = desktopPath+File.separator+name+File.separator;
    }
    File file = new File(projectPath+"index.html");
    public void folder(){
        File folder = new File(projectPath);
        try{
            if(!folder.exists()){
                folder.mkdir();
                file.createNewFile();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("<html>");
                fileWriter.close();
            }
            else{
                if(!file.exists()){
                    file.createNewFile();
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write("<html>");
                    fileWriter.close();
                }
            }

        }catch (IOException e){
            System.out.println("ERROR 101");
            e.printStackTrace();
        }
    }
    public void head(){
        try{
            FileWriter fileWriter = new FileWriter(projectPath+"index.html",true);
            fileWriter.write("\n#head"+headNumber+"\n<head>\n\n</head>");
            fileWriter.close();
            headNumber++;
        }catch (IOException e){
            System.out.println("ERROR 102");
            e.printStackTrace();
        }
    }
    public void body(){
        try{
            FileWriter fileWriter = new FileWriter(projectPath+"index.html",true);
            fileWriter.write("\n#body"+bodyNumber+"\n<body>\n\n</body>");
            fileWriter.close();
            bodyNumber++;
        }catch (IOException e){
            System.out.println("ERROR 103");
            e.printStackTrace();
        }
    }
    public void addElement(String where,String what,String titleText){
        try{
            Scanner scanner1 = new Scanner(file);
            String fileContent="";
//            boolean isFound = false;
            while(scanner1.hasNextLine()){
                String nextLine = scanner1.nextLine();
                System.out.println(nextLine);
//                if(!isFound){
//                    if(nextLine.equals(where)){
//                        isFound = true;
//                    }
//                }
//                if(isFound){
//                    if(nextLine.isEmpty()){
//                        isFound = false;
//                        fileContent = fileContent+"<title>"+titleText+"</title>";
//                    }
//                }
                fileContent = fileContent+nextLine;
            }
            try{
                scanner1.close();
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(fileContent);
                fileWriter.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
            System.out.println("ERROR 104");
        }
    }
}
