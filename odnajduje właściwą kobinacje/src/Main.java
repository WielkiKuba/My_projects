import java.util.Random;
import java.util.Scanner;
public class Main{
    public static void main(String[] args) {
        System.out.println("Program deokodujący");
        System.out.println("---------------------------------------");
        Random random = new Random();
        System.out.println("podaj maxymalną ilość cyfr w kodzie");
        Scanner scanner = new Scanner(System.in);
        double x = scanner.nextDouble();
        double combinations = Math.pow(10, x);
        int liczbaKombinacji = (int) combinations-1;
        int kod = random.nextInt(liczbaKombinacji);
        Main test = new Main();
        for(int i = 0;i<liczbaKombinacji;i++){
            int wynik = Main.test(i,kod,x);
            if(wynik==1){
                break;
            }
        }
    }
    public static int test(int number, int kod, double x){
        String String_number = Integer.toString(number);
        int newZeros = (int)x - String_number.length();
        String_number=Integer.toString(number);
        if(String_number.length() < x){
            for(int j = 0;j<newZeros;j++){
                String_number = "0"+String_number;
            }
        }
        if (number == kod){
            System.out.println("---------------------------------------");
            System.out.println("prawidłowy kod to: "+ String_number);
            return 1;
        }
        else{
            System.out.println("kod nieprawidłowy: "+String_number);
            return 0;
        }
    }
}