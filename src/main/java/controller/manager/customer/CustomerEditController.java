package controller.manager.customer;

import dao.CustomerDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Customer;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerEditController {

    @FXML
    Button editButton;
    @FXML
    VBox contentArea;

    @FXML
    TextField editId;

    @FXML
    TextField editName;
    @FXML
    TextField editPhoneNumber;
    @FXML
    TextField editAddress;
    @FXML
    TextField editEmail;
    @FXML
    TextField editIdPassport;

    @FXML
    DatePicker editDateOfBirth;
    @FXML
    ComboBox<String> editGender;

    Customer customer;
    private int customerId;
    private CustomerDao customerDao = new CustomerDao();

    public CustomerEditController() {}

    public CustomerEditController(int id, VBox contentArea) throws SQLException {
        customerId = id;

        this.contentArea = contentArea;
    }

    @FXML
    private void initialize() {
        Customer customer = customerDao.findById(customerId);
        if (customer != null) {
            editName.setText(customer.getFullName());
            editPhoneNumber.setText(customer.getPhoneNumber());
            editEmail.setText(customer.getEmail());
            editAddress.setText(customer.getAddress());
            editIdPassport.setText(customer.getIdPassport());
            editDateOfBirth.setValue(customer.getDateOfBirth());
            editGender.setValue(customer.getGender());
            editId.setText(String.valueOf(customer.getCustomerID()));
        } else {
            System.out.println("Customer is null");
        }

    }


    @FXML
    private void updateCustomer() {
        String fullname = editName.getText();
        String phonenumber = editPhoneNumber.getText();
        String email = editEmail.getText();
        String address = editAddress.getText();
        String idPassport = editIdPassport.getText();
        LocalDate dateOfBirth = editDateOfBirth.getValue();
        String gender = editGender.getValue();

        if (fullname.isEmpty() || phonenumber.isEmpty() || email.isEmpty() || address.isEmpty() || idPassport.isEmpty() || dateOfBirth == null || gender == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are required");
            alert.showAndWait();
            return;
        }

        Customer customer = new Customer();
        customer.setCustomerID(customerId);
        customer.setFullName(fullname);
        customer.setPhoneNumber(phonenumber);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setIdPassport(idPassport);
        customer.setDateOfBirth(dateOfBirth);
        customer.setGender(gender);

        customerDao.update(customer);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully");
        alert.showAndWait();

        loadContent("/com/example/hotelpro/manager/customer/customer-management.fxml");
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
