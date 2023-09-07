import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        Scanner scanner = new Scanner(System.in);
        int times = 500;
//        times to to jak bardzo tekst ma sie przesunąć w dół
        while(true){
            console.clear(times);
            System.out.println("<Polynomial calculator>");
            System.out.println("=========================================");
            System.out.println("<Powers = x**y");
            System.out.println("<Multiplication = x*y");
            System.out.println("<Division = x/y");
            System.out.println("<Subtraction = x-y");
            System.out.println("<Adding = x+y");
            System.out.println("<Logarithms = Log[x(y)] e.g. Log[4(16)]=2");
            System.out.println("<Elements = element[x(y)] e.g element[4(2)] = 2, element[16(2)] = 4");
            System.out.println("<Pi number = PI");
            System.out.println("=========================================");
            System.out.println("<Remember 0,5 is same as 1/2");
            System.out.println("<Remember do not make spaces (Good: x**2+1=0, Bad: x ** 2 + 1 = 0)");
            System.out.println("=========================================");
            System.out.println("Enter an operation");
            System.out.println("1-Solving polynomials e.g 4x+2=10");
            System.out.println("2-solving simple actions e.g. (x**y)/z*PI");
            int operation = scanner.nextInt();
            scanner.nextLine();
            switch (operation){
                case (1)->{
                    System.out.println(2/4);
                }
                case (2)->{
                    String solution ="";
                    List<String> list = new ArrayList<>();
                    System.out.println("Enter action");
                    String action = scanner.nextLine();
                    char[] action2 = action.toCharArray();
                    for(char j : action2){
                        list.add(String.valueOf(j));
                    }
                    for(int i = 0;i<list.size()-1;i++){
                        if(list.get(i).equals("*")||list.get(i).equals("**")||list.get(i).equals("/")){
                            solution = list.get(i-1)+list.get(i)+list.get(i+1);
                        }
                    }
                }
            }
            System.out.println("Press any key to restart");
            scanner.nextLine();
        }
    }
}