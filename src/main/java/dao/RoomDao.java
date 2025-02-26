package dao;

import model.Room;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.List;

public class RoomDao implements BaseDao<Room> {

    @Override
    public void save(Room room) {

    }

    @Override
    public void update(Room room) {

    }

    @Override
    public void delete(Room room) {

    }

    @Override
    public Room findById(int id) {

        return null;
    }

    @Override
    public List<Room> getAll() {

        return List.of();
    }
}