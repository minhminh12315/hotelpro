package controller.manager.product;

import controller.manager.employee.EmployeeEditController;
import dao.InventoryDao;
import dao.ProductDao;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Employee;
import model.Inventory;
import model.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductController {
    @FXML
    private TextField productNameField;
    @FXML
    private TextField unitPriceField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField unitField;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, BigDecimal> unitPriceColumn;
    @FXML
    private TableColumn<Product, String> descriptionColumn;
    @FXML
    private TableColumn<Product, String> unitColumn;
    @FXML
    private TableColumn<Product, String> actionColumn;

    @FXML
    public VBox contentArea;
    @FXML
    private TextField searchField;
    @FXML
    private Pagination pagination;

    private int itemsPerPage = 14;

    private ProductDao productDao = new ProductDao();
    private InventoryDao inventoryDao = new InventoryDao();
    private ObservableList<Product> productList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productIdColumn.prefWidthProperty().bind(Bindings.multiply(productsTable.widthProperty(), 0.05));
        productNameColumn.prefWidthProperty().bind(Bindings.multiply(productsTable.widthProperty(), 0.2));
        unitPriceColumn.prefWidthProperty().bind(Bindings.multiply(productsTable.widthProperty(), 0.15));
        quantityColumn.prefWidthProperty().bind(Bindings.multiply(productsTable.widthProperty(), 0.15));
        descriptionColumn.prefWidthProperty().bind(Bindings.multiply(productsTable.widthProperty(), 0.25));
        unitColumn.prefWidthProperty().bind(Bindings.multiply(productsTable.widthProperty(), 0.15));
        actionColumn.prefWidthProperty().bind(Bindings.multiply(productsTable.widthProperty(), 0.194));

        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        unitColumn.setCellValueFactory(new PropertyValueFactory<>("unit"));

        loadProducts();
        addActionButton();

        pagination.setPageCount((int) Math.ceil((double) productList.size() / itemsPerPage));
        pagination.setCurrentPageIndex(0);
        pagination.setMaxPageIndicatorCount(5);

        loadPage(0);
        pagination.currentPageIndexProperty().addListener((observable, oldValue, newValue) -> loadPage(newValue.intValue()));

    }


    private void loadProducts() {
        productList.setAll(productDao.getAll());
        productsTable.setItems(productList);
    }

    private void addActionButton() {
        actionColumn.setCellFactory(new Callback<TableColumn<Product, String>, TableCell<Product, String>>() {
            @Override
            public TableCell<Product, String> call(TableColumn<Product, String> param) {
                return new TableCell<Product, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Button updateButton = new Button("Update");
                            Button deleteButton = new Button("Delete");

                            updateButton.setOnAction(event -> {
                                Product product = getTableView().getItems().get(getIndex());
                                updateProduct(product.getProductID());
                            });

                            deleteButton.setOnAction(event -> {
                                Product product = getTableView().getItems().get(getIndex());
                                deleteProduct(product.getProductID());
                            });

                            HBox hbox = new HBox(10, updateButton, deleteButton);
                            setGraphic(hbox);
                        }
                    }
                };
            }
        });
    }


    @FXML
    private void searchProduct() {
        String searchText = searchField.getText().toLowerCase();
        productsTable.setItems(FXCollections.observableArrayList());

        if (searchText.isEmpty()) {
            productsTable.setItems(FXCollections.observableArrayList(productList));
            addActionButton();
        } else {
            List<Product> filteredList = productList.stream()
                    .filter(product -> product.getProductName().toLowerCase().contains(searchText) ||
                            String.valueOf(product.getUnitPrice()).toLowerCase().contains(searchText) ||
                            product.getUnit().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
            productsTable.setItems(FXCollections.observableArrayList(filteredList));
            addActionButton();
        }
    }

    private void loadPage(int pageIndex) {
        int start = pageIndex * itemsPerPage;
        int end = Math.min(start + itemsPerPage, productList.size());

        List<Product> pageData = productList.subList(start, end);
        productsTable.setItems(FXCollections.observableArrayList(pageData));
        addActionButton();
    }

    public void addProduct() {
        loadContent("/com/example/hotelpro/manager/product/product-add.fxml");
    }

    public void updateProduct(int productId) {
        try {


            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/hotelpro/manager/product/product-edit.fxml"));

            ProductEditController controller = new ProductEditController(productId, contentArea);
            fxmlLoader.setControllerFactory(param -> controller);

            Parent newContent = fxmlLoader.load();
            contentArea.getChildren().setAll(newContent);
       } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        Product product = new Product();
        product.setProductID(productId);

        productDao.delete(product);
        loadProducts();

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Product deleted successfully");
        alert.showAndWait();
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