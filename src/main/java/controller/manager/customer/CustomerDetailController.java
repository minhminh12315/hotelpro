package controller.manager.customer;

import dao.BookingDao;
import dao.CustomerDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Booking;
import model.Customer;
import model.Employee;
import model.Room;

import java.awt.print.Book;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

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
    private TableColumn<Booking, Date> checkInDateColumn;
    @FXML
    private TableColumn<Booking, Date> checkOutDateColumn;
    @FXML
    private TableColumn<Booking, String> statusColumn;

    List<Booking> booking;
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    public CustomerDetailController() {}

    public CustomerDetailController(int id, VBox contentArea) throws SQLException {
        this.contentArea = contentArea;

        booking = new Booking().getByCustomerId(id);
    }

    @FXML
    public void initialize(){
        loadCustomerDetail();
    }


    private void loadCustomerDetail() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        roomPriceColumn.setCellValueFactory(new PropertyValueFactory<>("roomPrice"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        bookingList.addAll(booking);
        bookingsTable.setItems(bookingList);
    }
}
