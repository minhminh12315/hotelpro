package controller.manager.room;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Room;
import dao.RoomDao;
import java.math.BigDecimal;
import javafx.scene.control.Alert;


public class RoomSettingsController {

    @FXML
    private Label roomNumberLabel;
    @FXML
    private TextField priceField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField typeField;
    @FXML
//    private TextField statusField;

    private Room currentRoom;

    public void setRoomData(Room room) {
        this.currentRoom = room;
        roomNumberLabel.setText("Room Number: " + room.getRoomNumber());
        priceField.setText(String.valueOf(room.getPrice()));
        capacityField.setText(String.valueOf(room.getCapacity()));
        typeField.setText(room.getRoomType());
//        statusField.setText(room.getStatus());
    }

    @FXML
    private void handleSaveChanges() {
        if (currentRoom != null) {
            try {
                currentRoom.setPrice(new BigDecimal(priceField.getText()));
                currentRoom.setCapacity(Integer.parseInt(capacityField.getText()));
                currentRoom.setRoomType(typeField.getText());
                // Gọi DAO để cập nhật dữ liệu vào database
                RoomDao roomDao = new RoomDao();
                roomDao.update(currentRoom);
                // Hiển thị thông báo thành công
                showAlert("Thành công", "Cập nhật thông tin phòng thành công!", Alert.AlertType.INFORMATION);
            } catch (NumberFormatException e) {
                showAlert("Lỗi nhập liệu", "Vui lòng nhập đúng định dạng số!", Alert.AlertType.ERROR);
            } catch (Exception e) {
                showAlert("Lỗi", "Có lỗi xảy ra khi cập nhật dữ liệu!", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleDeleteRoom() {
        if (currentRoom == null) {
            showAlert("Lỗi", "Không có phòng nào được chọn!", Alert.AlertType.ERROR);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Xác nhận xóa phòng");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xóa phòng số " + currentRoom.getRoomNumber() + " không?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                RoomDao roomDao = new RoomDao();
                roomDao.delete(currentRoom);  // Gọi phương thức xóa phòng với đối tượng Room

                showAlert("Thành công", "Phòng đã được xóa thành công!", Alert.AlertType.INFORMATION);

                // Sau khi xóa, có thể cập nhật lại danh sách phòng hoặc đóng cửa sổ
                // loadRoomData();  // Nếu có phương thức tải lại dữ liệu
            }
        });
    }




    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
