package controller.manager.customer;

import dao.ProductDao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.Customer;
import dao.CustomerDao;

import java.sql.SQLException;
import java.time.LocalDate;

public class CustomerAddController {
    @FXML
    Button editButton;
    @FXML
    VBox contentArea;

    @FXML
    TextField addName;
    @FXML
    TextField addPhoneNumber;
    @FXML
    TextField addAddress;
    @FXML
    TextField addEmail;
    @FXML
    TextField addIdPassport;

    @FXML
    DatePicker addDateOfBirth;
    @FXML
    ComboBox<String> addGender;

    private CustomerDao customerDao = new CustomerDao();

    public CustomerAddController() {}

    public CustomerAddController(VBox contentArea) throws SQLException {
        this.contentArea = contentArea;
    }

    @FXML
    public void initialize(){
        addGender.getItems().addAll("Male", "Female");
    }

    @FXML
    public void addCustomer(){
        String fullname = addName.getText();
        String phonenumber = addPhoneNumber.getText();
        String email = addEmail.getText();
        String address = addAddress.getText();
        String idPassport = addIdPassport.getText();
        LocalDate dateOfBirth = addDateOfBirth.getValue();
        String gender = addGender.getValue();

        if (fullname.isEmpty() || phonenumber.isEmpty() || email.isEmpty() || address.isEmpty() || idPassport.isEmpty() || dateOfBirth == null || gender == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are required");
            alert.showAndWait();
            return;
        }

        Customer customer = new Customer();
        customer.setFullName(fullname);
        customer.setPhoneNumber(phonenumber);
        customer.setEmail(email);
        customer.setAddress(address);
        customer.setIdPassport(idPassport);
        customer.setDateOfBirth(dateOfBirth);
        customer.setGender(gender);

        customerDao.save(customer);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Customer added successfully");
        alert.showAndWait();

    }
}
