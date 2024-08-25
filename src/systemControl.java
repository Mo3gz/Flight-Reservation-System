import java.sql.SQLException;
import java.util.Scanner;

public class systemControl {
    static Scanner input = new Scanner(System.in);

    public static void signIn() throws SQLException {
        System.out.print("Username: ");
        String username = input.next();

        System.out.print("Password: ");
        String password = input.next();

        if(!db.login(username, password)){
            System.out.println("Wrong Inputs");
            signIn();
        }

    }
    public static void signUp(int type) throws SQLException {
        String username;
        while (true){
            System.out.print("Username: ");
            username = input.next();
            if(!db.userExists(username))
                break;
            System.out.println("The username you entered is already taken. Please try another username.");
        }

        System.out.print("Password: ");
        String password = input.next();

        System.out.print("Email: ");
        String email = input.next();

        System.out.print("Phone Number: ");
        String phoneNumber = input.next();

        db.addUser(username, password, email, phoneNumber, type);
    }
    public void mainMenu() throws SQLException {
        db.setConnection();
        int choice = 0;
        while (choice != 3){
            System.out.println("Welcome to the Main Menu!");

            System.out.println("Please select an option:");
            System.out.println("1. Sign In");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");
            System.out.print("Enter Your Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    signIn();
                    break;
                case 2:
                    signUp(0);
                    break;
                case 3:
                    System.out.println("Good Bye!");
                    break;
                default:
                    System.out.println("Invalid choice");
                    break;
            }
        }
    }

}
