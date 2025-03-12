package controller.manager.booking;

import dao.BookingDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Booking;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class BookingController {

    @FXML
    private VBox root;

    @FXML
    private TextField searchBox;

    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private TableColumn<Booking, Integer> bookingIdColumn;

    @FXML
    private TableColumn<Booking, Integer> customerIdColumn;

    @FXML
    private TableColumn<Booking, Integer> roomIdColumn;

    @FXML
    private TableColumn<Booking, LocalDate> bookingDateColumn;

    @FXML
    private TableColumn<Booking, LocalDate> checkInDateColumn;

    @FXML
    private TableColumn<Booking, LocalDate> checkOutDateColumn;

    @FXML
    private TableColumn<Booking, String> statusColumn;

    private BookingDao bookingDao = new BookingDao();
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadBookings();
    }

    @FXML
    public void addBooking() {
        loadContent("/com/example/hotelpro/manager/pre-order-room.fxml");
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newContent = fxmlLoader.load();
            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadBookings() {
        List<Booking> bookings = bookingDao.getAll();
        bookingList.setAll(bookings);
        bookingTable.setItems(bookingList);
    }

    @FXML
    private void searchBookings() {
        String searchText = searchBox.getText().toLowerCase();
        List<Booking> filteredBookings = bookingDao.searchBookings(searchText);
        bookingList.setAll(filteredBookings);
    }
}
