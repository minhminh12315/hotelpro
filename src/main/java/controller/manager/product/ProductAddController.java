package controller.manager.product;

import dao.ProductDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Product;

import java.io.IOException;
import java.math.BigDecimal;

public class ProductAddController {
    @FXML
    private VBox root;
    @FXML
    private TextField productNameField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField unitField;

    private ProductDao productDao = new ProductDao();

    private boolean validateProduct() {
        String productName = productNameField.getText();
        String unitPriceText = unitPriceField.getText();
        String description = descriptionField.getText();
        String unit = unitField.getText();

        if (productName.isEmpty() || unitPriceText.isEmpty() || description.isEmpty() || unit.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "All fields are required");
            alert.showAndWait();
            return false;
        }

        try {
            new BigDecimal(unitPriceText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Unit price must be a valid number");
            alert.showAndWait();
            return false;
        }

        return true;
    }

    @FXML
    public void storeProduct() {
        if (validateProduct()) {
            String productName = productNameField.getText();
            BigDecimal unitPrice = new BigDecimal(unitPriceField.getText());
            String description = descriptionField.getText();
            String unit = unitField.getText();

            Product product = new Product();
            product.setProductName(productName);
            product.setUnitPrice(unitPrice);
            product.setDescription(description);
            product.setUnit(unit);

            productDao.save(product);


                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thêm mới thành công");
                alert.showAndWait();
                loadContent("/com/example/hotelpro/manager/product/product-management.fxml");

            productNameField.clear();
        }
    }

    private void loadContent(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newContent = fxmlLoader.load();
            root.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}