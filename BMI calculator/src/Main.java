import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Console console = new Console();
        int times = 100;
        console.clear(times);
        while(true){
            System.out.println("Enter your weight[kg]");
            int weight = scanner.nextInt();
            System.out.println("Enter your height[cm]");
            double height = scanner.nextInt();
            double height2 = Math.pow(height/100,2);
            double BMI = weight/height2;
            String BMI_description="";
            if(BMI<18.5){
                BMI_description="|niedowaga";
            }
            if(18.5<BMI && BMI<24.9){
                BMI_description="|prawidlowa masa ciala";
            }
            if(25<BMI && BMI<29.9){
                BMI_description="|nadwaga";
            }
            if(BMI>29.9){
                BMI_description="|otylosc";
            }
            double BMI_rounded = (Math.round(BMI*100));
            scanner.nextLine();
            System.out.println("Your BMI is "+BMI_rounded/100+BMI_description);
            System.out.println("Press any key to restart");
            scanner.nextLine();
            console.clear(times);
        }
    }
}