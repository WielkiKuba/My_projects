import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        Scanner scanner = new Scanner(System.in);
        File_ file = new File_();
        int times = 50;
        console.clear(times);
        System.out.println("==========================");
        System.out.println("Enter project name:");
        System.out.println("==========================");
        String name = scanner.nextLine();
        file.path(name);
        file.folder();
        File_structure structure = new File_structure();
        while(true){
            console.clear(times);
            System.out.println("==========================");
            System.out.println("1-Display structure");
            System.out.println("2-Add element");
            System.out.println("3-Delete element");
            System.out.println("==========================");
            switch (scanner.nextInt()){
                case (1)->{
                    List<String> structure_List = structure.read(name);
                    System.out.println("==========================");
                    for(String element : structure_List){
                        System.out.println(element);
                    }
                    System.out.println("==========================");
                    break;
                }
                case (2)->{
                    System.out.println(" ");
                    System.out.println("==========================");
                    System.out.println("1-Head");
                    System.out.println("2-Title");
                    System.out.println("3-Body");
                    System.out.println("4-Picture");
                    System.out.println("5-Text");
                    System.out.println("==========================");
                    switch (scanner.nextInt()){
                        case (1)->{
                            file.head();
                            break;
                        }
                        case (2)->{
                            System.out.println("==========================");
                            System.out.println("Enter a number of head in which create title");
                            System.out.println("==========================");
                            int headtitleNumber = scanner.nextInt();
                            System.out.println("==========================");
                            System.out.println("Enter a title");
                            System.out.println("==========================");
                            String where = "#head"+headtitleNumber;
                            String what = "title";
                            scanner.nextLine();
                            String titleText = scanner.nextLine();
                            file.addElement(where,what,titleText);
                            break;
                        }
                        case (3)->{
                            file.body();
                            break;
                        }
                        case (4)->{
                            break;
                        }
                        case (5)->{
                            break;
                        }
                    }
                    break;
                }
                case (3)->{
                    System.out.println(3);
                    break;
                }
            }
            System.out.println("Enter any key to restart");
            String x = scanner.nextLine();
            String x2 = scanner.nextLine();
        }
    }
}