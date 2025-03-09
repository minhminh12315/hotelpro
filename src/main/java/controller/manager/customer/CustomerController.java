package controller.manager.customer;

import controller.manager.employee.EmployeeAddController;
import controller.manager.employee.EmployeeEditController;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Customer;
import dao.CustomerDao;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CustomerController {
    @FXML
    VBox contentArea;

    @FXML
    private TableView<Customer> customersTable;
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;
    @FXML
    private TableColumn<Customer,String> customerNameColumn;
    @FXML
    private TableColumn<Customer,String> customerPhoneColumn;
    @FXML
    private TableColumn<Customer,String> customerEmailColumn;
    @FXML
    private TableColumn<Customer,String> customerAddressColumn;
    @FXML
    private TableColumn<Customer,String> customerIdPassportColumn;
    @FXML
    private TableColumn<Customer, LocalDate> customerDOBColumn;
    @FXML
    private TableColumn<Customer,String> customerGenderColumn;
    @FXML
    private TableColumn<Customer, String> actionColumn;

    private CustomerDao customerDao = new CustomerDao();
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @FXML
    public void initialize(){
        customerIdColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.025));
        customerNameColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.1));
        customerPhoneColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.1));
        customerEmailColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.15));
        customerAddressColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.15));
        customerIdPassportColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.1));
        customerDOBColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.1));
        customerGenderColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.075));

        // Action column - give it more space since it holds multiple buttons
        actionColumn.prefWidthProperty().bind(Bindings.multiply(customersTable.widthProperty(), 0.18));

        loadCustomers();

        actionColumn.setCellFactory(new Callback<TableColumn<Customer, String>, TableCell<Customer, String>>() {
            @Override
            public TableCell<Customer, String> call(TableColumn<Customer, String> param) {
                return new TableCell<Customer, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button updateButton = new Button("Update");
                            Button deleteButton = new Button("Delete");
                            Button detailButton = new Button("...");

                            updateButton.setOnAction(event -> {
                                Customer customer = getTableView().getItems().get(getIndex());
                                System.out.println("Update for: " + customer.getFullName());
                                updateCustomer(customer.getCustomerID());
                            });

                            deleteButton.setOnAction(event -> {
                                Customer customer = getTableView().getItems().get(getIndex());
                                System.out.println("Delete for: " + customer.getFullName());
                                deleteCustomer(customer.getCustomerID());
                            });

                            detailButton.setOnAction(event -> {
                                Customer customer = getTableView().getItems().get(getIndex());
                                System.out.println("Detail for: " + customer.getFullName());
                                detailButton(customer.getCustomerID());
                            });


                            // Place buttons in HBox for layout
                            HBox hbox = new HBox(10, updateButton, deleteButton, detailButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    private void loadCustomers() {
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        customerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        customerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        customerIdPassportColumn.setCellValueFactory(new PropertyValueFactory<>("idPassport"));
        customerDOBColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        customerGenderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        customerList.setAll(customerDao.getAll());
        customersTable.setItems(customerList);
    }

    public void detailButton(int id){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/customer/customer-detail.fxml"));

            CustomerDetailController customerDetailController = new CustomerDetailController(id, contentArea);
            fxmlLoader.setControllerFactory(param -> customerDetailController);

            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addCustomer() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/customer/customer-add.fxml"));

            CustomerAddController customerAddController = new CustomerAddController(contentArea);
            fxmlLoader.setControllerFactory(param -> customerAddController);

            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCustomer(int id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/customer/customer-edit.fxml"));

            CustomerEditController customerEditController = new CustomerEditController(id, contentArea);
            fxmlLoader.setControllerFactory(param -> customerEditController);

            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if FXML loading fails
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCustomer(int customerId) {
        Customer customer = new Customer();
        customer.setCustomerID(customerId);

        customerDao.delete(customer);
        loadCustomers();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully");
        alert.showAndWait();
    }

}
