package controller.manager.service;

import dao.ServiceDao;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;

public class ServicesManagementController {

    @FXML
    private VBox contentArea;

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
    private TableColumn<Service, Void> actionColumn;

    private final ObservableList<Service> serviceList = FXCollections.observableArrayList();
    private final ServiceDao serviceDao = new ServiceDao();

    @FXML
    public void initialize() {
        setupTableColumns();
        setupColumnWidths();
        loadServices();
        setupActionColumn();
    }

    private void setupTableColumns() {
        colID.setCellValueFactory(new PropertyValueFactory<>("serviceID"));
        colName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        colType.setCellValueFactory(new PropertyValueFactory<>("serviceType"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void setupColumnWidths() {
        colID.prefWidthProperty().bind(Bindings.multiply(serviceTable.widthProperty(), 0.05));
        colName.prefWidthProperty().bind(Bindings.multiply(serviceTable.widthProperty(), 0.2));
        colPrice.prefWidthProperty().bind(Bindings.multiply(serviceTable.widthProperty(), 0.15));
        colType.prefWidthProperty().bind(Bindings.multiply(serviceTable.widthProperty(), 0.15));
        colDescription.prefWidthProperty().bind(Bindings.multiply(serviceTable.widthProperty(), 0.25));
        actionColumn.prefWidthProperty().bind(Bindings.multiply(serviceTable.widthProperty(), 0.2));
    }

    private void loadServices() {
        Service selectedService = serviceTable.getSelectionModel().getSelectedItem();
        Integer selectedServiceID = (selectedService != null) ? selectedService.getServiceID() : null;

        serviceList.setAll(serviceDao.getAll());
        serviceTable.setItems(serviceList);

        if (selectedServiceID != null) {
            restoreSelectionByID(selectedServiceID);
        }
    }

    private void restoreSelectionByID(int serviceID) {
        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).getServiceID() == serviceID) {
                serviceTable.getSelectionModel().select(i);
                serviceTable.scrollTo(i);
                break;
            }
        }
    }

    private void setupActionColumn() {
        actionColumn.setCellFactory(createActionCellFactory());
    }

    private Callback<TableColumn<Service, Void>, TableCell<Service, Void>> createActionCellFactory() {
        return param -> new TableCell<>() {
            private final Button updateButton = new Button("Update");
            private final Button deleteButton = new Button("Delete");
            private final HBox actionBox = new HBox(10, updateButton, deleteButton);

            {
                updateButton.setOnAction(event -> handleUpdateService(getCurrentService()));
                deleteButton.setOnAction(event -> handleDeleteService(getCurrentService()));
            }

            private Service getCurrentService() {
                return getTableView().getItems().get(getIndex());
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        };
    }

    public void handleAddService() {
        loadContent("/com/example/hotelpro/service/add-service.fxml");
    }

    private void handleUpdateService(Service service) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/service/edit-service.fxml"));
            Parent newContent = loader.load();

            EditServiceController controller = loader.getController();
            controller.setService(service);
            controller.setParentController(this);

            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteService(Service service) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Are you sure you want to delete this service?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            serviceDao.delete(service);
            loadServices();
        }
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newContent = loader.load();
            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadServiceTable() {
        loadServices();
    }
}
