package controller.manager;

import dao.CustomerDao;
import dao.RoomDao;
import dao.ServiceDao;
import dao.InvoiceDao;
import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.*;

import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DashboardController {

    public VBox containArea;
    @FXML
    Label totalRevenueTodayLabel;
    @FXML
    Label totalRevenueMonthLabel;
    @FXML
    Label totalRevenueYearLabel;
    @FXML
    private TableView<ServiceUsage> revenueBySourceTable;
    @FXML
    private TableColumn<ServiceUsage, Integer> RevenueBookingIdColumn;
    @FXML
    private TableColumn<ServiceUsage, String> serviceTotalColumn;
    @FXML
    private TableColumn<ServiceUsage, String> productTotalColumn;
    @FXML
    private TableColumn<ServiceUsage, String> roomTotalColumn;

    @FXML
    Label totalRoomsLabel;
    @FXML
    Label availableRoomsLabel;
    @FXML
    Label occupiedRoomsLabel;
    @FXML
    Label maintenanceRoomsLabel;

    @FXML
    Label pendingBookingsLabel;
    @FXML
    Label checkedInBookingsLabel;
    @FXML
    Label checkedOutBookingsLabel;
    @FXML
    TableView<Booking> upcomingBookingsTable;
    @FXML
    TableColumn<Booking, Integer> bookingIdColumn;
    @FXML
    TableColumn<Booking, String> bookingCustomerNameColumn;
    @FXML
    TableColumn<Booking, String> bookingRoomColumn;
    @FXML
    TableColumn<Booking, String> bookingCheckInColumn;

    @FXML
    Label totalProductsLabel;
    @FXML
    TableView<Inventory> lowStockProductTable;
    @FXML
    TableColumn<Inventory, Integer> productIdColumn;
    @FXML
    TableColumn<Inventory, Integer> lowStockQuantityColumn;
    @FXML
    TableColumn<Inventory, LocalDate> lastUpdatedColumn;
    @FXML
    TableView<InventoryTransaction> inventoryTransactionsTable;
    @FXML
    TableColumn<InventoryTransaction, Integer> transactionIdColumn;
    @FXML
    TableColumn<InventoryTransaction, String> productColumn;
    @FXML
    TableColumn<InventoryTransaction, Integer> quantityColumn;
    @FXML
    TableColumn<InventoryTransaction, String> transactionDateColumn;

    @FXML
    Label totalServicesTodayLabel;
    @FXML
    Label totalServicesMonthLabel;
    @FXML
    TableView<ServiceUsage> mostUsedServicesTable;
    @FXML
    TableColumn<ServiceUsage, String> serviceNameColumn;
    @FXML
    TableColumn<ServiceUsage, Integer> serviceUsageCountColumn;

    @FXML
    Label totalCustomersLabel;
    @FXML
    TableView<Customer> customersInHotelTable;
    @FXML
    TableColumn<Customer, String> customerInHotelNameColumn;
    @FXML
    TableColumn<Customer, String> customerRoomColumn;
    @FXML
    TableColumn<Customer, String> customerCheckInColumn;
    @FXML
    TableColumn<Customer, String> customerCheckOutColumn;

    @FXML
    Label totalTransactionsLabel;
    @FXML
    Label transactionsPerEmployeeLabel;
    @FXML
    TableView<EmployeePerformance> topEmployeesTable;
    @FXML
    TableColumn<EmployeePerformance, String> employeeNameColumn;
    @FXML
    TableColumn<EmployeePerformance, Integer> employeeTransactionCountColumn;

    @FXML
    Label bookingAlertsLabel;
    @FXML
    Label maintenanceAlertsLabel;
    @FXML
    Label inventoryAlertsLabel;

    @FXML
    Button generateRevenueReportButton;

    @FXML
    Label totalCheckInsTodayLabel;
    @FXML
    Label totalCheckOutsTodayLabel;
    @FXML
    Label totalRevenueTodaySummaryLabel;


    @FXML
    public void initialize() throws SQLException {
        RevenueOverview();
        RoomStatus();
        BookingOverview();
        InventoryStatus();
        ServiceUsage();
        CustomerOverview();
        EmployeePerformance();
    }

    private void RevenueOverview(){
        configureTableView(revenueBySourceTable);

        double totalRevenueToday = ServiceUsage.getRevenueForToday();
        double totalRevenueMonth = ServiceUsage.getRevenueForMonth();
        double totalRevenueYear = ServiceUsage.getRevenueForYear();

        totalRevenueTodayLabel.setText("$" + totalRevenueToday);
        totalRevenueMonthLabel.setText("$" + totalRevenueMonth);
        totalRevenueYearLabel.setText("$" + totalRevenueYear);

        List<ServiceUsage> lstSU = new ServiceUsage().getAll();
        ObservableList<ServiceUsage> serviceUsageList = FXCollections.observableArrayList(lstSU);

        RevenueBookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("BookingID"));
        serviceTotalColumn.setCellValueFactory(new PropertyValueFactory<>("ServiceTotal"));
        productTotalColumn.setCellValueFactory(new PropertyValueFactory<>("ProductTotal"));
        roomTotalColumn.setCellValueFactory(new PropertyValueFactory<>("RoomTotal"));

        revenueBySourceTable.setItems(serviceUsageList);
    }

    private void RoomStatus() {

        double totalRoom = Room.getTotalRoom();
        double totalAvailableRoom = Room.getAvailableRoom();
        double totalOccupiedRoom = Room.getOccupiedRoom();
        double totalMaintenanceRoom = Room.getMaintenanceRoom();

        totalRoomsLabel.setText(totalRoom + " room");
        availableRoomsLabel.setText(totalAvailableRoom + " room");
        occupiedRoomsLabel.setText(totalOccupiedRoom + " room");
        maintenanceRoomsLabel.setText(totalMaintenanceRoom + " room");
    }

    private void BookingOverview() {
        configureTableView(upcomingBookingsTable);

        double totalPendingBooking = Booking.getTotalPendingBooking();
        double totalCheckedOutBooking = Booking.getTotalCheckedOutBooking();
        double totalCheckedInBooking = Booking.getTotalCheckedInBooking();

        pendingBookingsLabel.setText(String.valueOf(totalPendingBooking));
        checkedInBookingsLabel.setText(String.valueOf(totalCheckedOutBooking));
        checkedOutBookingsLabel.setText(String.valueOf(totalCheckedInBooking));


        List<Booking> lstB = new Booking().getUpcomingBooking();
        ObservableList<Booking> bookingList = FXCollections.observableArrayList(lstB);

        bookingIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookingID"));
        bookingCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullname"));
        bookingRoomColumn.setCellValueFactory(new PropertyValueFactory<>("roomnumber"));
        bookingCheckInColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));

        upcomingBookingsTable.setItems(bookingList);
    }

    private void InventoryStatus(){
        configureTableView(lowStockProductTable);
        configureTableView(inventoryTransactionsTable);

        double totalProduct = Inventory.getTotalProduct();

        totalProductsLabel.setText(String.valueOf(totalProduct));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        lowStockQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        lastUpdatedColumn.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));

        transactionIdColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        productColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        transactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));

        List<Inventory> lowStockProducts = new Inventory().getLowStockProducts();
        ObservableList<Inventory> lowStockObservableList = FXCollections.observableArrayList(lowStockProducts);
        lowStockProductTable.setItems(lowStockObservableList);

        List<InventoryTransaction> inventoryTransactions = new InventoryTransaction().getInventoryTransactions();
        ObservableList<InventoryTransaction> inventoryObservableList = FXCollections.observableArrayList(inventoryTransactions);
        inventoryTransactionsTable.setItems(inventoryObservableList);
    }

    private void ServiceUsage(){
        configureTableView(mostUsedServicesTable);

        double totalServicesToday = Service.getTotalServicesToday();
        double totalServicesMonthly= Service.getTotalServicesMonthly();

        totalServicesTodayLabel.setText(String.valueOf(totalServicesToday));
        totalServicesMonthLabel.setText(String.valueOf(totalServicesMonthly));


        serviceNameColumn.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        serviceUsageCountColumn.setCellValueFactory(new PropertyValueFactory<>("usageCount"));

        List<ServiceUsage> mostUsedServices = ServiceUsage.getMostUsedServices();
        ObservableList<ServiceUsage> servicesObservableList = FXCollections.observableArrayList(mostUsedServices);
        mostUsedServicesTable.setItems(servicesObservableList);

    }

    private void CustomerOverview(){
        configureTableView(customersInHotelTable);

        double totalCustomer = Customer.getTotalCustomers();

        totalCustomersLabel.setText(String.valueOf(totalCustomer));


        customerInHotelNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        customerRoomColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        customerCheckInColumn.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        customerCheckOutColumn.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));

        List<Customer> customersInHotel = new Customer().getCustomersInHotel();

        ObservableList<Customer> customersObservableList = FXCollections.observableArrayList(customersInHotel);
        customersInHotelTable.setItems(customersObservableList);
    }

    private void EmployeePerformance() throws SQLException {
        configureTableView(topEmployeesTable);

        Employee employee = new Employee();

        // Get total transactions
        int totalTransactions = employee.getTotalTransactions();
        totalTransactionsLabel.setText(String.valueOf(totalTransactions));

        // Get employee performance (top employees)
        List<EmployeePerformance> performanceList = employee.getEmployeePerformance();
        ObservableList<EmployeePerformance> observableList = FXCollections.observableArrayList(performanceList);

        topEmployeesTable.refresh();

        // Set data into table columns
        employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().employeeNameProperty());
        employeeTransactionCountColumn.setCellValueFactory(cellData -> cellData.getValue().transactionCountProperty().asObject());

        // Set items into the table
        topEmployeesTable.setItems(observableList);

        // Calculate transactions per employee and update the label
        if (totalTransactions > 0 && !performanceList.isEmpty()) {
            int avgTransactions = totalTransactions / performanceList.size();
            transactionsPerEmployeeLabel.setText(String.valueOf(avgTransactions));
        }
    }

    private void configureTableView(TableView<?> tableView) {
        tableView.setPrefHeight(300);
        tableView.setMinHeight(300);
        tableView.setMaxHeight(300);
    }

}