package controller.manager.room;

import dao.RoomDao;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.Room;

import java.io.IOException;
import javafx.scene.control.Label;
import java.math.BigDecimal;

public class AddRoomController {

    @FXML
    private VBox root;

    @FXML
    private TextField roomNumberField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField capacityField;

    @FXML
    private ComboBox<String> typeField;

    @FXML
    private Label errorLabel; // Add a Label to display error messages

    private final RoomDao roomDao = new RoomDao();

    @FXML
    private void handleSave() {
        try {
            int roomNumber = Integer.parseInt(roomNumberField.getText());

            if (roomDao.findByRoomNumber(String.valueOf(roomNumber)) != null) {
                errorLabel.setText("Room with this number already exists!");
                return;
            }

            double price = Double.parseDouble(priceField.getText());
            int capacity = Integer.parseInt(capacityField.getText());
            String type = typeField.getValue();

            Room room = new Room();
            room.setRoomNumber(roomNumber);
            room.setPrice(BigDecimal.valueOf(price));
            room.setCapacity(capacity);
            room.setRoomType(type);

            roomDao.save(room);

            loadContent("/com/example/hotelpro/manager/room/room-management.fxml");
            
        } catch (NumberFormatException e) {
            // Handle invalid input
            e.printStackTrace();
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