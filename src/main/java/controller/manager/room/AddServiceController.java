package controller.manager.room;

import dao.BookingDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Booking;
import model.Service;
import dao.ServiceDao;
import dao.BookingUsageDao;
import model.BookingUsage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AddServiceController {

    @FXML
    private VBox root;

    @FXML
    private ComboBox<Service> serviceComboBox;

    @FXML
    private TextField priceField;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private TextField bookingIdField;

    private final ServiceDao serviceDao = new ServiceDao();
    private final BookingUsageDao bookingUsageDao = new BookingUsageDao();

    public void setBookingID(int bookingId) {

        System.out.println("Booking IDDDD: " + bookingId);

        bookingIdField.setText(String.valueOf(bookingId));
    }

    @FXML
    public void initialize() {
        // Load all services into the combo box
        serviceComboBox.getItems().addAll(serviceDao.getAll());

        serviceComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                priceField.setText(newValue.getServicePrice().toString());
            }
        });
        // Initialize the quantity spinner with default values
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        quantitySpinner.setValueFactory(valueFactory);
    }

    @FXML
    public void handleAddServiceAndMore() {
        try {
            String bookingIdText = bookingIdField.getText();
            int bookingId = Integer.parseInt(bookingIdText);

            Service selectedService = serviceComboBox.getValue();
            if (selectedService == null) {
                showAlert("Validation Error", "Please select a service.");
                return;
            }

            int quantity = quantitySpinner.getValue();
            if (quantity <= 0) {
                showAlert("Validation Error", "Quantity must be greater than 0.");
                return;
            }

            // Calculate usage price (quantity * service price)
            BigDecimal usagePrice = selectedService.getServicePrice().multiply(BigDecimal.valueOf(quantity));
            // Create a new BookingUsage object
            BookingUsage bookingUsage = new BookingUsage();
            bookingUsage.setBookingID(bookingId);
            bookingUsage.setServiceID(selectedService.getServiceID());
            bookingUsage.setServiceUsagePrice(usagePrice.intValue());
            bookingUsage.setQuantity(quantity);
            bookingUsage.setUsageDate(LocalDate.now());

            // Save booking usage to the database
            bookingUsageDao.save(bookingUsage);

            showAlert("Success", "Service added to booking successfully.");

            // Clear the form
            serviceComboBox.getSelectionModel().clearSelection();
            priceField.clear();
            quantitySpinner.getValueFactory().setValue(1);


        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid booking ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while adding the service: " + e.getMessage());
        }

    }

    @FXML
    public void handleAddService() {
        try {
            String bookingIdText = bookingIdField.getText();
            int bookingId = Integer.parseInt(bookingIdText);

            Service selectedService = serviceComboBox.getValue();
            if (selectedService == null) {
                showAlert("Validation Error", "Please select a service.");
                return;
            }

            int quantity = quantitySpinner.getValue();
            if (quantity <= 0) {
                showAlert("Validation Error", "Quantity must be greater than 0.");
                return;
            }

            // Calculate usage price (quantity * service price)
            BigDecimal usagePrice = selectedService.getServicePrice().multiply(BigDecimal.valueOf(quantity));
            // Create a new BookingUsage object
            BookingUsage bookingUsage = new BookingUsage();
            bookingUsage.setBookingID(bookingId);
            bookingUsage.setServiceID(selectedService.getServiceID());
            bookingUsage.setServiceUsagePrice(usagePrice.intValue());
            bookingUsage.setQuantity(quantity);
            bookingUsage.setUsageDate(LocalDate.now());

            // Save booking usage to the database
            bookingUsageDao.save(bookingUsage);

            showAlert("Success", "Service added to booking successfully.");

            loadContent("/com/example/hotelpro/manager/room/room-management.fxml");

        } catch (NumberFormatException e) {
            showAlert("Validation Error", "Please enter a valid booking ID.");
        } catch (Exception e) {
            showAlert("Error", "An error occurred while adding the service: " + e.getMessage());
        }
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
