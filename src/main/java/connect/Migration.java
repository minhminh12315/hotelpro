package connect;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Migration {
    public static void migrate() {
        String createCustomerTableSQL = "CREATE TABLE IF NOT EXISTS Customer ("
                + "CustomerID SERIAL PRIMARY KEY, "
                + "FullName VARCHAR(100) NOT NULL, "
                + "PhoneNumber VARCHAR(15) NOT NULL, "
                + "Email VARCHAR(100), "
                + "Address TEXT, "
                + "ID_Passport VARCHAR(20) NOT NULL, "
                + "DateOfBirth DATE, "
                + "Gender VARCHAR(10)"
                + ")";

        String createRoomTableSQL = "CREATE TABLE IF NOT EXISTS Room ("
                + "RoomID SERIAL PRIMARY KEY, "
                + "RoomType VARCHAR(50) NOT NULL, "
                + "Price DECIMAL(10, 2) NOT NULL, "
                + "Status VARCHAR(15) DEFAULT 'Available', "
                + "Description TEXT"
                + ")";

        String createBookingTableSQL = "CREATE TABLE IF NOT EXISTS Booking ("
                + "BookingID SERIAL PRIMARY KEY, "
                + "CustomerID INT NOT NULL, "
                + "RoomID INT NOT NULL, "
                + "BookingDate DATE NOT NULL, "
                + "RoomPrice INT, "
                + "CheckInDate DATE NOT NULL, "
                + "CheckOutDate DATE, "
                + "Status VARCHAR(15) DEFAULT 'Pending', "
                + "FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID), "
                + "FOREIGN KEY (RoomID) REFERENCES Room(RoomID)"
                + ")";

        String createServiceTableSQL = "CREATE TABLE IF NOT EXISTS Service ("
                + "ServiceID SERIAL PRIMARY KEY, "
                + "ServiceName VARCHAR(100) NOT NULL, "
                + "ServicePrice DECIMAL(10, 2) NOT NULL, "
                + "Description TEXT"
                + ")";

        String createServiceUsageTableSQL = "CREATE TABLE IF NOT EXISTS ServiceUsage ("
                + "ServiceUsageID SERIAL PRIMARY KEY, "
                + "BookingID INT NOT NULL, "
                + "ServiceID INT NOT NULL, "
                + "ServiceUsagePrice INT, "
                + "Quantity INT DEFAULT 1 NOT NULL, "
                + "UsageDate DATE NOT NULL, "
                + "FOREIGN KEY (BookingID) REFERENCES Booking(BookingID), "
                + "FOREIGN KEY (ServiceID) REFERENCES Service(ServiceID)"
                + ")";

        String createInvoiceTableSQL = "CREATE TABLE IF NOT EXISTS Invoice ("
                + "InvoiceID SERIAL PRIMARY KEY, "
                + "BookingID INT NOT NULL, "
                + "IssueDate DATE NOT NULL, "
                + "TotalAmount DECIMAL(15, 2) NOT NULL, "
                + "PaymentMethod VARCHAR(10), "
                + "Status VARCHAR(10) DEFAULT 'Unpaid', "
                + "FOREIGN KEY (BookingID) REFERENCES Booking(BookingID)"
                + ")";

        String createEmployeeTableSQL = "CREATE TABLE IF NOT EXISTS Employee ("
                + "EmployeeID SERIAL PRIMARY KEY, "
                + "FullName VARCHAR(100) NOT NULL, "
                + "PhoneNumber VARCHAR(15), "
                + "Email VARCHAR(100), "
                + "Role VARCHAR(10) DEFAULT 'Staff', "
                + "Password VARCHAR(255) NOT NULL, "
                + "StartDate DATE NOT NULL"
                + ")";

        try (Connection connection = Connect.connection();
             Statement statement = connection.createStatement()) {

            statement.execute(createCustomerTableSQL);
            statement.execute(createRoomTableSQL);
            statement.execute(createBookingTableSQL);
            statement.execute(createServiceTableSQL);
            statement.execute(createServiceUsageTableSQL);
            statement.execute(createInvoiceTableSQL);
            statement.execute(createEmployeeTableSQL);

            System.out.println("Tables created successfully!");

        } catch (SQLException e) {
            System.out.println("Error creating tables!");
            e.printStackTrace();
        }
    }
}