import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
public class Main {
    public static void main(String[] args) {
        int times = 1000;
        clean cleaner = new clean();
        cleaner.console(times);
        while(true){
            Scanner scanner = new Scanner(System.in);
            System.out.println("wybierz operacje:");
            System.out.println("1>tworzenie nowej notatki");
            System.out.println("2>otwieranie notatki");
            System.out.println("3>edycja starej notatki");
            System.out.println("4>lista notatek");
            System.out.println("5>usuniecie danej notatki");
            String Path = System.getProperty("user.home")+File.separator+"documents"+File.separator+"notes";
            int option = scanner.nextInt();
            scanner.nextLine();
            if(option==1){
                File folder = new File(Path);
                folder.mkdir();
                System.out.println("podaj nazwe notatki (np.Notatka.txt)");
                String nameFile = scanner.nextLine();
                System.out.println("wpisz treść notatki \n(wpisz \"saveFile\" aby zapisać i wyjsc)");
                boolean Procesing = true;
                String insideFile = "<"+nameFile+">";
                while(Procesing){
                    String inside = scanner.nextLine();
                    if (inside.equals("saveFile")){
                        Procesing = false;
                        try{
                            FileWriter writer = new FileWriter(Path+File.separator+nameFile);
                            writer.write(insideFile);
                            writer.close();
                            System.out.println("notatka zostala zapisana");
                            System.out.println("Press any key to restart");
                            String x = scanner.nextLine();
                            clean.console(times);
                        }
                        catch (IOException e){
                            System.out.println("wystąpił blad podczas tworzenia pliku");
                            e.printStackTrace();
                        }
                    }
                    else{
                        insideFile=insideFile+"\n"+inside;
                    }
                }
            }
            if(option==2){
                System.out.println("podaj nazwe notatki do otworzenia (Notatka.txt)");
                String fileName = scanner.nextLine();
                String filePath = System.getProperty("user.home")+File.separator+"documents"+File.separator+"notes";
                File file = new File(filePath,fileName);
                if(file.exists()){
                    try{
                        System.out.println("<PROSZE, TREŚĆ WYBRANEGO PRZEZ CIEBIE PLIKU>");
                        System.out.println("===============================================================");
                        Scanner scannerfile = new Scanner(file);
                        while(scannerfile.hasNextLine()){
                            String line = scannerfile.nextLine();
                            System.out.println(line);
                        }
                        System.out.println("===============================================================");
                        System.out.println("Press any key to restart");
                        String x = scanner.nextLine();
                        clean.console(times);
                    }
                    catch (IOException e){
                        System.out.println("error");
                    }
                }
                else{
                    System.out.println("nie znaleziono pliku");
                }
            }
            if(option==3){
                System.out.println("podaj nazwe notatki do edycji (np.Notatka.txt)");
                String nameFile = scanner.nextLine();
                String filePath = System.getProperty("user.home")+File.separator+"documents"+File.separator+"notes";
                File file = new File(filePath,nameFile);
                if(file.exists()){
                    try{
                        Desktop.getDesktop().open(file);
                        System.out.println("Press any key to restart");
                        String x = scanner.nextLine();
                        clean.console(times);
                    }
                    catch (IOException e){
                        System.out.println("error");
                        e.printStackTrace();
                    }
                }
                else{
                    System.out.println("pliku nie znaleziono");
                }
            }
            if(option==4){
                String filePath = System.getProperty("user.home")+File.separator+"documents"+File.separator+"notes";
                File folder = new File(filePath);
                if(folder.exists()){
                    File[] files = folder.listFiles();
                    int files_count = files.length;
                    System.out.println("===============================================================");
                    for(int i = 0; i<files_count;i++){
                        String name = files[i].getName();
                        System.out.println(name);
                    }
                    System.out.println("===============================================================");
                    System.out.println("Press any key to restart");
                    String x = scanner.nextLine();
                    clean.console(times);
                }
                else{
                    folder.mkdir();
                    System.out.println("wystąpil blad podczas szukania notatek");
                }
            }
            if(option==5){
                System.out.println("podaj nazwe notatki do usuniecia");
                String fileName = scanner.nextLine();
                String filePath = System.getProperty("user.home")+File.separator+"documents"+File.separator+"notes"+File.separator+fileName;
                File file = new File(filePath);
                file.delete();
                System.out.println("plik "+file.getName()+" zostal usuniety");
                System.out.println("Press any key to restart");
                String x = scanner.nextLine();
                clean.console(times);

            }
            if(option>5){
                System.out.println("miales wpisac liczbe od 1 do 4 :( ");
                String x = scanner.nextLine();
            }
        }
    }
}