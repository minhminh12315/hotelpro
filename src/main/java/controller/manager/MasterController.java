package controller.manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Employee;

import java.io.IOException;
import java.sql.SQLException;

public class MasterController {

    @FXML
    public BorderPane root;
    public MenuButton logoutButton;

    @FXML
    ScrollPane contentScrollPane;

    @FXML
    private VBox contentArea;

    @FXML
    public static String userRole;

    @FXML
    private Button btnHome;
    @FXML
    private Button btnRoomManagement;
    @FXML
    private Button btnEmployeeManagement;
    @FXML
    private Button btnServicesManagement;
    @FXML
    private Button btnCustomerManagement;
    @FXML
    private Button btnBookingManagement;
    @FXML
    private Button btnProductManagement;

    public static int employeeID;

    public static void setEmployeeID(int employeeID) {
        MasterController.employeeID = employeeID;
    }

    public static int getEmployeeID() {
        return employeeID;
    }

    public void initialize() throws SQLException {
        if (userRole == null) {
            userRole = "manager";
            System.out.println("Warning: userRole is null, defaulting to manager.");
        }
        if (employeeID != 0) {
            Employee employee = new Employee().getById(employeeID);
            if (employee != null) {
                logoutButton.setText(employee.getFullName());
            }
        }
//        if (userRole == null) {
//            // chuyen trang login
//            try {
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/login/login.fxml"));
//                Parent newContent = fxmlLoader.load();
//                root.getChildren().setAll(newContent);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        contentScrollPane.addEventFilter(ScrollEvent.ANY, event -> {
            if (event.getDeltaY() != 0) {
                double multiplier = 6.0;  // Increase this value to speed up scrolling
                contentScrollPane.setVvalue(contentScrollPane.getVvalue() - event.getDeltaY() * multiplier / contentScrollPane.getContent().getBoundsInLocal().getHeight());
                event.consume();
            }
        });
        updateViewBasedOnRole(); // Cập nhật giao diện dựa trên vai trò
        handleDashboard();
    }

    public static void setUserRole(String userRole) {
        MasterController.userRole = userRole;
        System.out.println("User role set to: " + userRole);
    }

    private void updateViewBasedOnRole() {
        // Check the user role and show/hide management sections accordingly
        if ("Staff".equalsIgnoreCase(userRole)) {
            // Staff role - only some buttons will be visible
            btnRoomManagement.setVisible(true);
            btnRoomManagement.setManaged(true);

            btnEmployeeManagement.setVisible(false);
            btnEmployeeManagement.setManaged(false);

            btnServicesManagement.setVisible(true);
            btnServicesManagement.setManaged(true);

            btnCustomerManagement.setVisible(true);
            btnCustomerManagement.setManaged(true);

            btnBookingManagement.setVisible(true);
            btnBookingManagement.setManaged(true);

            btnProductManagement.setVisible(false);
            btnProductManagement.setManaged(false);

            // Hide settings or any other management sections staff shouldn't access
            btnHome.setVisible(true);
            btnHome.setManaged(true);

        } else if ("Manager".equalsIgnoreCase(userRole)) {
            // Manager role - all buttons will be visible
            btnRoomManagement.setVisible(true);
            btnRoomManagement.setManaged(true);

            btnEmployeeManagement.setVisible(true);
            btnEmployeeManagement.setManaged(true);

            btnServicesManagement.setVisible(true);
            btnServicesManagement.setManaged(true);

            btnCustomerManagement.setVisible(true);
            btnCustomerManagement.setManaged(true);

            btnBookingManagement.setVisible(true);
            btnBookingManagement.setManaged(true);

            btnProductManagement.setVisible(true);
            btnProductManagement.setManaged(true);

            // Allow access to home and settings as well
            btnHome.setVisible(true);
            btnHome.setManaged(true);
        }

        System.out.println("User role: " + userRole);
    }


    @FXML
    private void handleDashboard() {
        loadContent("/com/example/hotelpro/manager/dashboard.fxml");
    }

    @FXML
    private void handleRoomManagement() {
        loadContent("/com/example/hotelpro/manager/room/room-management.fxml");
    }

    @FXML
    private void handleServicesManagement() {
        loadContent("/com/example/hotelpro/manager/service/services-management.fxml");
    }
    
    @FXML
    private void handleEmployeeManagement() {
        loadContent("/com/example/hotelpro/manager/employee/employee-management.fxml");
    }

    @FXML
    private void handleCustomerManagement() {
        loadContent("/com/example/hotelpro/manager/customer/customer-management.fxml");
    }

    public void handleProductManagement() {
        loadContent("/com/example/hotelpro/manager/product/product-management.fxml");
    }

    @FXML
    private void handleSettings() {
        loadContent("/com/example/hotelpro/settings/settings.fxml");
    }

    @FXML
    private void handleBookingManagement() {
        loadContent("/com/example/hotelpro/manager/booking/booking-management.fxml");
    }

    @FXML
    public void handleLogout(ActionEvent event) {
        try {
            // Close the current stage (window)
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            currentStage.close();

            // Load the new scene (login.fxml in this case)
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/login/login.fxml"));
            Parent root = fxmlLoader.load();

            Scene scene = new Scene(root);
            currentStage.setScene(scene);

            // Set the title of the new stage (optional)
            currentStage.setTitle("Login");

            // Show the new stage (login window)
            currentStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);

            // Highlight the corresponding sidebar button based on the page loaded
            if (fxmlPath.contains("dashboard")) {
                highlightActiveButton("Dashboard");
            } else if (fxmlPath.contains("room-management")) {
                highlightActiveButton("RoomManagement");
            } else if (fxmlPath.contains("employee-management")) {
                highlightActiveButton("EmployeeManagement");
            } else if (fxmlPath.contains("services-management")) {
                highlightActiveButton("ServicesManagement");
            } else if (fxmlPath.contains("customer-management")) {
                highlightActiveButton("CustomerManagement");
            } else if (fxmlPath.contains("booking-management")) {
                highlightActiveButton("BookingManagement");
            } else if (fxmlPath.contains("product-management")) {
                highlightActiveButton("ProductManagement");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void highlightActiveButton(String pageName) {
        // Remove active style from all buttons
        resetButtonStyles();

        // Add active style to the selected button
        switch (pageName) {
            case "Dashboard":
                btnHome.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                break;
            case "RoomManagement":
                btnRoomManagement.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                break;
            case "EmployeeManagement":
                btnEmployeeManagement.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                break;
            case "ServicesManagement":
                btnServicesManagement.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                break;
            case "CustomerManagement":
                btnCustomerManagement.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                break;
            case "BookingManagement":
                btnBookingManagement.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                break;
            case "ProductManagement":
                btnProductManagement.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white;");
                break;
            default:
                break;
        }
    }

    private void resetButtonStyles() {
        // Reset the styles of all buttons to their default state
        btnHome.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        btnRoomManagement.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        btnEmployeeManagement.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        btnServicesManagement.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        btnCustomerManagement.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        btnBookingManagement.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        btnProductManagement.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
    }

}