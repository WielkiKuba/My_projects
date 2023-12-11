import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        String sqlUrl = "jdbc:mysql://x/Library";
        String sqlPassword = "x";
        String sqlLogin = "x";

        try {

            Connection connection = DriverManager.getConnection(sqlUrl,sqlLogin,sqlPassword);

            while(true){
                int userId = 0,bookId = 0, time2 = 0,optionInt = 0,optionInt2 = 0,timeInt =0,numberOfbooks = 0,numberInt = 0,quantityInt = 0,times = 100;
                String query = "",name = "",surname = "",bookName = "",query2 = "";
                Console console = new Console();
                Scanner scanner = new Scanner(System.in);
                console.clear(times);
                System.out.println("Enter an option");
                System.out.println("===========================");
                System.out.println("1-Users management");
                System.out.println("2-Books management");
                System.out.println("3-Rents management");
                System.out.println("4-Show all books");
                System.out.println("===========================");
                String option = scanner.nextLine();
                console.clear(times);
                try{
                    optionInt = Integer.parseInt(option);
                }catch (Exception e){
                    System.out.println("Enter a number between 1-3");
                    break;
                }
                switch (optionInt){
                    case (1)->{
                        System.out.println("Enter an option");
                        System.out.println("===========================");
                        System.out.println("1-Create user");
                        System.out.println("2-Delete user");
                        System.out.println("===========================");
                        String option2 = scanner.nextLine();
                        System.out.println("Enter a name of user");
                        name = (scanner.nextLine()).toUpperCase();
                        String[] parts = name.split(" ");
                        if(!(parts.length==1)){
                            System.out.println("ONLY NAME");
                            break;
                        }
                        System.out.println("Enter a surname of user");
                        System.out.println("(If you have two-part surname, enter like here:surname1-surname2)");
                        String[] parts2 = surname.split(" ");
                        if(!(parts2.length==1)){
                            System.out.println("ONLY NAME");
                            break;
                        }
                        surname = (scanner.nextLine()).toUpperCase();
                        if(option2.equals("1")){
                            query = "INSERT INTO Users(name,surname) VALUES(?,?);";
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                        } else if (option2.equals("2")) {
                            query = "DELETE FROM Users WHERE name = ? AND surname = ?;";
                        }
                        else{
                            System.out.println("Enter a number between 1-2");
                        }
                        if(name.isEmpty()||surname.isEmpty()){
                            System.out.println("Name or surname can not be empty");
                            break;
                        }
                        try{
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1,name);
                            preparedStatement.setString(2,surname);
                            preparedStatement.executeUpdate();
                        }catch (SQLException e){}
                    }
                    case (2)->{
                        int arguments = 2;
                        System.out.println("Enter an option");
                        System.out.println("===========================");
                        System.out.println("1-Add a NEW book");
                        System.out.println("2-Add/delete a specific amount of book");
                        System.out.println("3-Delete a SERIES of books");
                        System.out.println("===========================");
                        String option2 = scanner.nextLine();
                        try{
                            optionInt2 = Integer.parseInt(option2);
                        }catch (Exception e){
                            System.out.println("Enter a number between 1-4");
                            break;
                        }
                        System.out.println("Enter a name of book");
                        bookName = (scanner.nextLine()).toUpperCase();
                        String quantity = "0";
                        switch (optionInt2){
                            case (1)->{
                                query = "INSERT INTO Books(bookName,quantity) VALUES (?,?)";
                                System.out.println("Enter a number of books");
                                quantity = scanner.nextLine();
                            }
                            case (2)->{
                                System.out.println("Enter a number of books to add");
                                Statement statement = connection.createStatement();
                                ResultSet resultSet = statement.executeQuery("SELECT quantity FROM Books WHERE bookName = '"+bookName+"';");
                                while(resultSet.next()){
                                    numberOfbooks = resultSet.getInt(1);
                                }
                                System.out.println("===========================");
                                System.out.println("Enter number of books to add/delete");
                                System.out.println("e.g \"2\" for plus 2 and \"-2\" for minus 2 (Good:\"-2\"|Bad:\"- 2\")");
                                System.out.println("===========================");
                                String number = scanner.nextLine();
                                try{
                                    numberInt = Integer.parseInt(number);
                                    numberOfbooks = numberOfbooks + numberInt;
                                }catch (Exception e){
                                    System.out.println("Enter a number");
                                    break;
                                }
                                statement.close();
                                if(numberOfbooks<0){
                                    System.out.println("There's no enough to delete "+numberInt+" books");
                                    break;
                                }
                                query = "UPDATE Books SET quantity = "+numberOfbooks+" WHERE bookName = '"+bookName+"';";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.executeUpdate();
                                query = "";
                            }
                            case (3)->{
                                query = "DELETE FROM Books WHERE bookName = ?";
                                arguments = 1;
                            }
                        }
                        if(optionInt2<1 || optionInt2>3){
                            System.out.println("Enter a number between 1-3");
                        }
                        try{
                            quantityInt = Integer.parseInt(quantity);
                        }catch (Exception e){
                            System.out.println("Enter a number");
                            break;
                        }
                        if(!query.isEmpty()){
                            PreparedStatement preparedStatement = connection.prepareStatement(query);
                            preparedStatement.setString(1,bookName);
                            if(arguments==2){
                                preparedStatement.setInt(2,quantityInt);
                            }
                            preparedStatement.executeUpdate();
                        }
                    }
                    case (3)->{
                        System.out.println("Enter an option");
                        System.out.println("===========================");
                        System.out.println("1-Create a new rent");
                        System.out.println("2-Delete a rent");
                        System.out.println("3-Show all rents");
                        System.out.println("===========================");
                        String option2 = scanner.nextLine();
                        try{
                            optionInt2 = Integer.parseInt(option2);
                        }catch (Exception e){
                            System.out.println("Enter a number between 1-2");
                            break;
                        }
                        switch (optionInt2){
                            case (1)->{
                                int bookQuantity = 0;
                                System.out.println("Enter name");
                                name = (scanner.nextLine()).toUpperCase();
                                System.out.println("Enter surname");
                                surname = (scanner.nextLine()).toUpperCase();
                                System.out.println("Enter name of book");
                                bookName = (scanner.nextLine()).toUpperCase();
                                System.out.println("Enter a time(days) of rent");
                                String time = scanner.nextLine();
                                try{
                                    timeInt = Integer.parseInt(time);
                                }catch (Exception e){
                                    System.out.println("Enter a number");
                                    break;
                                }
                                Statement statement = connection.createStatement();
                                ResultSet resultSet = statement.executeQuery("SELECT id FROM Users WHERE name = '"+name+"' AND surname = '"+surname+"';");
                                while(resultSet.next()){
                                    userId = resultSet.getInt(1);
                                }
                                if(userId==0){
                                    System.out.println("User not found");
                                    break;
                                }
                                ResultSet resultSet1 = statement.executeQuery("SELECT id,quantity FROM Books WHERE bookName = '"+bookName+"';");
                                while (resultSet1.next()){
                                    bookId = resultSet1.getInt("id");
                                    bookQuantity = resultSet1.getInt("quantity");
                                }
                                if(bookQuantity-1<0){
                                    System.out.println("There's no this book in storage");
                                    break;
                                }
                                statement.close();
                                query = "INSERT INTO Rents(who,what,time) VALUES (?,?,?);";
                                PreparedStatement preparedStatement = connection.prepareStatement(query);
                                preparedStatement.setInt(1,userId);
                                preparedStatement.setInt(2,bookId);
                                preparedStatement.setInt(3,timeInt);
                                preparedStatement.executeUpdate();
                                preparedStatement.close();
                                bookQuantity = bookQuantity - 1;
                                query = "UPDATE Books SET quantity = "+bookQuantity+" WHERE bookName = '"+bookName+"';";
                                PreparedStatement preparedStatement1 = connection.prepareStatement(query);
                                preparedStatement1.executeUpdate();
                            }
                            case (2)->{
                                int bookQuantity = 0,rentId = 0;
                                System.out.println("Enter name");
                                name = (scanner.nextLine()).toUpperCase();
                                System.out.println("Enter surname");
                                surname = (scanner.nextLine()).toUpperCase();
                                System.out.println("Enter name of book");
                                bookName = (scanner.nextLine()).toUpperCase();
                                Statement statement = connection.createStatement();
                                ResultSet resultSet = statement.executeQuery("SELECT id FROM Users WHERE name = '"+name+"' AND surname = '"+surname+"';");
                                while(resultSet.next()){
                                    userId = resultSet.getInt(1);
                                }
                                ResultSet resultSet1 = statement.executeQuery("SELECT id,quantity FROM Books WHERE bookName = '"+bookName+"';");
                                while (resultSet1.next()){
                                    bookId = resultSet1.getInt("id");
                                    bookQuantity = resultSet1.getInt("quantity");
                                }
                                statement.close();
                                query = "SELECT id FROM Rents WHERE who = "+userId+" AND what = "+bookId+";";
                                Statement statement1 = connection.createStatement();
                                ResultSet resultSet2 = statement1.executeQuery(query);
                                while (resultSet2.next()){
                                    rentId =  resultSet2.getInt("id");
                                }
                                statement1.close();
                                bookQuantity = bookQuantity + 1;
                                query = "UPDATE Books SET quantity = "+bookQuantity+" WHERE bookName = '"+bookName+"';";
                                PreparedStatement preparedStatement1 = connection.prepareStatement(query);
                                preparedStatement1.executeUpdate();
                                statement1.close();
                                Statement statement2 = connection.createStatement();
                                query = "DELETE FROM Rents WHERE id = "+rentId+";";
                                statement2.executeUpdate(query);
                            }
                            case (3)->{
                                Statement statement = connection.createStatement();
                                query = "SELECT * FROM Rents;";
                                ResultSet resultSet = statement.executeQuery(query);
                                while (resultSet.next()){
                                    userId = resultSet.getInt("who");
                                    bookId = resultSet.getInt("what");
                                    time2 = resultSet.getInt("time");
                                    Statement statement1 = connection.createStatement();
                                    query2 = "SELECT name,surname FROM Users WHERE id = "+userId+";";
                                    ResultSet resultSet1 = statement1.executeQuery(query2);
                                    while (resultSet1.next()){
                                        name = resultSet1.getString("name");
                                        surname = resultSet1.getString("surname");
                                    }
                                    query2 = "SELECT bookName FROM Books WHERE id = "+bookId+";";
                                    ResultSet resultSet2 = statement1.executeQuery(query2);
                                    while (resultSet2.next()){
                                        bookName = resultSet2.getString(1);
                                    }
                                    System.out.println("["+name+" "+surname+" Rented a "+bookName+" for "+time2+" days]");
                                }
                            }
                        }
                        if(optionInt2>3 || optionInt2<1){
                            System.out.println("Enter a number between 1-3");
                        }
                    }
                    case (4)->{
                        query = "SELECT bookName,quantity FROM Books;";
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery(query);
                        while(resultSet.next()){
                            System.out.println("===========================");
                            String SQLbookName = resultSet.getString(1);
                            int SQLquantity = resultSet.getInt(2);
                            System.out.println(SQLbookName+", "+SQLquantity);
                        }
                        System.out.println("===========================");
                    }
                }
                if(optionInt<1 || optionInt>4){
                    System.out.println("Enter a number between 1-4");
                }
                System.out.println("===========================");
                System.out.println("Enter any key to restart");
                System.out.println("===========================");
                String x = scanner.nextLine();
            }
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println("ERROR 101");
            System.out.println("Connection with SQL has failed");
        }
    }
}
