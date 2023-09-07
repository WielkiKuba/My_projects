import java.util.Scanner;
import java.util.Random;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random lucky = new Random();
        int number =lucky.nextInt(101);
        boolean game_procesing = true;
        System.out.println("zgadnij liczbe z przedziału od 1 do 100");
        while(game_procesing){
            int userNumber = scanner.nextInt();
            if(userNumber==number){
                game_procesing=false;
            }
            else{
                if(userNumber<number){
                    System.out.println("liczba o ,której myśle jest wieksza");
                }
                else{
                    System.out.println("liczba o ,której myśle jest mniejsza");
                }
            }
        }
        System.out.println("wygrałeś :(");
        String y = scanner.nextLine();
    }
}