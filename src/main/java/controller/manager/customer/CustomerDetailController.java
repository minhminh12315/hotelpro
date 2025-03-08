package controller.manager.customer;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import model.Booking;
import model.Customer;
import model.Room;

import java.sql.SQLException;
import java.util.Date;

public class CustomerDetailController {
    @FXML
    VBox contentArea;

    @FXML
    private TableView<Booking> bookingsTable;

    @FXML
    private TableColumn<Booking, Integer> bookingIdColumn;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Room, Integer> roomIdColumn;
    @FXML
    private TableColumn<Booking, Date> bookingDateColumn;
    @FXML
    private TableColumn<Booking, Integer> roomPriceColumn;
    @FXML
    private TableColumn<Booking, Date> expectedCheckInColumn;
    @FXML
    private TableColumn<Booking, Date> expectedCheckOutColumn;
    @FXML
    private TableColumn<Booking, Date> checkInDateColumn;
    @FXML
    private TableColumn<Booking, Date> checkOutDateColumn;
    @FXML
    private TableColumn<Booking, String> statusColumn;

    Customer customer;

    public CustomerDetailController() {}

    public CustomerDetailController(int id, VBox contentArea) throws SQLException {
//        customer = new Customer().getById(id).getCustomer();

        this.contentArea = contentArea;
    }
}
