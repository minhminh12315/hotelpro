package service;

import dao.RoomDao;
import model.Room;

import java.util.List;

public class RoomService {
    private RoomDao roomDao;

    public RoomService() {
        this.roomDao = new RoomDao();
    }

    public void addRoom(Room room) {
        roomDao.save(room);
    }

    public void updateRoom(Room room) {
        roomDao.update(room);
    }

    public void deleteRoom(Room room) {
        roomDao.delete(room);
    }

    public Room getRoomById(int id) {
        return roomDao.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomDao.getAll();
    }
}