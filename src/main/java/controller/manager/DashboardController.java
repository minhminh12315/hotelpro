package controller.manager;

import dao.CustomerDao;
import dao.RoomDao;
import dao.ServiceDao;
import dao.InvoiceDao;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Customer;
import model.Room;
import model.Service;

import java.util.List;

public class    DashboardController {

    @FXML
    private Label totalRevenueLabel;

    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableView<Room> roomsTable;
    @FXML
    private TableColumn<Room, Integer> roomIdColumn;
    @FXML
    private TableColumn<Room, String> roomTypeColumn;

    @FXML
    private TableView<Service> servicesTable;
    @FXML
    private TableColumn<Service, Integer> serviceIdColumn;
    @FXML
    private TableColumn<Service, String> serviceNameColumn;

    private CustomerDao customerDao = new CustomerDao();
    private RoomDao roomDao = new RoomDao();
    private ServiceDao serviceDao = new ServiceDao();
    private InvoiceDao invoiceDao = new InvoiceDao();

    @FXML
    public void initialize() {
        // Initialize columns
        customerIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        customerNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        roomIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().typeProperty());

        serviceIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        serviceNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());

        // Load data
        loadRevenue();
        loadCustomers();
        loadRooms();
        loadServices();
    }

    private void loadRevenue() {
        double totalRevenue = invoiceDao.getTotalRevenue();
        totalRevenueLabel.setText(String.format("$%.2f", totalRevenue));
    }

    private void loadCustomers() {
        List<Customer> customers = customerDao.getAll();
        customersTable.getItems().setAll(customers);
    }

    private void loadRooms() {
        List<Room> rooms = roomDao.getAll();
        roomsTable.getItems().setAll(rooms);
    }

    private void loadServices() {
        List<Service> services = serviceDao.getAll();
        servicesTable.getItems().setAll(services);
    }
}