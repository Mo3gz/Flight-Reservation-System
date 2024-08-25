import java.sql.SQLException;
import java.sql.Time;
import java.util.Scanner;

public class admin {
    static Scanner input = new Scanner(System.in);
    public static void addAirCraft() throws SQLException {
        int id, registrationNum, capacity;
        String modelName;

        while(true){
            System.out.print("Enter Aircraft Id: ");
            id = input.nextInt();

            if(db.existsAircraft(id))
                System.out.println("The aircraft ID already exists");
            else
                break;
        }

        System.out.print("Enter Model Name: ");
        modelName = input.next();

        System.out.print("Enter Registration Number: ");
        registrationNum = input.nextInt();

        System.out.print("Enter Capacity: ");
        capacity = input.nextInt();

        db.addAircraft(id, modelName, registrationNum, capacity);
        System.out.println("Added successfully");
    }
    public static void updateAirCraft() throws SQLException {
        int id, registrationNum, capacity;
        String modelName;

        while(true){
            System.out.print("Enter the ID of the aircraft whose data you want to update: ");
            id = input.nextInt();

            if(!db.existsAircraft(id))
                System.out.println("The Aircraft ID not found!");
            else
                break;
        }


        System.out.print("Enter New Model Name: ");
        modelName = input.next();

        System.out.print("Enter New Registration Number: ");
        registrationNum = input.nextInt();

        System.out.print("Enter New Capacity: ");
        capacity = input.nextInt();

        db.updateAircraft(id, modelName, registrationNum, capacity);
        System.out.println("Updated successfully");
    }
    public static void addFlight() throws SQLException {
        int seatsClass1, seatsClass2, seatsClass3, aircraftId, price;
        String departureDate, arrivalDate, departureTime, arrivalTime, source, destination;

        System.out.print("Enter Source: ");
        input.nextLine();
        source = input.nextLine();

        System.out.print("Enter Destination: ");
        destination = input.nextLine();

        System.out.println("Date Example: 2023-05-21");
        System.out.print("Enter Departure Date:  ");
        departureDate = input.next();

        System.out.print("Enter Arrival Date: ");
        arrivalDate = input.next();

        System.out.println("Time Example: 12:10");
        System.out.print("Enter Departure Time: ");
        departureTime = input.next();
        departureTime += ":00";

        System.out.print("Enter Arrival Time: ");
        arrivalTime = input.next();
        arrivalTime += ":00";

        System.out.println("Enter Price: ");
        price = input.nextInt();

        while(true){
            System.out.print("Enter Aircraft Id: ");
            aircraftId = input.nextInt();

            if(db.existsAircraft(aircraftId))
                break;
            else
                System.out.println("The aircraft ID doesn't exists");
        }
        int cap = db.capacityAircraft(aircraftId) - 20;
        System.out.println("Capacity of aircraft is: " + cap);
        System.out.println("Writing available seats for each class Note: This capacity is not included in the cabin crew");
        while(true){
            System.out.print("Economy Available Seats: ");
            seatsClass1 = input.nextInt();
            System.out.print("Business Available Seats: ");
            seatsClass2 = input.nextInt();
            System.out.print("First Class Available Seats: ");
            seatsClass3 = input.nextInt();

            if(seatsClass1 + seatsClass2 + seatsClass3 <= cap)
                break;
            else
                System.out.println("Try again!");
        }
        db.addFlight(departureDate, arrivalDate, departureTime, arrivalTime, source, destination, seatsClass1, seatsClass2, seatsClass3, aircraftId, price);
    }
    public static void updateFlight() throws SQLException {
        int id, seats1, seats2, seats3, aircraftId;
        String dDate, ADate,source, destination,dTime , aTime;

        while(true){
            System.out.print("Enter the ID of the flight whose data you want to update: ");
            id = input.nextInt();

            if(!db.existsFlight(id))
                System.out.println("The Flight ID not found!");
            else
                break;
        }

        System.out.print("Enter New Source: ");
        input.nextLine();
        source = input.nextLine();

        System.out.print("Enter New Destination: ");
        destination = input.nextLine();

        System.out.println("Example date: 2023-05-23");
        System.out.print("Enter New Departure Date: ");
        dDate = input.next();

        System.out.print("Enter New Arrival Date: ");
        ADate = input.next();


        System.out.println("Example Time: 12:10");
        System.out.print("Enter New Departure Time: ");
        dTime = input.next();
        dTime += ":00";

        System.out.print("Enter New Arrival Time: ");
        aTime = input.next();
        aTime += ":00";

        System.out.print("Enter New Number of Economy Seats: ");
        seats1 = input.nextInt();

        System.out.print("Enter New Number of Business Seats: ");
        seats2 = input.nextInt();

        System.out.print("Enter New Number of First Class Seats: ");
        seats3 = input.nextInt();

        System.out.print("Enter New Aircraft ID: ");
        aircraftId = input.nextInt();

        db.UpdateFlight(id, dDate, ADate, source, destination, seats1, seats2, seats3, aircraftId, dTime, aTime);
    }
    public static void viewPassengers() throws SQLException {
        int flightId;
        while(true){
            System.out.print("Enter Flight ID: ");
            flightId = input.nextInt();

            if(db.existsFlight(flightId))
                break;
            else
                System.out.println("The Flight ID doesn't exists");
        }
        db.viewPassengers(flightId);
    }
    public static void adminMenu() throws SQLException {
        int choice = 0;
        while (choice != 9){
            System.out.println("----Admin Menu----");
            System.out.println("1. Add Aircraft");
            System.out.println("2. Update Aircraft");
            System.out.println("3. Add Flight");
            System.out.println("4. Update Flight");
            System.out.println("5. View Flights");
            System.out.println("6. View Passengers");
            System.out.println("7. Generate Reports");
            System.out.println("8. Sign Up Admin");
            System.out.println("9. Log out");
            System.out.print("Enter Your Choice: ");
            choice = input.nextInt();

            switch (choice) {
                case 1:
                    addAirCraft();
                    break;
                case 2:
                    updateAirCraft();
                    break;
                case 3:
                    addFlight();
                    break;
                case 4:
                    updateFlight();
                    break;
                case 5:
                    db.viewAllFlights();
                    break;
                case 6:
                    viewPassengers();
                    break;
                case 7:
                    db.report();
                    break;
                case 8:
                    systemControl.signUp(1);
                    break;
                case 9:
                    new Main();
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

}
