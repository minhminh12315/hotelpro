package controller.manager;

import dao.EmployeeDao;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Employee;

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
    private TableColumn<Employee, Date> employeeStartColumn;

    private EmployeeDao employeeDao = new EmployeeDao();

    @FXML
    public void initialize() {
        employeeIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        employeeNameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        employeePhoneColumn.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        employeeEmailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        employeeRoleColumn.setCellValueFactory(cellData -> cellData.getValue().roleProperty());
        employeePasswordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
//        employeeStartColumn.setCellValueFactory(cellData -> cellData.getValue().startDateProperty());

        loadEmployees();
    }

    private void loadEmployees(){
        List<Employee> employees = employeeDao.getAll();
        employeesTable.getItems().setAll(employees);
    }


}
