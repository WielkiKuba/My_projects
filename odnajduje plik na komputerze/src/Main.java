import java.io.File;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        while (true) {
            Scanner scanner2 = new Scanner(System.in);
            System.out.println("===============================================================");
            //nie przeszukuje folderów w folderze
            System.out.println("podaj nazwe pliku");
            System.out.println("(naprzykład: test.txt)(narazie działa tylko .txt)");
            String fileName = scanner2.nextLine();
            String File_path = System.getProperty("user.home");
            String User_name = System.getProperty("user.name");
            System.out.println();
            File folder = new File("C:"+File.separator+"Users",User_name);
            File[] files = folder.listFiles();
            File file = new File(File_path,fileName);
            int count = files.length;
            if(file.exists()){
                System.out.println(File_path);
                System.out.println("<PROSZE, TREŚĆ WYBRANEGO PRZEZ CIEBIE PLIKU>");
                System.out.println("===============================================================");
                try {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNextLine()) {
                        String tresc = scanner.nextLine();
                        System.out.println(tresc);
                    }
                } catch (java.io.FileNotFoundException e) {
                    System.out.println("Error");
                }
                System.out.println("===============================================================");
            }
            else{
                for (int i = 0; i < count; i++) {
                    File file2 = new File(files[i],fileName);
                    if(file2.exists()){
                        System.out.println(files[i]);
                        System.out.println("<PROSZE, TREŚĆ WYBRANEGO PRZEZ CIEBIE PLIKU>");
                        System.out.println("===============================================================");
                        try {
                            Scanner scanner = new Scanner(file2);
                            while (scanner.hasNextLine()) {
                                String tresc = scanner.nextLine();
                                System.out.println(tresc);
                            }
                        } catch (java.io.FileNotFoundException e) {
                            System.out.println("In "+File_path+" File not found-wystąpił błąd");
                        }
                        System.out.println("===============================================================");
                        break;
                    }
                    else{
                        File[] files2 = files[i].listFiles();
                        if(files2 != null ){
                            int count2 = files2.length;
                            for(int j =0; j<count2;j++){
                                File file3 = new File(files2[j],fileName);
                                if(file3.exists()){
                                    System.out.println(files2[j]);
                                    System.out.println("<PROSZE, TREŚĆ WYBRANEGO PRZEZ CIEBIE PLIKU>");
                                    System.out.println("===============================================================");
                                    try {
                                        Scanner scanner = new Scanner(file3);
                                        while (scanner.hasNextLine()) {
                                            String tresc = scanner.nextLine();
                                            System.out.println(tresc);
                                        }
                                    } catch (java.io.FileNotFoundException e) {
                                        System.out.println("In "+File_path+" File not found-wystąpił błąd");
                                    }
                                    System.out.println("===============================================================");
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            System.out.println("Press any key to restart");
            String x = scanner2.nextLine();
            clean();
        }
    }
    public static void clean () {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
}