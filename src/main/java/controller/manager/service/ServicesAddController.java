package controller.manager.service;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import dao.ServiceDao;
import model.Service;

import java.math.BigDecimal;

public class ServicesAddController {

    private ServicesManagementController parentController;

    public void setParentController(ServicesManagementController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private TextField serviceNameField;

    @FXML
    private TextField servicePriceField;

    @FXML
    private ComboBox<String> serviceTypeField;

    @FXML
    private TextField descriptionField;

    @FXML
    public void initialize() {
        serviceTypeField.getItems().addAll("Room Service","Transport");
    }

    @FXML
    private void handleService() {
        if (!validateInputs()) {
            return;
        }

        try {
            Service newService = buildServiceFromInputs();
            new ServiceDao().save(newService);

            if (parentController != null) {
                parentController.reloadServiceTable();
            } else {
                System.err.println("Parent controller is null! Service table will not be reloaded.");
            }

            showAlert(Alert.AlertType.INFORMATION, "Service added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error saving service: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        String serviceName = serviceNameField.getText().trim();
        String servicePriceText = servicePriceField.getText().trim();
        String serviceType = serviceTypeField.getValue();

        if (serviceName.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Service name cannot be empty!");
            return false;
        }

        if (serviceType == null || serviceType.trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please select a valid service type!");
            return false;
        }

        try {
            BigDecimal servicePrice = new BigDecimal(servicePriceText);
            if (servicePrice.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert(Alert.AlertType.ERROR, "Service price must be greater than zero!");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid price format!");
            return false;
        }

        if (descriptionField.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Please provide a description!");
            return false;
        }

        return true;
    }

    private Service buildServiceFromInputs() {
        Service service = new Service();
        service.setServiceName(serviceNameField.getText().trim());
        service.setServicePrice(new BigDecimal(servicePriceField.getText().trim()));
        service.setServiceType(serviceTypeField.getValue());
        service.setDescription(descriptionField.getText().trim());
        return service;
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.showAndWait();
    }
}
