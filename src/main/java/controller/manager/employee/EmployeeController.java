package controller.manager.employee;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Employee;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class EmployeeController {
    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, Integer> employeeIdColumn;
    @FXML
    private TableColumn<Employee, String> employeeNameColumn;
    @FXML
    private TableColumn<Employee, String> employeePhoneColumn;

    @FXML
    private TableColumn<Employee, String> employeeEmailColumn;
    @FXML
    private TableColumn<Employee, String> employeeRoleColumn;
    @FXML
    private TableColumn<Employee, String> employeePasswordColumn;
    @FXML
    private TableColumn<Employee, LocalDate> employeeStartColumn;

    @FXML
    private TableColumn<Employee, String> actionColumn;
    @FXML
    public VBox contentArea;
    @FXML
    public Button addNewButton;

    List<Employee> lstEmp = new Employee().getAll();
    ObservableList<Employee> employeelst = FXCollections.observableArrayList(lstEmp);

    public EmployeeController() throws SQLException {
    }


    @FXML
    public void initialize() {
        employeeIdColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.05)); // 5% of the width
        employeeNameColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.15)); // 15% of the width
        employeePhoneColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.15)); // 15% of the width
        employeeEmailColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.2)); // 20% of the width
        employeeRoleColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.1)); // 10% of the width
        employeePasswordColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.1)); // 10% of the width
        employeeStartColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.1)); // 10% of the width
        actionColumn.prefWidthProperty().bind(Bindings.multiply(employeesTable.widthProperty(), 0.144)); // 15% of the width


        loadEmployees();

        actionColumn.setCellFactory(new Callback<TableColumn<Employee, String>, TableCell<Employee, String>>() {
            @Override
            public TableCell<Employee, String> call(TableColumn<Employee, String> param) {
                return new TableCell<Employee, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button updateButton = new Button("Update");
                            Button deleteButton = new Button("Delete");

                            // Update button action
                            updateButton.setOnAction(event -> {
                                Employee employee = getTableView().getItems().get(getIndex());
                                System.out.println("Update clicked for: " + employee.getFullName());
                                update(employee.getEmployeeID());
                            });

                            // Delete button action
                            deleteButton.setOnAction(event -> {
                                Employee employee = getTableView().getItems().get(getIndex());
                                System.out.println("Delete clicked for: " + employee.getFullName());
                                deleteEmployee(employee.getEmployeeID());
                            });

                            // Place buttons in HBox for layout
                            HBox hbox = new HBox(10, updateButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }

    private void loadEmployees() {
        employeeIdColumn.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
        employeeNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        employeePhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        employeeEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        employeeRoleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        employeePasswordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        employeeStartColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));

//        eventTable.getItems().addAll(lstEmp);
        employeesTable.setItems(employeelst);
    }

    public void addEmployee() {
        loadContent("/com/example/hotelpro/manager/employee/employee-add.fxml");
    }

    public void update(int id) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/employee/employee-edit.fxml"));

            EmployeeEditController editEventController = new EmployeeEditController(id);
            fxmlLoader.setControllerFactory(param -> editEventController);

            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if FXML loading fails
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteEmployee(int id) {
        Employee employeeToDelete = null;

        for (Employee employee : employeelst) {
            if (employee.getEmployeeID() == id) {
                employeeToDelete = employee;
                break;
            }
        }

        if (employeeToDelete != null) {
            employeelst.remove(employeeToDelete);

            employeeToDelete.delete(id);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully.");
            alert.showAndWait();
        }
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
