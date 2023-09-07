import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int times = 1000;
        while(true){
            Clear clear = new Clear();
            clear.console(times);
            String numbers;
            String[] splited_numbers;
            int int_numbers=0;
            String number;
            System.out.println("<<<<Select option>>>>");
            System.out.println("1--<<<<<Addition>>>>>");
            System.out.println("2-<<<<Subtraction>>>>");
            System.out.println("3--<<Multiplication>>");
            System.out.println("4--<<<<<Division>>>>>");
            System.out.println("5--<<<<<<Powers>>>>>>");
            System.out.println("6--<<<<<Elements>>>>>");
            System.out.println("7--<<<<Logarithms>>>>");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch(choice){
                case (1)->{
                    System.out.println("Type numbers to addition");
                    System.out.println("np. (\"1,2,3\")");
                    numbers = scanner.nextLine();
                    splited_numbers=numbers.split(",");
                    for(String i:splited_numbers){
                        int_numbers=int_numbers+Integer.parseInt(i);
                    }
                    System.out.println(int_numbers);
                }
                case (2)->{
                    System.out.println("Type numbers to subtraction");
                    System.out.println("np. (\"1,2,3\")");
                    numbers = scanner.nextLine();
                    splited_numbers=numbers.split(",");
                    int_numbers = Integer.parseInt(splited_numbers[0]);
                    int_numbers = int_numbers*2;
                    for(String i:splited_numbers){
                        int_numbers=int_numbers-Integer.parseInt(i);
                    }
                    System.out.println(int_numbers);
                }
                case (3)->{
                    System.out.println("Type numbers to multiplication");
                    System.out.println("np. (\"1,2,3\")");
                    numbers = scanner.nextLine();
                    splited_numbers=numbers.split(",");
                    int_numbers = 1;
                    for(String i:splited_numbers){
                        int_numbers=int_numbers*Integer.parseInt(i);
                    }
                    System.out.println(int_numbers);
                }
                case (4)->{
                    System.out.println("Type numbers to division");
                    System.out.println("np. (\"1,2,3\")");
                    numbers = scanner.nextLine();
                    splited_numbers=numbers.split(",");
                    double double_numbers = Double.parseDouble(splited_numbers[0])*Double.parseDouble(splited_numbers[0]);
//                    System.out.println(double_numbers);
                    for(String i:splited_numbers){
                        double_numbers=double_numbers/Double.parseDouble(i);
                    }
                    System.out.println(double_numbers);
                }
                case (5)->{
                    System.out.println("Type base of power");
                    number = scanner.nextLine();
                    double double_number = Double.parseDouble(number);
                    System.out.println("Type exponents");
                    System.out.println("np. (\"1,2,3\")");
                    numbers = scanner.nextLine();
                    splited_numbers=numbers.split(",");
                    for(String i:splited_numbers){
                        double double_i = Double.parseDouble(i);
                        double_number=Math.pow(double_number,double_i);
                    }
                    System.out.println(double_number);
                }
                case (6)->{
                    System.out.println("Type sub-root number");
                    number = scanner.nextLine();
                    double double_number = Double.parseDouble(number);
                    System.out.println("Type the degree of the root");
                    numbers = scanner.nextLine();
                    splited_numbers=numbers.split(",");
                    for(String i:splited_numbers){
                        double double_i = Double.parseDouble(i);
                        double_number=Math.pow(double_number,1/double_i);
                    }
                    System.out.println(double_number);
                }
                case (7)->{
                    System.out.println("Type the base of the logarithm");
                    number = scanner.nextLine();
                    double double_number = Double.parseDouble(number);
                    System.out.println("Type logarithm number");
                    numbers = scanner.nextLine();
                    double double_numbers = Double.parseDouble(numbers);
                    double_number = Math.log(double_numbers)/Math.log(double_number);
                    System.out.println(double_number);
                }
            }
            if(choice>7){
                System.out.println("Enter number between 1-7");
            }
            System.out.println("Press any key to restart");
            scanner.nextLine();
            clear.console(times);
        }
    }
}