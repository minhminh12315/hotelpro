package controller.manager.room;

import dao.RoomDao;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.Room;

import java.math.BigDecimal;

public class AddRoomController {

    @FXML
    private TextField roomNumberField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField capacityField;

    @FXML
    private ComboBox<String> typeField;

    private final RoomDao roomDao = new RoomDao();

    @FXML
    private void handleSave() {
        try {
            int roomNumber = Integer.parseInt(roomNumberField.getText());
            double price = Double.parseDouble(priceField.getText());
            int capacity = Integer.parseInt(capacityField.getText());
            String type =  typeField.getValue();

            Room room = new Room();
            room.setRoomNumber(roomNumber);
            room.setPrice(BigDecimal.valueOf(price));
            room.setCapacity(capacity);
            room.setRoomType(type);

            roomDao.save(room);

            // Close the current window
            roomNumberField.getScene().getWindow().hide();
        } catch (NumberFormatException e) {
            // Handle invalid input
            e.printStackTrace();
        }
    }
}