import java.sql.SQLException;
import java.util.Scanner;

public class customer {
    static Scanner input = new Scanner(System.in);
    public static void customerMenu() throws SQLException {
        int choice = 0;
        while(choice != 5){
            System.out.println("----Customer Menu----");
            System.out.println("1. Update Info");
            System.out.println("2. Find and Book Flights");
            System.out.println("3. Cancel Flight");
            System.out.println("4. Change Flight Class");
            System.out.println("5. Log out");
            System.out.print("Enter Your Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    update_info();
                    break;
                case 2:
                    flightMenu();
                    break;
                case 3:
                    cancelFlight();
                    break;
                case 4:
                    changeFlightClass();
                    break;
                case 5:
                    System.out.println("Logged out successfully!\n");
                    System.out.println("Do you want to terminate the program?");
                    System.out.println("1. Yes");
                    System.out.println("2. No");
                    System.out.print("Enter Your Choice: ");
                    int choice2 = input.nextInt();
                    if(choice2 == 1){
                        System.exit(0);
                    }
                    else if(choice2 == 2){
                        systemControl main = new systemControl();
                        main.mainMenu();
                    }
                    else{
                        System.out.println("Invalid choice!");
                    }

                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

    }
    public static void update_info() throws SQLException {
        System.out.print("Username: ");
        String username = input.next();

        System.out.print("Password: ");
        String password = input.next();

        System.out.print("Email: ");
        String email = input.next();

        System.out.print("Phone Number: ");
        String phoneNumber = input.next();

        db.updateUser(username, password, email, phoneNumber);
    }
    public static void findFlights() throws SQLException {
        System.out.println("Write the information of the desired flight");

        System.out.print("Enter Source: ");
        input.nextLine();
        String source = input.nextLine();

        System.out.print("Enter Destination: ");
        String destination = input.nextLine();

        System.out.println("Example date: 2023-05-23");
        System.out.print("Date: ");
        String date = input.next();


        db.findFlights(source, destination, date);
    }
    public static void bookFlight() throws SQLException {
        int id;
        while (true) {
            System.out.print("Enter Flight ID that you want to book: ");
            id = Integer.parseInt(input.next());

            if (db.existsFlight(id))
                break;

            else
                System.out.println("The Flight ID doesn't exists");
        }

        int price = db.getPrice(id);
        System.out.println("1- Economy Class, Price " + price);
        System.out.println("2- Business Class, Price " + (price * 1.2));
        System.out.println("3- First Class, Price " + (price * 1.4));
        System.out.print("Flight Class: ");
        int flightClass = Integer.parseInt(input.next());
        db.bookFlight(id, flightClass, price);
        payment(id);
    }
    public static void payment(int flightID) throws SQLException {
        int cvv;
        String cardNum,expDate;
        System.out.print("Enter your Credit Card number: ");
        cardNum = input.next();
        System.out.print("Enter your CVV: ");
        cvv = input.nextInt();
        System.out.println("Example for Date: 2023-05");
        System.out.print("Enter your Expiry Date: ");
        expDate = input.next();
        expDate += "-01";

        db.Payment(flightID,cardNum,expDate,cvv);
        System.out.println("Payment Successful");
    }
    public static void cancelFlight() throws SQLException {
        if(!db.viewFlight()){
            System.out.println("You dont have bookings to cancel flight");
            return;
        }
        System.out.println("Write the information of the flight you want to cancel");
        System.out.print("Booking ID: ");
        int BookingID = Integer.parseInt(input.next());
        db.cancelFlight(BookingID);
    }
    public static void changeFlightClass() throws SQLException {
        if(!db.viewFlight()){
            System.out.println("You dont have bookings to change flight class");
            return;
        }

        System.out.println("Write th booking ID :");
        int bookingID = Integer.parseInt(input.next());
        System.out.println("1- Economy Class");
        System.out.println("2- Business Class");
        System.out.println("3- First Class");
        System.out.print("Flight Class: ");
        int flightClass = Integer.parseInt(input.next());
        db.ChangeClass(bookingID, flightClass);
    }
    public static void flightMenu() throws SQLException {
        int choice = 0;
        while (choice != 4) {
            System.out.println("1- View All Flights");
            System.out.println("2- Find Flights");
            System.out.println("3- Book Flight");
            System.out.println("4- return to menu");
            System.out.print("Enter Your Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    db.viewAllFlights();
                    break;
                case 2:
                    findFlights();
                    break;
                case 3:
                    bookFlight();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }
}