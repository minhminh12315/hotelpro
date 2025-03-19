package controller.manager.booking;

import dao.BookingDao;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Booking;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class BookingController {

    @FXML
    private VBox root;

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
    @FXML
    private TextField searchField;
    @FXML
    private Pagination pagination;

    private int itemsPerPage = 13;

    private BookingDao bookingDao = new BookingDao();
    private ObservableList<Booking> bookingList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        bookingIdColumn.prefWidthProperty().bind(Bindings.multiply(bookingTable.widthProperty(), 0.1));
        customerIdColumn.prefWidthProperty().bind(Bindings.multiply(bookingTable.widthProperty(), 0.1));
        roomIdColumn.prefWidthProperty().bind(Bindings.multiply(bookingTable.widthProperty(), 0.1));
        bookingDateColumn.prefWidthProperty().bind(Bindings.multiply(bookingTable.widthProperty(), 0.2));
        checkInDateColumn.prefWidthProperty().bind(Bindings.multiply(bookingTable.widthProperty(), 0.2));
        checkOutDateColumn.prefWidthProperty().bind(Bindings.multiply(bookingTable.widthProperty(), 0.2));
        statusColumn.prefWidthProperty().bind(Bindings.multiply(bookingTable.widthProperty(), 0.094));

        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        roomIdColumn.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        bookingDateColumn.setCellValueFactory(new PropertyValueFactory<>("bookingDate"));
        checkInDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        loadBookings();

        pagination.setPageCount((int) Math.ceil((double) bookingList.size() / itemsPerPage));
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(5);

        loadPage(0);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> loadPage(newValue.intValue()));

    }

    @FXML
    public void addBooking() {
        loadContent("/com/example/hotelpro/manager/room/pre-order-room.fxml");
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
    private void searchBooking() {
        String searchText = searchField.getText().toLowerCase();
        bookingTable.setItems(FXCollections.observableArrayList());

        if (searchText.isEmpty()){
            bookingTable.setItems(FXCollections.observableArrayList(bookingList));
        } else {
            List<Booking> filteredList = bookingList.stream()
                    .filter(booking -> String.valueOf(booking.getBookingID()).toLowerCase().contains(searchText) ||
                            String.valueOf(booking.getCustomerID()).toLowerCase().contains(searchText) ||
                            String.valueOf(booking.getRoomID()).toLowerCase().contains(searchText) ||
                            booking.getStatus().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());

            bookingTable.setItems(FXCollections.observableArrayList(filteredList));
        }
    }

    private void loadPage(int pageIndex) {
        int start = pageIndex * itemsPerPage;
        int end = Math.min(start + itemsPerPage, bookingList.size());

        List<Booking> pageData = bookingList.subList(start, end);
        bookingTable.setItems(FXCollections.observableArrayList(pageData));
    }
}
