import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        while(true){
            for(int j =0;j<100;j++){
                System.out.println(" ");
            }
            Scanner scanner = new Scanner(System.in);
            Random random = new Random();
            System.out.println("<========================================>");
            System.out.println("              Cube generator");
            System.out.println("<========================================>");
            for(int i = 0; i<2;i++){
                System.out.println(" ");
            }
            System.out.println("<========================================>");
            System.out.println("      Please enter number of walls");
            System.out.println("<========================================>");
            int numbers_of_walls = scanner.nextInt();
            scanner.nextLine();
            System.out.println(" ");
            System.out.println("<========================================>");
            System.out.println("      Please enter number of times");
            System.out.println("<========================================>");
            int times = scanner.nextInt();
            scanner.nextLine();
            for(int k = 0; k<times;k++){
                int random1 = random.nextInt(numbers_of_walls);
                System.out.println("Result of "+((int) k+1)+" throw: "+((int) random1+1));
            }
            System.out.println("Press any key to restart");
            String x = scanner.nextLine();
            for(int j =0;j<100;j++){
                System.out.println(" ");
            }
        }
    }
}