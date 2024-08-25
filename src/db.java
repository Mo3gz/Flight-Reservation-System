import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class db {
    private static Connection connection;
    protected static String username;
    public static void setConnection(){
        try {
            String currentDir = java.lang.System.getProperty("user.dir");
            String url = "jdbc:sqlite:" + currentDir + "\\db\\mydatabase.db";
            connection = getConnection(url);

        } catch (SQLException e) {
            System.out.println("Error connecting to database");
            e.printStackTrace();
        }
    }
    public static boolean existsAircraft(int id) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Aircrafts WHERE Aircraft_Id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        if (rs.next())
            count = rs.getInt("count");
        return count == 1;
    }
    public static void addAircraft(int id, String modelName, int registrationNumber, int capacity) throws SQLException {
        String sql = "INSERT INTO Aircrafts(Aircraft_Id, Model_Name, Registration_number, Capacity) VALUES (?, ?, ?, ?); ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.setString(2, modelName);
        statement.setInt(3, registrationNumber);
        statement.setInt(4, capacity);
        statement.executeUpdate();

        statement.close();
    }
    public static void updateAircraft(int id, String modelName, int registrationNumber, int capacity) throws SQLException {
        String sql = "UPDATE Aircrafts SET Model_Name = ?, Registration_number = ?,  Capacity = ? WHERE Aircraft_Id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, modelName);
        statement.setInt(2, registrationNumber);
        statement.setInt(3, capacity);
        statement.setInt(4, id);
        statement.executeUpdate();

        statement.close();
    }
    public static boolean existsFlight(int id) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM Flights WHERE Flight_Id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        if (rs.next())
            count = rs.getInt("count");
        return count == 1;
    }
    public static void addFlight(String departureDate, String arrivalDate, String departureTime, String arrivalTime, String source, String destination, int seatsClass1, int seatsClass2, int seatsClass3, int aircraftId, int price) throws SQLException {
        String sql = "INSERT INTO Flights (Departure_Date, Arrival_Date, Flight_Source, Flight_Destination, Aircraft_Id, Time_of_Departure, Time_of_Arrival, Available_Seats_1, Available_Seats_2, Available_Seats_3, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, departureDate);
        statement.setString(2, arrivalDate);
        statement.setString(3, source);
        statement.setString(4, destination);
        statement.setInt(5, aircraftId);
        statement.setString(6, departureTime);
        statement.setString(7, arrivalTime);
        statement.setInt(8, seatsClass1);
        statement.setInt(9, seatsClass2);
        statement.setInt(10, seatsClass3);
        statement.setInt(11, price);
        statement.executeUpdate();
        statement.close();
    }
    public static boolean userExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) AS count FROM users WHERE username = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        int count = 0;
        if (rs.next())
            count = rs.getInt("count");
        return count == 1;
    }
    public static boolean login(String un, String password) throws SQLException{
        username = un;
        String sql = "SELECT user_type FROM users WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, un);
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        int count = 2;
        if (rs.next())
            count = rs.getInt("user_type");

        if (count == 1){
            System.out.println("Welcome, " + un + ".");
            admin.adminMenu();
            return true;
        }else if(count == 0){
            System.out.println("Welcome, " + un + ".");
            customer.customerMenu();
            return true;
        }

        return false;
    }
    public static void addUser(String userName, String Password, String userEmail, String phone, int type) throws SQLException {
        String sql = "INSERT INTO users (username,password ,email, phone_number, user_type) VALUES ( ?, ?, ?, ?, ?)";
        PreparedStatement statement2 = connection.prepareStatement(sql);
        statement2.setString(1,userName);
        statement2.setString(2,Password);
        statement2.setString(3,userEmail);
        statement2.setString(4,phone);
        statement2.setInt(5,type);
        statement2.executeUpdate();
    }
    public static void updateUser(String userName, String Password, String userEmail, String phone) throws SQLException {
        String sql = "UPDATE users SET password = ?, email = ?, phone_number = ? WHERE username = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, Password);
        statement.setString(2, userEmail);
        statement.setString(3, phone);
        statement.setString(4, userName);
        statement.executeUpdate();
    }
    public static void findFlights(String source, String destination, String date) throws SQLException {
        String sql = "SELECT * FROM Flights WHERE Flight_Source = ? AND Flight_Destination = ? AND Departure_Date = ? ";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, source);
        statement.setString(2, destination);
        statement.setString(3, date);
        ResultSet rs = statement.executeQuery();


        while (rs.next()) {
            int id = rs.getInt("Flight_Id");
            String source1 = rs.getString("Flight_Source");
            String destination1 = rs.getString("Flight_Destination");
            String depDate = rs.getString("Departure_Date");
            String arrivalDate = rs.getString("Arrival_Date");
            String depTime = rs.getString("Time_of_Departure");
            String arrTime = rs.getString("Time_of_Arrival");
            int availableSeats1 = rs.getInt("Available_Seats_1");
            int availableSeats2 = rs.getInt("Available_Seats_2");
            int availableSeats3 = rs.getInt("Available_Seats_3");
            int price = rs.getInt("price");

            System.out.println("Flight ID: " + id);
            System.out.println("Departure Date: " + depDate);
            System.out.println("Arrival Date: " + arrivalDate);
            System.out.println("Flight Source: " + source1);
            System.out.println("Flight Destination: " + destination1);
            System.out.println("Time of Departure: " + depTime);
            System.out.println("Time of Arrival: " + arrTime);
            System.out.println("Available Seats for Economy: " + availableSeats1);
            System.out.println("Available Seats for Business: " + availableSeats2);
            System.out.println("Available Seats for First Class: " + availableSeats3);
            System.out.println("Price (Economy): " + price);
            System.out.println("Price (Business): " + (price * 1.2));
            System.out.println("Price (First Class): " + (price * 1.4));
            System.out.println();
        }
    }
    public static int capacityAircraft(int id) throws SQLException {
        String sql = "SELECT Capacity FROM Aircrafts WHERE Aircraft_Id = ?;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();
        return rs.getInt("Capacity");
    }
    public static void viewPassengers(int flightId) throws SQLException {
        String sql = "SELECT b.Booking_Id, b.UserName, b.Class_Id, b.SeatNumber, f.Departure_Date, f.Arrival_Date, f.Flight_Source, f.Flight_Destination, f.Aircraft_Id, f.Time_of_Departure, f.Time_of_Arrival, f.Price "
                + "FROM Bookings b "
                + "JOIN Flights f ON b.Flight_Id = f.Flight_Id "
                + "WHERE b.Flight_Id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, flightId);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Details Of Flight: ");
        String departureDate = resultSet.getString("Departure_Date");
        String arrivalDate = resultSet.getString("Arrival_Date");
        String flightSource = resultSet.getString("Flight_Source");
        String flightDestination = resultSet.getString("Flight_Destination");
        int aircraftId = resultSet.getInt("Aircraft_Id");
        String timeOfDeparture = resultSet.getString("Time_of_Departure");
        String timeOfArrival = resultSet.getString("Time_of_Arrival");
        int price = resultSet.getInt("Price");

        System.out.println("Departure Date: " + departureDate);
        System.out.println("Arrival Date: " + arrivalDate);
        System.out.println("Flight Source: " + flightSource);
        System.out.println("Flight Destination: " + flightDestination);
        System.out.println("Aircraft ID: " + aircraftId);
        System.out.println("Time of Departure: " + timeOfDeparture);
        System.out.println("Time of Arrival: " + timeOfArrival);
        System.out.println("Price of Economy Class: " + price);
        System.out.println("Price of Business Class: " + (price * 1.2));
        System.out.println("Price of First Class: " + (price * 1.4));

        System.out.println();

        System.out.println("Passengers on the Flight: ");
        System.out.println("-------------------------------------");
        System.out.printf("| %-12s | %-20s | %-9s | %-11s |\n", "Booking ID", "User Name", "Class ID", "Seat Number");
        System.out.println("-------------------------------------");

        while (resultSet.next()) {
            int bookingId = resultSet.getInt("Booking_Id");
            String userName = resultSet.getString("UserName");
            int classId = resultSet.getInt("Class_Id");
            int seatNumber = resultSet.getInt("SeatNumber");

            System.out.printf("| %-12d | %-20s | %-9d | %-11d |\n", bookingId, userName, classId, seatNumber);
        }

        System.out.println("-------------------------------------");

        // Close the ResultSet, PreparedStatement, and Connection
        resultSet.close();
        statement.close();
    }
    public static void bookFlight(int id, int ClassID, int price) throws SQLException {
        int seatNumber = 0;
        if (ClassID == 1){
            String sql2 = "SELECT Available_Seats_1 from Flights WHERE Flight_Id = ?;";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, id);
            ResultSet rs = statement2.executeQuery();
            seatNumber = rs.getInt("Available_Seats_1");


            String sql = "UPDATE Flights SET Available_Seats_1 = Available_Seats_1 - 1 WHERE Flight_Id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            statement.close();
            statement2.close();
        }
        else if (ClassID == 2){
            String sql2 = "SELECT Available_Seats_2 from Flights WHERE Flight_Id = ?;";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, id);
            ResultSet rs = statement2.executeQuery();
            seatNumber = rs.getInt("Available_Seats_2");

            String sql = "UPDATE Flights SET Available_Seats_2 = Available_Seats_2 - 1 WHERE Flight_Id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            statement.close();
            statement2.close();
        }
        else if (ClassID == 3){
            String sql2 = "SELECT Available_Seats_3 from Flights WHERE Flight_Id = ?;";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, id);
            ResultSet rs = statement2.executeQuery();
            seatNumber = rs.getInt("Available_Seats_3");

            String sql = "UPDATE Flights SET Available_Seats_3 = Available_Seats_3 - 1 WHERE Flight_Id = ?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();

            statement.close();
            statement2.close();
        }

        String sql2 = "INSERT INTO Bookings(Flight_Id, UserName, Class_Id, SeatNumber, price) VALUES (?, ?, ?, ?, ?); ";
        PreparedStatement statement2 = connection.prepareStatement(sql2);
        statement2.setInt(1, id);
        statement2.setString(2, username);
        statement2.setInt(3, ClassID);
        statement2.setInt(4, seatNumber);
        statement2.setInt(5, price);
        statement2.executeUpdate();

        String sql3 = "INSERT INTO User_Flights(UserName, Flight_Id)  VALUES (?, ?);";
        PreparedStatement statement3 = connection.prepareStatement(sql3);
        statement3.setString(1, username);
        statement3.setInt(2, id);


        statement2.close();
        statement3.close();
    }
    public static void Payment(int flightID,String cardNum,String expire,int CVV) throws SQLException {
        String sql = "Select Booking_Id from Bookings where Flight_ID = ? And UserName = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,flightID);
        statement.setString(2,username);

        ResultSet rs = statement.executeQuery();
        int BookingID = rs.getInt("Booking_Id");



        String sql1 = "INSERT INTO Payments (Booking_ID, Credit_Card_Number, UserName, Expire_Date, CVV) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement1 = connection.prepareStatement(sql1);
        statement1.setInt(1, BookingID);
        statement1.setString(2, cardNum);
        statement1.setString(3, username);
        statement1.setString(4, expire);
        statement1.setInt(5, CVV);


        statement1.executeUpdate();


    }
    public static void cancelFlight(int BookingID) throws SQLException {
            String sql = "DELETE FROM Bookings WHERE Booking_Id = ? AND UserName = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, BookingID);
            statement.setString(2, username);
            statement.executeUpdate();

            String sql2 = "SELECT Class_Id, Flight_Id FROM Bookings WHERE Booking_Id = ? AND UserName = ?";
            PreparedStatement statement2 = connection.prepareStatement(sql2);
            statement2.setInt(1, BookingID);
            statement2.setString(2, username);
            ResultSet rs = statement2.executeQuery();
            int classID = rs.getInt("Class_Id");
            int FlightID = rs.getInt("Flight_Id");

            if(classID == 1) {
                String sql4 = "UPDATE Flights SET Available_Seats_1 = Available_Seats_1 + 1 WHERE Flight_Id = ?";
                PreparedStatement statement4 = connection.prepareStatement(sql4);
                statement4.setInt(1,FlightID );
                statement4.executeUpdate();
            }
            else if(classID == 2) {
                String sql4 = "UPDATE Flights SET Available_Seats_2 = Available_Seats_2 + 1 WHERE Flight_Id = ?";
                PreparedStatement statement4 = connection.prepareStatement(sql4);
                statement4.setInt(1, FlightID);
                statement4.executeUpdate();
            }
            else if(classID == 3) {
                String sql4 = "UPDATE Flights SET Available_Seats_3 = Available_Seats_3 + 1 WHERE Flight_Id = ?";
                PreparedStatement statement4 = connection.prepareStatement(sql4);
                statement4.setInt(1, FlightID);
                statement4.executeUpdate();
            }

            String sql3 = "DELETE FROM User_Flights WHERE Flight_Id = ? AND UserName = ?;";
            PreparedStatement statement3 = connection.prepareStatement(sql3);
            statement3.setInt(1, FlightID);
            statement3.setString(2, username);
            statement3.executeUpdate();
    }
    public static void ChangeClass(int BookingID, int classID){
        try{
            String sql = "UPDATE Bookings SET Class_Id = ? WHERE Booking_Id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, classID);
            statement.setInt(2, BookingID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error changing flight class");
            e.printStackTrace();
        }
    }
    public static void viewAllFlights() {
        try {
            String sql = "SELECT * FROM Flights;";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            System.out.println("---------------------------------------------------Flights-----------------------------------------------------------------");
            System.out.printf("| %-9s | %-15s | %-18s | %-15s | %-19s | %-15s | %-15s | %-23s | %-25s | %-25s | %-9s |\n",
                    "Flight ID", "Departure Date", "Arrival Date", "Flight Source", "Flight Destination",
                    "Departure Time", "Arrival Time", "Available Seats (Economy)", "Available Seats (Business)",
                    "Available Seats (First Class)", "Price (Economy) ");
            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("Flight_Id");
                String source = rs.getString("Flight_Source");
                String destination = rs.getString("Flight_Destination");
                String depDate = rs.getString("Departure_Date");
                String arrivalDate = rs.getString("Arrival_Date");
                String depTime = rs.getString("Time_of_Departure");
                String arrTime = rs.getString("Time_of_Arrival");
                int availableSeats1 = rs.getInt("Available_Seats_1");
                int availableSeats2 = rs.getInt("Available_Seats_2");
                int availableSeats3 = rs.getInt("Available_Seats_3");
                int price = rs.getInt("price");

                System.out.printf("| %-9d | %-15s | %-18s | %-15s | %-19s | %-15s | %-15s | %-23d | %-25d | %-25d | $%-7d |\n",
                        id, depDate, arrivalDate, source, destination, depTime, arrTime,
                        availableSeats1, availableSeats2, availableSeats3, price);
            }

            System.out.println("-------------------------------------------------------------------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("Error finding flights");
            e.printStackTrace();
        }
    }
    public static void viewAllAircraft(){
        try {

            Statement statement = connection.createStatement();

            String query = "SELECT * FROM Aircrafts";
            ResultSet resultSet = statement.executeQuery(query);

            System.out.println("-----------Aircraft Table-----------");
            System.out.printf("%-12s %-20s %-20s %-10s%n",
                    "Aircraft ID", "Model Name", "Registration Number", "Capacity");
            System.out.println("---------------------------------------------");

            // Print the aircraft data
            while (resultSet.next()) {
                int aircraftId = resultSet.getInt("Aircraft_Id");
                String modelName = resultSet.getString("Model_Name");
                int registrationNumber = resultSet.getInt("Registration_Number");
                int capacity = resultSet.getInt("Capacity");

                System.out.printf("%-12d %-20s %-20d %-10d%n",
                        aircraftId, modelName, registrationNumber, capacity);
            }

            // Close the resources
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void viewAllBookings(){
        try {
            Statement statement = connection.createStatement();

            String sql = "SELECT * FROM Bookings";
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("-----------Bookings Table -----------");
            System.out.printf("%-12s %-20s %-12s %-12s %-10s %-10s%n",
                    "Booking ID", "Username", "Flight ID", "Class ID", "Seat Number", "Price");
            System.out.println("---------------------------------------------");

            while (resultSet.next()) {
                int bookingId = resultSet.getInt("Booking_Id");
                String username = resultSet.getString("UserName");
                int flightId = resultSet.getInt("Flight_Id");
                int classId = resultSet.getInt("Class_Id");
                int seatNumber = resultSet.getInt("SeatNumber");
                int price = resultSet.getInt("Price");

                System.out.printf("%-12d %-20s %-12d %-12d %-10d %-10d%n",
                        bookingId, username, flightId, classId, seatNumber, price);
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getPrice(int id) throws SQLException {
        String sql = "SELECT price FROM Flights WHERE Flight_Id = ? ;";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet rs = statement.executeQuery();

        return rs.getInt("price");
    }
    public static void report() throws SQLException {
        System.out.println("-----------Admin Report-----------");


        viewAllAircraft();

        System.out.println();
        System.out.println();

        viewAllBookings();

        System.out.println();
        System.out.println();

        viewAllFlights();

        System.out.println();
        System.out.println();
    }
    public static boolean viewFlight() throws SQLException {
        String sql = "SELECT b.*, c.Class_Name FROM Bookings b " +
                "INNER JOIN Classes c ON b.Class_Id = c.Class_Id WHERE b.UserName = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, username);

        ResultSet rs = statement.executeQuery();
        if (!rs.next())
            return false;
        while (rs.next()) {
            int bookingId = rs.getInt("Booking_Id");
            String id = rs.getString("Flight_Id");
            String className = rs.getString("Class_Name");
            int price = rs.getInt("Price");
            System.out.println( "Booking ID: " + bookingId + "\nFlight ID: " + id + "\nClass: " + className + "\nPrice: " + price);
            System.out.println();
        }
        return true;
    }
    public static void UpdateFlight (int FlightID, String DestinationDate, String ArrivalDate, String source, String destination, int AvailableSeats1, int AvailableSeats2, int AvailableSeats3, int AircraftID, String DestinationTime, String ArrivalTime) throws SQLException {
        String sql = "UPDATE Flights SET Departure_Date = ?, Arrival_Date = ?, Flight_Source = ?, Flight_Destination = ?, Available_Seats_1 = ?, Available_Seats_2 = ?, Available_Seats_3 = ?, Aircraft_Id = ?, Time_of_Departure = ?, Time_of_Arrival = ? WHERE Flight_Id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, DestinationDate);
        statement.setString(2, ArrivalDate);
        statement.setString(3, source);
        statement.setString(4, destination);
        statement.setInt(5, AvailableSeats1);
        statement.setInt(6, AvailableSeats2);
        statement.setInt(7, AvailableSeats3);
        statement.setInt(8, AircraftID);
        statement.setString(9, DestinationTime);
        statement.setString(10, ArrivalTime);
        statement.setInt(11, FlightID);
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Flight updated successfully");
        }
        else {
            System.out.println("Error updating flight");
        }
    }
}


