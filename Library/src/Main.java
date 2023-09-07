import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Clear clear = new Clear();
        Folder folder = new Folder();
        Config config = new Config();
        int times = 500;
        while(true){
            folder.create();
            folder.create1();
            clear.console(times);
            String isPINchanged = config.PIN(2);
            Scanner scanner = new Scanner(System.in);
            if(isPINchanged.equals("false")){
                System.out.println("<<<change password in admin panel!>>>"+"\n");
            }
            if(isPINchanged.equals("true")){
            }
            System.out.println("<select an option");
            System.out.println("==================================================");
            System.out.println("1-List of books");
            System.out.println("2-Rent a book");
            System.out.println("3-Admin panel");
            System.out.println("==================================================");
            int option = scanner.nextInt();
            scanner.nextLine();
            if(option>3||option<=0){
                System.out.println("<Enter number between 1-3");
                System.out.println("Press any key to restart");
                scanner.nextLine();
                clear.console(times);
                continue;
            }
            else{
                switch (option){
                    case(1) ->{
                        folder.list("Books.txt",1);
                    }
                    case(2) ->{
                        System.out.println("enter a name");
                        String userName = scanner.nextLine();
                        System.out.println("enter a last name");
                        String userLastname = scanner.nextLine();
                        System.out.println("for how long? (days)[good:7 | bad: 7days]");
                        String time = scanner.nextLine();
                        System.out.println("enter name a book which you would like to rent");
                        String bookName = scanner.nextLine();
                        boolean doHavewe = folder.doHavewe(bookName);
                        if(doHavewe){
                            int mode = folder.del_number(bookName,1,0,false);
                            if(mode==0){
                                folder.edit(bookName,1,1,false);
                                folder.edit_userRent(userName,userLastname,time,bookName);
                            }
                        }
                        else{
                            System.out.println("Book do not found");
                        }
                    }
                    case(3) ->{
                        if(isPINchanged.equals("false")){
                            System.out.println("enter PIN (1234)");
                        }
                        if(isPINchanged.equals("true")){
                            System.out.println("enter PIN");
                        }
                        String PIN = scanner.nextLine();
                        if(PIN.equals(config.PIN(1))){
                            clear.console(times);
                            System.out.println("<select an option | Admin's panel");
                            System.out.println("==================================================");
                            System.out.println("1-Admin's list of books");
                            System.out.println("2-Add a book");
                            System.out.println("3-Delete a books");
                            System.out.println("4-Delete an any number of books");
                            System.out.println("5-Manage a rented book");
                            System.out.println("6-Print a logs");
                            System.out.println("7-Change PIN");
                            System.out.println("==================================================");
                            int option2 = scanner.nextInt();
                            scanner.nextLine();
                            if(option2>7||option2<=0){
                                System.out.println("<Enter number between 1-7");
                                System.out.println("Press any key to restart");
                                scanner.nextLine();
                                clear.console(times);
                                continue;
                            }
                            else{
                                switch (option2){
                                    case(1) ->{
                                        folder.list("Books.txt",0);
                                    }
                                    case(2) ->{
                                        System.out.println("enter a book name");
                                        String bookName = scanner.nextLine();
                                        System.out.println("enter a number of books");
                                        int booksNumber = scanner.nextInt();
                                        folder.edit(bookName,booksNumber,0,true);
                                        scanner.nextLine();
                                    }
                                    case(3) ->{
                                        System.out.println("enter a book name");
                                        String bookName = scanner.nextLine();
                                        folder.del(bookName,0,1);
                                    }
                                    case(4) ->{
                                        System.out.println("enter a book name");
                                        String bookName = scanner.nextLine();
                                        System.out.println("enter a number of books to delete");
                                        int number = scanner.nextInt();
                                        scanner.nextLine();
                                        folder.del_number(bookName,number,0,true);
                                    }
                                    case(5) ->{
                                        clear.console(times);
                                        System.out.println("<select an option");
                                        System.out.println("==================================================");
                                        System.out.println("1-List of our clients");
                                        System.out.println("2-cancel a rent");
                                        System.out.println("==================================================");;
                                        int option3 = scanner.nextInt();
                                        scanner.nextLine();
                                        if(option3>2||option2<=0){
                                            System.out.println("<Enter number between 1-7");
                                            System.out.println("Press any key to restart");
                                            scanner.nextLine();
                                            clear.console(times);
                                            continue;
                                        }
                                        else{
                                            switch (option3){
                                                case(1)->{
                                                    folder.list("User_rent.txt",0);
                                                }
                                                case(2)->{
                                                    System.out.println("enter name");
                                                    String name = scanner.nextLine();
                                                    System.out.println("enter last name");
                                                    String lastName = scanner.nextLine();
                                                    System.out.println("enter book name");
                                                    String bookName = scanner.nextLine();
                                                    folder.edit(bookName,1,0,false);
                                                    int isEmpty = folder.del_number(bookName,1,1,false);
                                                    if(isEmpty==2){
                                                        folder.del(bookName,1,0);
                                                    }
                                                    folder.deleteRent(name,lastName,bookName);
                                                }
                                            }
                                        }
                                    }
                                    case(6) ->{
                                        folder.list("Logs.txt",0);
                                    }
                                    case(7) ->{
                                        System.out.println("enter new PIN");
                                        String PIN_2 = scanner.nextLine();
                                        config.change_PIN(PIN_2);
                                    }
                                }
                            }
                        }
                        else{
                            System.out.println("wrong PIN");
                        }
                    }
                }
            }
            System.out.println("Press any key to restart");
            scanner.nextLine();
            clear.console(times);
        }
    }
}