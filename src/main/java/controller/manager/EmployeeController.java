package controller.manager;

import dao.EmployeeDao;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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

    private final EmployeeDao employeeDao = new EmployeeDao();

    @FXML
    public void initialize() {
        // Thiết lập giá trị cho các cột TableView
        employeeIdColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmployeeID()));
        employeeNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName()));
        employeePhoneColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
        employeeEmailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        employeeRoleColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRole()));
        employeePasswordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        employeeStartColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getStartDate()));

        // Tải dữ liệu ban đầu
        loadEmployees();
    }

    private void loadEmployees() {
        // Lấy danh sách nhân viên từ database và hiển thị
        List<Employee> employees = employeeDao.getAll();
        employeesTable.getItems().setAll(employees);
    }

    public void addEmployee(Employee employee) {
        // Thêm nhân viên mới
        employeeDao.save(employee);
        loadEmployees();
    }

    public void updateEmployee(Employee employee) {
        // Cập nhật thông tin nhân viên
        employeeDao.update(employee);
        loadEmployees();
    }

    public void deleteEmployee(Employee employee) {
        // Xóa nhân viên
        employeeDao.delete(employee);
        loadEmployees();
    }
}
