package controller.manager.product;

import dao.ProductDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Product;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ProductEditController {

    @FXML
    private TextField productNameField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField unitField;

    private ProductDao productDao = new ProductDao();
    private int productId;

    public void setProductId(int productId) {
        this.productId = productId;
    }


    @FXML
    private void initialize() {
        loadProductDetails();
    }

    private void loadProductDetails() {
        if (productId != 0) {
            Product product = productDao.findById(productId);
            if (product != null) {
                productNameField.setText(product.getProductName());
                unitPriceField.setText(product.getUnitPrice().toString());
                descriptionField.setText(product.getDescription());
                unitField.setText(product.getUnit());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Product not found");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void updateProduct() {
        String productName = productNameField.getText();
        String unitPriceText = unitPriceField.getText();
        String description = descriptionField.getText();
        String unit = unitField.getText();

        if (productName.isEmpty() || unitPriceText.isEmpty() || description.isEmpty() || unit.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are required");
            alert.showAndWait();
            return;
        }

        BigDecimal unitPrice;
        try {
            unitPrice = new BigDecimal(unitPriceText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unit price must be a valid number");
            alert.showAndWait();
            return;
        }

        Product product = new Product();
        product.setProductID(productId);
        product.setProductName(productName);
        product.setUnitPrice(unitPrice);
        product.setDescription(description);
        product.setUnit(unit);

        productDao.update(product);

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product updated successfully");
        alert.showAndWait();
    }
}
