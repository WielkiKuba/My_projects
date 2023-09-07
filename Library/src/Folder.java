import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import java.time.*;
import java.util.List;
public class Folder {
    Clear clear = new Clear();
    Console console = new Console();
    int clearTimes = 500;
    String Path = System.getProperty("user.home")+File.separator+"AppData"+File.separator+"Roaming"+File.separator+".Library";
//    C:/users/user/Appdata/Roaming/.Library
    public void create(){
        File file = new File(Path);
        if(!file.exists()){
            file.mkdir();
        }
    }
    public void create1(){
        File file = new File(Path+File.separator+"Books.txt");
        File logs = new File(Path+File.separator+"Logs.txt");
        File rent = new File(Path+File.separator+"Rent.txt");
        File userRent = new File(Path+File.separator+"User_rent.txt");
        File password = new File(Path+File.separator+"Password.txt");
        List<File> list = new ArrayList<>();
        list.add(file);
        list.add(logs);
        list.add(rent);
        list.add(userRent);
        int number = 0;
        for(File i : list){
            if(!i.exists()){
                try{
                    i.createNewFile();
                    String name = i.getName();
                    console.log("file {"+name+"} created");
                }
                catch (IOException e){
                    e.printStackTrace();
                    System.out.println("error 11"+number);
                }
                number++;
            }
            if(!password.exists()){
                try{
                    password.createNewFile();
                    String name = password.getName();
                    console.log("file {"+name+"} created");
                    try{
                        FileWriter fileWriter = new FileWriter(password);
                        fileWriter.write("1234"+"\n");
                        fileWriter.write("false");
                        fileWriter.close();
                    }
                    catch (IOException e){
                        e.printStackTrace();
                        System.out.println("error 115");
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                    System.out.println("error 114");
                }
            }
        }
    }
    public void edit(String bookName,Integer booksNumber,Integer mode,Boolean isLogon){
        String mode1 = "";
        String[] line = new String[0];
        String trimLine = "";
        if(mode==0){
            mode1 = "Books.txt";
        }
        if(mode==1){
            mode1 = "Rent.txt";
        }
        File file = new File(Path+File.separator+mode1);
        if(file.exists()){
            try{
                Scanner scanner = new Scanner(file);
                boolean isNew = true;
                while(scanner.hasNextLine()){
                    line = (scanner.nextLine()).split("\"");
                    trimLine = line[1].trim();
                    if(bookName.toUpperCase().equals(trimLine.toUpperCase())){
                        isNew = false;
                    }
                }
                if(!isNew){
                    Scanner scanner1 = new Scanner(file);
                    String content = "";
                    while(scanner1.hasNextLine()){
                        String line2 = scanner1.nextLine();
                        String[] nextLine_Tochar = line2.split("\"");
                        String bookName2 = nextLine_Tochar[1].trim().toUpperCase();
                        String[] booksNumber2 = nextLine_Tochar[2].split(" ");
                        int number = Integer.parseInt(booksNumber2[2]);
                        if(bookName2.equals(bookName.toUpperCase())){
                            String betterLine2 = "\" "+bookName + " \" ( "+(number+booksNumber)+" )";
                            content = content + betterLine2 + "\n";
                        }
                        else{
                            content = content + line2 + "\n";
                        }
                    }
                    scanner.close();
                    FileWriter fileWriter = new FileWriter(Path+File.separator+mode1);
                    fileWriter.write(content);
                    fileWriter.close();
                    if(isLogon){
                        System.out.println("<Book-s "+"["+bookName+" x"+booksNumber+"]"+" has been added");
                    }
                    String name = file.getName();
                    console.log("changed file {"+name+"} | added "+"\""+bookName+"\""+" count: "+booksNumber);
                }
                else{
                    String content="";
                    FileWriter fileWriter = new FileWriter(Path+File.separator+mode1,true);
                    fileWriter.write(content+"\" "+bookName+" \""+" "+"( "+booksNumber+" )"+"\n");
                    fileWriter.close();
                    if(isLogon){
                        System.out.println("<Book-s "+"["+bookName+"]"+" has been added");
                    }
                    String name = file.getName();
                    console.log("changed file {"+name+"} | added "+"\""+bookName+"\""+" count: "+booksNumber);
                    scanner.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("error 102");
            }
        }
    }
    public void del(String bookName,Integer mode,Integer isLogon){
        String mode1= "";
        if(mode == 0){
            mode1 = "Books.txt" ;
        }
        if(mode == 1){
            mode1 = "Rent.txt";
        }
        File file = new File(Path+File.separator+mode1);
        if(file.exists()){
            try{
                Scanner scanner = new Scanner(file);
                String content="";
                String[] nextLine_toChar = new String[0];
                while(scanner.hasNextLine()){
                    String nexLine = scanner.nextLine();
                    nextLine_toChar = nexLine.split(" ");
                    String Modify_nextLine = nextLine_toChar[1];
                    for(int i=2; i<nextLine_toChar.length-4;i++){
                        Modify_nextLine = Modify_nextLine+" "+nextLine_toChar[i];
                    }
                    String Modify_nextLine_toUpper = Modify_nextLine.toUpperCase();
                    if(Modify_nextLine_toUpper.equals(bookName.toUpperCase())){
                        continue;
                    }
                    else{
                        content = content+nexLine+"\n";
                    }
                }
                FileWriter fileWriter = new FileWriter(Path+File.separator+mode1);
                fileWriter.write(content);
                fileWriter.close();
                if(isLogon == 1){
                    System.out.println("<Book-s "+"["+bookName+"]"+" has been deleted");
                }
                String name = file.getName();
                console.log("changed file {"+name+"} | deleteted all "+"\""+bookName+"\""+" count: "+nextLine_toChar[nextLine_toChar.length-2]);
                scanner.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("error 103");
            }
        }
    }
    public int del_number(String bookName,Integer number,Integer mode,Boolean isLogon){
        String mode1 = "";
        if(mode == 0){
            mode1 = "Books.txt";
        }
        if(mode == 1){
            mode1 = "Rent.txt";
        }
        File file = new File(Path+File.separator+mode1);
        int end = 0;
        if(file.exists()){
            try{
                Scanner scanner = new Scanner(file);
                String content="";
                while(scanner.hasNextLine()){
                    String nexLine = scanner.nextLine();
                    String[] nextLine_toChar = nexLine.split(" ");
                    String Modify_nextLine= nextLine_toChar[1];
                    for(int i=2; i<nextLine_toChar.length-4;i++){
                        Modify_nextLine = Modify_nextLine+" "+nextLine_toChar[i];
                    }
                    String Modify_nextLine_toUpper = Modify_nextLine.toUpperCase();
                    if(Modify_nextLine_toUpper.equals(bookName.toUpperCase())){
                        if(nextLine_toChar[nextLine_toChar.length-1].equals("(1)")){
                            continue;
                        }
                        else {
                            String str = "\" ";
                            for(int j =1;j<nextLine_toChar.length;j++){
                                String word = nextLine_toChar[j];
                                if(word.equals(nextLine_toChar[nextLine_toChar.length-2])){
                                    int int_number = (Integer.parseInt(nextLine_toChar[nextLine_toChar.length-2]))-number;
                                    if(int_number<0){
                                        System.out.println("have entered wrong number of books (out of quantality)");
                                        end = 1;
                                    }
                                    if(int_number == 0){
                                        end = 2;
                                    }
                                    else{
                                        str = str + int_number+" ";
                                    }
                                }
                                else{
                                    str=str+word+" ";
                                }
                            }
                            content = content+str+"\n";
                        }
                    }
                    else{
                        content = content+nexLine+"\n";
                    }
                }
                if(end == 0){
                    FileWriter fileWriter = new FileWriter(Path+File.separator+mode1);
                    fileWriter.write(content);
                    fileWriter.close();
                    if(isLogon){
                        System.out.println("<Book-s "+"["+bookName+"]"+" has been deleted");
                    }
                    String name = file.getName();
                    console.log("changed file {"+name+"} | deleteted "+"\""+bookName+"\""+" count: "+number);
                }
                else{

                }
                scanner.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("error 104");
            }
        }
        return end;
    }
    public void list(String path,Integer islogOn){
        clear.console(clearTimes);
        if (islogOn != 0) {
            System.out.println("<List of our books");
        }
        boolean isEmpty = true;
        System.out.println("==================================================");
        File file = new File(Path+File.separator+path);
        if(file.exists()){
            try{
                Scanner scanner = new Scanner(file);
                while(scanner.hasNextLine()){
                    String nextLine = scanner.nextLine();
                    if(islogOn==0){
                        System.out.println(nextLine);
                        isEmpty = false;
                    }
                    else{
                        String[] nextLine_Splited = nextLine.split("\"");
                        if(nextLine_Splited.length >= 2) {
                            String bookName = nextLine_Splited[1].trim();
                            System.out.println(bookName);
                            isEmpty = false;
                        }
                    }
                }
                if(islogOn==1){
                    console.log("List displayed");
                }
                scanner.close();
            }
            catch (IOException e){
                e.printStackTrace();
                System.out.println("error 105");
            }
        }
        if(isEmpty){
            System.out.println("<List is empty>");
        }
        System.out.println("==================================================");
    }
    public void edit_userRent(String name,String lastName,String time,String bookName){
        try{
            File file = new File(Path+File.separator+"User_rent.txt");
            FileWriter fileWriter = new FileWriter(file,true);
            fileWriter.write("["+LocalDate.now()+"] "+lastName+" "+name+" rented a \" "+bookName+" \" for "+time+" day-s | to: "+LocalDate.now().plusDays(Long.parseLong(time))+"\n");
            fileWriter.close();
            System.out.println(lastName+" "+name+" rented a \""+bookName+"\" for "+time+" day-s");
            String fileName = file.getName();
            console.log("changed file {"+fileName+"} | "+lastName+" "+name+" rented a \""+bookName+"\" for "+time+" day-s | to: "+LocalDate.now().plusDays(Long.parseLong(time)));
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("error 106");
        }
    }
    public void deleteRent(String name,String lastName,String bookName){
        File file = new File(Path+File.separator+"User_rent.txt");
        boolean found = false;
        try{
            Scanner scanner = new Scanner(file);
            String content = "";
            while(scanner.hasNextLine()){
                String clearLine = scanner.nextLine();
                String[] line = clearLine.split(" ");
                if(line[1].toUpperCase().equals(lastName.toUpperCase())){
                    if(line[2].toUpperCase().equals(name.toUpperCase())){
                        if(line[6].toUpperCase().equals(bookName.toUpperCase())){
                            found = true;
                            continue;
                        }
                    }
                }
                content = content + clearLine+"\n";
            }
            scanner.close();
            if(found){
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(content);
                fileWriter.close();
                String fileName = file.getName();
                console.log("changed file {"+fileName+"} | "+lastName+" "+name+"'s rent (\""+bookName+"\") has been removed");
            }
            else{
                System.out.println("Product not found");
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("error 107");
        }
    }
    public boolean doHavewe(String bookName){
        boolean doHave = false;
        File file = new File(Path+File.separator+"Books.txt");
        try{
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] lineTochar = scanner.nextLine().split("\"");
                String line = lineTochar[1].trim().toUpperCase();
//                System.out.println(line);
                if(line.equals(bookName.toUpperCase())){
                    doHave = true;
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return doHave;
    }
}