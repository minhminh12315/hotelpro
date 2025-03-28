package controller.manager.room;

import controller.manager.MasterController;
import dao.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class CheckoutController {

    @FXML
    private VBox root;

    @FXML
    private Label roomIdLabel;

    @FXML
    private TextField customerNameField;

    @FXML
    private TextField phoneNumberField;

    @FXML
    private TextField totalAmountField;

    @FXML
    private DatePicker checkOutDateField;

    @FXML
    private DatePicker checkInDateField;

    @FXML
    private Button confirmCheckoutButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TableView<BookingUsage> bookingUsageTableView;
    @FXML
    private TableColumn<BookingUsage, String> serviceColumn;
    @FXML
    private TableColumn<BookingUsage, Integer> quantityColumn;
    @FXML
    private TableColumn<BookingUsage, BigDecimal> priceColumn;


    @FXML
    private ComboBox<Product> productComboBox;
    @FXML
    private TextField productQuantityField;

    private List<Product> selectedProducts = new ArrayList<>();


    private int roomId;
    private int bookingId;

    BookingDao bookingDao = new BookingDao();
    CustomerDao customerDao = new CustomerDao();
    BookingUsageDao bookingUsageDao = new BookingUsageDao();
    InvoiceDao invoiceDao = new InvoiceDao();
    InventoryTransactionDao inventoryTransactionDao = new InventoryTransactionDao();
    RoomDao roomDao = new RoomDao();

    public void setRoomId(int roomId) {
        this.roomId = roomId;
        roomIdLabel.setText("" + roomId);
    }

    public void setRoot(VBox root){
        this.root = root;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
        Booking booking = bookingDao.findById(bookingId);
        
        System.out.println("Booking: " + booking);
        if (booking != null) {
            Customer customer = customerDao.findById(booking.getCustomerID());
            System.out.println("Customer: " + customer);
            checkInDateField.setValue(booking.getCheckInDate());
            if (customer != null) {
                customerNameField.setText(customer.getFullName());
                phoneNumberField.setText(customer.getPhoneNumber());
                System.out.println("Customer name: " + customer.getFullName());
            }
            loadBookingUsage(bookingId);

            // Calculate total amount
            BigDecimal totalAmount = calculateTotalAmount(bookingId);
            totalAmountField.setText(totalAmount.toString());
        }
    }

    private void loadBookingUsage(int bookingId) {
        List<BookingUsage> bookingUsages = bookingUsageDao.findByBookingId(bookingId);
        bookingUsageTableView.getItems().setAll(bookingUsages);
    }

    @FXML
    private void initialize() {
        confirmCheckoutButton.setOnAction(event -> handleConfirmCheckout());
        cancelButton.setOnAction(event -> handleCancel());
        checkOutDateField.setValue(LocalDate.now());

        // Load products into ComboBox
        List<Product> products = new ProductDao().getAll();
        productComboBox.getItems().addAll(products);

        // Initialize table columns
        serviceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getServiceName()));

        quantityColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuantity()).asObject());
        priceColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getServiceUsagePrice()));
        // Load booking usage data
        loadBookingUsage(bookingId);
    }

    public BigDecimal calculateTotalAmount(int bookingId) {
        BigDecimal roomCharge = calculateRoomCharge(bookingId);  // Calculate room charge
        BigDecimal serviceCharges = calculateServiceCharges(bookingId);  // Calculate service charges
        BigDecimal productCharges = calculateProductCharges(bookingId);  // Calculate product charges

        // Total amount = Room charge + Service charges + Product charges
        return roomCharge.add(serviceCharges).add(productCharges);
    }

    private BigDecimal calculateRoomCharge(int bookingId) {
        Booking booking = bookingDao.findById(bookingId);
        BigDecimal roomCharge = new BigDecimal(booking.getRoomPrice() * bookingDao.getNumberOfDays(bookingId));
        return roomCharge;
    }

    private BigDecimal calculateServiceCharges(int bookingId) {
        List<BookingUsage> bookingUsages = bookingUsageDao.findByBookingId(bookingId);
        BigDecimal totalServiceCharges = BigDecimal.ZERO;
        for (BookingUsage usage : bookingUsages) {
            if (usage.getServiceID() != null) {
                totalServiceCharges = totalServiceCharges.add(usage.getServiceUsagePrice().multiply(new BigDecimal(usage.getQuantity())));
            }
        }
        return totalServiceCharges;
    }

    private BigDecimal calculateProductCharges(int bookingId) {
        List<BookingUsage> bookingUsages = bookingUsageDao.findByBookingId(bookingId);
        BigDecimal totalProductCharges = BigDecimal.ZERO;
        for (BookingUsage usage : bookingUsages) {
            if (usage.getProductID() != null) {
                totalProductCharges = totalProductCharges.add(usage.getServiceUsagePrice().multiply(new BigDecimal(usage.getQuantity())));
            }
        }
        return totalProductCharges;
    }

    @FXML
    private void addProductToInvoice() {
        Product selectedProduct = productComboBox.getSelectionModel().getSelectedItem();
        String quantityText = productQuantityField.getText();

        if (selectedProduct == null || quantityText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a product and enter quantity.");
            alert.showAndWait();
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(quantityText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Quantity must be a valid number.");
            alert.showAndWait();
            return;
        }

        selectedProduct.setQuantity(quantity);
        selectedProducts.add(selectedProduct);
        bookingUsageTableView.getItems().add(new BookingUsage(selectedProduct.getProductID(), selectedProduct.getProductName(), quantity, selectedProduct.getUnitPrice()));
    }

    private void handleConfirmCheckout() {
        String customerName = customerNameField.getText();
        String totalAmount = totalAmountField.getText();
        LocalDate checkOutDate = checkOutDateField.getValue();
        Integer employeeId = MasterController.getEmployeeID();

        if (customerName.isEmpty() || totalAmount.isEmpty() || checkOutDate == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.", ButtonType.OK);
            alert.show();
            return;
        }

        try {
            BigDecimal amount = new BigDecimal(totalAmount);
            amount = calculateTotalAmount(bookingId);

            // Call a DAO or service method to handle the checkout process, e.g., save checkout info to the database.

            // Create and save the invoice
            Invoice invoice = new Invoice();
            invoice.setBooking(bookingDao.findById(bookingId));
            invoice.setIssueDate(LocalDate.now());
            invoice.setTotalAmount(amount);
            invoice.setPaymentMethod("Credit"); // Example payment method
            invoice.setStatus("Paid");
            invoiceDao.save(invoice);

            // Update inventory
            List<BookingUsage> bookingUsages = bookingUsageDao.findByBookingId(bookingId);
            for (BookingUsage usage : bookingUsages) {
                if (usage.getProductID() == null) {
                    continue;
                }
                InventoryTransaction transaction = new InventoryTransaction();
                transaction.setProductID(usage.getProductID());
                transaction.setBookingID(bookingId);
                transaction.setEmployeeID(employeeId);
                transaction.setQuantity(usage.getQuantity());
                transaction.setTransactionType("Export");
                transaction.setTransactionDate(LocalDate.now());
                transaction.setRemarks("Checkout");
                inventoryTransactionDao.save(transaction);
            }

            for (Product product : selectedProducts) {
                InventoryTransaction transaction = new InventoryTransaction();
                transaction.setProductID(product.getProductID());
                transaction.setBookingID(bookingId);
                transaction.setEmployeeID(employeeId);
                transaction.setQuantity(product.getQuantity());
                transaction.setTransactionType("Export");
                transaction.setTransactionDate(LocalDate.now());
                transaction.setRemarks("Checkout");
                inventoryTransactionDao.save(transaction);
            }

            Booking booking = bookingDao.findById(bookingId);
            booking.setCheckOutDate(checkOutDate);
            booking.setStatus("CheckedOut");
            bookingDao.update(booking);

            Room room = roomDao.findById(roomId);
            room.setStatus("Available");
            roomDao.update(room);

            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Checkout completed successfully!", ButtonType.OK);
            successAlert.show();

            loadContent("/com/example/hotelpro/manager/room/room-management.fxml");

            // Optionally reset fields or redirect back to the room management view.
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid total amount.", ButtonType.OK);
            alert.show();
        }
    }

    private void handleCancel() {
        loadContent("/com/example/hotelpro/manager/room/room-management.fxml");
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
}
