package controller.manager.product;

import dao.InventoryDao;
import dao.ProductDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Employee;
import model.Inventory;
import model.Product;


import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ProductEditController {

    @FXML
    VBox contentArea;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField unitField;

    Product product;

    private ProductDao productDao = new ProductDao();
    private InventoryDao inventoryDao = new InventoryDao();
    private int productId;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductEditController(int id, VBox contentArea) throws SQLException {
        product = new Product().getById(id);
        setProductId(id);
        System.out.println(id);

        if (product == null) {
            System.out.println("Product is null after getById call");
        } else {
            System.out.println("Product loaded: " + product.getProductName());
        }

        this.contentArea = contentArea;
    }

    @FXML
    private void initialize() {
        if (product != null) {

            productNameField.setText(product.getProductName());
            unitPriceField.setText(product.getUnitPrice().toString());
            inventoryDao.findById(product.getProductID());
            quantityField.setText(String.valueOf(inventoryDao.findById(product.getProductID()).getQuantity()));
            descriptionField.setText(product.getDescription());
            unitField.setText(product.getUnit());
        } else {
            System.out.println("Product is null");
        }
    }

    @FXML
    private void updateProduct() {
        String productName = productNameField.getText();
        String unitPriceText = unitPriceField.getText();
        String quantityText = quantityField.getText();
        String description = descriptionField.getText();
        String unit = unitField.getText();

        if (productName.isEmpty() || unitPriceText.isEmpty() || quantityText.isEmpty() || description.isEmpty() || unit.isEmpty()) {
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
        product.setQuantity(Integer.parseInt(quantityText));
        product.setLastUpdated(LocalDate.now());
        product.setUnit(unit);

        productDao.update(product);


        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product updated successfully");
        alert.showAndWait();

        loadContent("/com/example/hotelpro/manager/product/product-management.fxml");

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
