// ServicesManagementController.java
package controller.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Service;
import dao.ServiceDao;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class ServicesManagementController {

    @FXML
    private VBox root;

    @FXML
    private TableView<Service> serviceTable;

    @FXML
    private TableColumn<Service, Integer> colID;

    @FXML
    private TableColumn<Service, String> colName;

    @FXML
    private TableColumn<Service, BigDecimal> colPrice;

    @FXML
    private TableColumn<Service, String> colType;

    @FXML
    private TableColumn<Service, String> colDescription;

    @FXML
    private Button btnAdd, btnEdit, btnDelete;

    @FXML
    private TextField serviceNameField, servicePriceField, descriptionField;

    @FXML
    private ComboBox<String> serviceTypeField;

    private final ObservableList<Service> serviceList = FXCollections.observableArrayList();
    private final ServiceDao serviceDao = new ServiceDao();

    @FXML
    public void initialize() {
        colID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        colType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));

        loadServices();
        serviceTable.setItems(serviceList);
    }

    private void loadServices() {
        serviceList.clear();
        serviceList.addAll(serviceDao.getAll());
    }

    @FXML
    private void handleAddService() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/add-service.fxml"));
            Parent newContent = fxmlLoader.load();
            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditService() {
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            Service updatedService = showServiceDialog(selectedService);
            if (updatedService != null) {
                serviceDao.update(updatedService);
                loadServices();
            }
        } else {
            showAlert("No Selection", "Please select a service to edit.");
        }
    }

    @FXML
    private void handleDeleteService() {
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
        if (selectedService != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Deletion");
            alert.setHeaderText("Are you sure you want to delete this service?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                serviceDao.delete(selectedService);
                loadServices();
            }
        } else {
            showAlert("No Selection", "Please select a service to delete.");
        }
    }

    private Service showServiceDialog(Service service) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle(service == null ? "Add Service" : "Edit Service");
        dialog.setHeaderText(service == null ? "Enter new service details" : "Edit service details");

        // Build custom dialog layout for all fields
        // Placeholder: You can replace with a custom FXML dialog if needed
        // Return the updated/new service
        return service; // Replace with actual dialog logic
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void handleSave(ActionEvent actionEvent) {
        try{
            String serviceName = serviceNameField.getText();
            double servicePrice = Double.parseDouble(servicePriceField.getText());
            String serviceType = serviceTypeField.getValue();
            String description = descriptionField.getText();

            Service service = new Service();
            service.setServiceName(serviceName);
            service.setServicePrice(BigDecimal.valueOf(servicePrice));
            service.setServiceType(serviceType);
            service.setDescription(description);

            serviceDao.save(service);

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/services-management.fxml"));
                Parent newContent = fxmlLoader.load();
                root.getChildren().setAll(newContent);

                loadServices();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            // Handle invalid input
            e.printStackTrace();
        }
    }
}