package controller.manager.service;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Service;
import dao.ServiceDao;

import java.math.BigDecimal;

public class EditServiceController {

    @FXML
    private TextField serviceNameField, servicePriceField, descriptionField;

    @FXML
    private ComboBox<String> serviceTypeField;

    private Service service;
    private final ServiceDao serviceDao = new ServiceDao();

    private ServicesManagementController parentController;

    public void setService(Service service) {
        this.service = service;
        fillServiceData();
    }

    public void setParentController(ServicesManagementController parentController) {
        this.parentController = parentController;
    }

    private void fillServiceData() {
        if (service != null) {
            serviceNameField.setText(service.getServiceName());
            servicePriceField.setText(service.getServicePrice().toString());
            descriptionField.setText(service.getDescription());
            serviceTypeField.setValue(service.getServiceType());
        }
    }

    @FXML
    private void saveService() {
        try {
            service.setServiceName(serviceNameField.getText());
            service.setServicePrice(new BigDecimal(servicePriceField.getText()));
            service.setServiceType(serviceTypeField.getValue());
            service.setDescription(descriptionField.getText());

            serviceDao.update(service);

            showAlert(Alert.AlertType.INFORMATION, "Service updated successfully!");

            if (parentController != null) {
                parentController.reloadServiceTable();  // Gọi cha refresh table
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to update service: " + e.getMessage());
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message);
        alert.showAndWait();
    }

    @FXML
    public void updateService() {
        saveService();
    }
}
